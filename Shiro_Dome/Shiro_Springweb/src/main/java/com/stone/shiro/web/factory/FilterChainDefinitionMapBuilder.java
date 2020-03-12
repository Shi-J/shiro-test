package com.stone.shiro.web.factory;

import java.util.LinkedHashMap;

/*
    配置资源和权限需要的bean
*/
public class FilterChainDefinitionMapBuilder
{
    public LinkedHashMap<String,Object> buildfilterChainDefinitionMap(){
        //  必须为LinkedHashMap
        LinkedHashMap<String,Object>  map = new LinkedHashMap();
        //  假装查询数据库，从数据库中添加资源和权限
        map.put("/login.jsp","anon");
        map.put("/shiro/login","anon");
        map.put("/shiro/logout","logout");
        map.put("/user.jsp","authc,roles[user]"); //认证并且必须为角色为user
        map.put("/admin.jsp","authc,roles[admin]");//认证并且必须为角色为user
        map.put("/success.jsp","user");//自动登录一样可以进入
        map.put("/**","authc");

        return map;
    }

}
