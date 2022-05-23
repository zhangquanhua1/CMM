package com.ConstructionManagement.framework.config;

import com.ConstructionManagement.common.core.domain.model.LoginUser;
import com.ConstructionManagement.common.core.redis.RedisCache;
import com.ConstructionManagement.common.utils.SecurityUtils;
import com.ConstructionManagement.common.utils.spring.SpringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServletServerHttpRequest;
import org.springframework.web.socket.WebSocketHandler;
import org.springframework.web.socket.server.support.DefaultHandshakeHandler;

import javax.servlet.http.HttpServletRequest;
import java.security.Principal;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;


public class CustomHandshakeHandler extends DefaultHandshakeHandler {

    private RedisCache redisCache = SpringUtils.getBean(RedisCache.class);
    private final Logger logger = LoggerFactory.getLogger(CustomHandshakeHandler.class);

    @Override
    protected Principal determineUser(ServerHttpRequest request, WebSocketHandler wsHandler, Map<String, Object> attributes) {
//            String userId = ;
//        return new CustomPrinciple(String.valueOf(loginUser.getUserId()));
        if (request instanceof ServletServerHttpRequest) {
            ServletServerHttpRequest servletServerHttpRequest = (ServletServerHttpRequest) request;
            HttpServletRequest httpRequest = servletServerHttpRequest.getServletRequest();
            final String userId = httpRequest.getParameter("userId");
            List<String> onlineUsers = redisCache.getCacheList("webscoketOnlineUsers");
            if (onlineUsers == null)
                onlineUsers = new ArrayList<>();
            logger.info("在线用户id" + onlineUsers);
            if (!onlineUsers.contains(userId))
                onlineUsers.add(userId);
            redisCache.deleteObject("webscoketOnlineUsers");
            redisCache.setCacheList("webscoketOnlineUsers", onlineUsers);

            return new CustomPrinciple(userId);
        }
        return null;
    }
}
