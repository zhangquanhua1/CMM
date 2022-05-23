package com.ConstructionManagement.system.service.impl;

import com.ConstructionManagement.common.core.domain.ChatMsg;
import com.ConstructionManagement.system.mapper.ChatMsgMapper;
import com.ConstructionManagement.system.service.IChatMsgService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ChatMsgServiceImpl implements IChatMsgService {
    @Autowired
    ChatMsgMapper cmm;
    @Override
    public int deleteByPrimaryKey(String to) {
        return cmm.deleteByPrimaryKey(to);
    }

    @Override
    public int insertSelective(ChatMsg record) {
        return cmm.insertSelective(record);
    }

    @Override
    public List<ChatMsg> selectByPrimaryKey(String id) {
        return  cmm.selectByPrimaryKey(id);
    }

    @Override
    public int updateByPrimaryKeySelective(ChatMsg record) {
        return cmm.updateByPrimaryKeySelective(record);
    }
}
