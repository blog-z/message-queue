package com.message.mapper;

import com.message.entity.Message;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

public interface MessageMapper {
    int deleteByPrimaryKey(String messageId);

    int insert(Message record);

    Message selectByPrimaryKey(String messageId);

    List<Message> selectAll();

    int updateByPrimaryKey(Message record);
}