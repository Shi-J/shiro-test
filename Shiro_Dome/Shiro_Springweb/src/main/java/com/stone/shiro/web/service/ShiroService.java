package com.stone.shiro.web.service;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authz.annotation.RequiresRoles;
import org.apache.shiro.session.Session;

public class ShiroService
{
    /*
        @RequiresRoles 必须要有什么角色才能进行访问
    */

    @RequiresRoles({"user"})
    public void getMethod(){
        //  测试shiro session  2. 得到session
        Session session = SecurityUtils.getSubject().getSession();
        Object key = session.getAttribute("key");

        System.out.println("shiro service .... \t  session的值 "+key);
    }

}
