package com.ConstructionManagement.web.controller.ws;

import com.ConstructionManagement.common.core.controller.BaseController;
import com.ConstructionManagement.common.core.domain.ChatMsg;
import com.ConstructionManagement.common.core.domain.model.LoginUser;
import com.ConstructionManagement.common.core.redis.RedisCache;
import com.ConstructionManagement.system.service.IChatMsgService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;

import java.util.Date;
import java.util.List;

@Controller
public class WsController extends BaseController {
    @Autowired
    SimpMessagingTemplate simpMessagingTemplate;
    @Autowired
    private RedisCache redisCache;
    @Autowired
    IChatMsgService ims;
    @MessageMapping("/ws/chat")
    public void handleMsg( ChatMsg chatMsg) {
        chatMsg.setDate(new Date());
        List<String> lists=redisCache.getCacheList("webscoketOnlineUsers");
        if(!lists.contains(chatMsg.getTo())){
            ims.insertSelective(chatMsg);
        }
        simpMessagingTemplate.convertAndSendToUser(chatMsg.getTo(), "/queue/chat", chatMsg);
    }
}
