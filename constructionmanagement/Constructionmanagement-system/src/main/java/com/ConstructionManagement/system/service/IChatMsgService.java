package com.ConstructionManagement.system.service;

import com.ConstructionManagement.common.core.domain.ChatMsg;

import java.util.List;

public interface IChatMsgService {
    int deleteByPrimaryKey(String to);

    int insertSelective(ChatMsg record);

    List<ChatMsg> selectByPrimaryKey(String id);

    int updateByPrimaryKeySelective(ChatMsg record);
}
