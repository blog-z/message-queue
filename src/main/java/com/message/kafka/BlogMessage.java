package com.message.kafka;

import com.dubbo.util.JsonUtil;
import com.message.entity.Message;
import com.message.mapper.MessageMapper;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;


@Component
public class BlogMessage{

    private static final Logger logger= LoggerFactory.getLogger(BlogMessage.class);

    @Autowired
    private MessageMapper messageMapper;

    @Autowired
    private KafkaTemplate<String,String> kafkaTemplate;

    @KafkaListener(topics = "blogSend",groupId = "blogGroup")
    public void listenKafka(ConsumerRecord<String,String> consumerRecord){
        new Thread(() -> {
            //监听kafka topic blogSend
            String key=consumerRecord.key();
            String value=consumerRecord.value();
            String topic=consumerRecord.topic();
            Message message= JsonUtil.stringToObj(value,Message.class);
            messageMapper.insert(message);
            logger.info("key"+key);
            logger.info("value"+value);
            logger.info("topic"+topic);
            //处理完成后，在将信息发送的kafka topic blogGet
            kafkaTemplate.send("blogGet",key,value);
        }).start();
    }

}
