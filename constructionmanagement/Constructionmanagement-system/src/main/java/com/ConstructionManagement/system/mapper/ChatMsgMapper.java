package com.ConstructionManagement.system.mapper;

import com.ConstructionManagement.common.core.domain.ChatMsg;

import java.util.List;

public interface ChatMsgMapper {
    int deleteByPrimaryKey(String to);

    int insertSelective(ChatMsg record);

    List<ChatMsg> selectByPrimaryKey(String to);

    int updateByPrimaryKeySelective(ChatMsg record);

}
