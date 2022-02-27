package com.ConstructionManagement.framework.config.securityconfig;

import java.io.IOException;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;
import com.ConstructionManagement.common.core.domain.model.LoginUser;
import com.ConstructionManagement.common.utils.SecurityUtils;
import com.ConstructionManagement.common.utils.StringUtils;
import com.ConstructionManagement.framework.web.service.TokenService;

/**
 * token过滤器 验证token有效性
 *
 * @author ruoyi
 */
@Component
public class JwtAuthenticationTokenFilter extends OncePerRequestFilter
{
    @Autowired
    private TokenService tokenService;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain)
            throws ServletException, IOException
    {
        //根据请求获取用户
        LoginUser loginUser = tokenService.getLoginUser(request);
        //SecurityUtils.getAuthentication()当前用户的Authentication
        if (StringUtils.isNotNull(loginUser) && StringUtils.isNull(SecurityUtils.getAuthentication()))
        {
            //验证token是否能续约
            tokenService.verifyToken(loginUser);
            //loginUser.getAuthorities() == null
            UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(loginUser, null, loginUser.getAuthorities());
            authenticationToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));

            //不设置在下一个过滤器会产生异常
            SecurityContextHolder.getContext().setAuthentication(authenticationToken);
        }
        //将请求转发给过滤器链上下一个对象
        chain.doFilter(request, response);
    }
}
