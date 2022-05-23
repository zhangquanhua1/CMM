package com.ConstructionManagement.web.controller.system;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import com.ConstructionManagement.common.constant.Constants;
import com.ConstructionManagement.common.core.domain.AjaxResult;
import com.ConstructionManagement.common.core.domain.entity.SysMenu;
import com.ConstructionManagement.common.core.domain.entity.SysUser;
import com.ConstructionManagement.common.core.domain.model.LoginBody;
import com.ConstructionManagement.common.utils.SecurityUtils;
import com.ConstructionManagement.framework.web.service.SysLoginService;
import com.ConstructionManagement.framework.web.service.SysPermissionService;
import com.ConstructionManagement.system.service.ISysMenuService;

/**
 * 登录验证
 *
 * @author ruoyi
 */
@RestController
public class SysLoginController
{
    @Autowired
    private SysLoginService loginService;

    @Autowired
    private ISysMenuService menuService;

    @Autowired
    private SysPermissionService permissionService;

    /**
     * 登录方法
     *
     * @param loginBody 登录信息
     * @return 结果
     */
    @PostMapping("/login")
    public AjaxResult login(@RequestBody LoginBody loginBody)
    {
        AjaxResult ajax = AjaxResult.success();
        // 生成令牌
        Map<String, String> result = loginService.login(loginBody.getUsername(), loginBody.getPassword(), loginBody.getCode(),
                loginBody.getUuid());
        String token = result.get("token");
        ajax.put(Constants.TOKEN, token);
        ajax.put("userId",result.get("userId"));
        //System.out.println("userId:"+result.get("userId"));
        return ajax;
    }

    /**
     * 获取用户信息
     *
     * @return 用户信息
     */
    @GetMapping("getInfo")
    public AjaxResult getInfo()
    {
        SysUser user = SecurityUtils.getLoginUser().getUser();
        // 根据用户获取角色集合 role_key集合
        Set<String> roles = permissionService.getRolePermission(user);
        // 根据用户获取菜单权限集合  perms集合
        Set<String> permissions = permissionService.getMenuPermission(user);
        AjaxResult ajax = AjaxResult.success();
        ajax.put("user", user);
        ajax.put("roles", roles);
        ajax.put("permissions", permissions);
        return ajax;
    }

    /**
     * 获取路由信息
     *
     * @return 路由信息
     */
    @GetMapping("getRouters")
    public AjaxResult getRouters()
    {
        Long userId = SecurityUtils.getUserId();
        //构建父子节点的菜单
        List<SysMenu> menus = menuService.selectMenuTreeByUserId(userId);

        return AjaxResult.success(menuService.buildMenus(menus));
    }
}

