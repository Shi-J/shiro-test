package com.stone.shiro.web.controller;

import com.stone.shiro.web.service.ShiroService;
import com.sun.net.httpserver.HttpsServer;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.authz.annotation.RequiresRoles;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpSession;

@Controller
@RequestMapping("/shiro")
public class LoginController
{
    @Autowired
    private ShiroService shiroService;

    @RequestMapping("/testShiroAnnotation")
    public String testShiroAnnotation(HttpSession session){
        //  测试shiro session  1. 设置session
        session.setAttribute("key","shiroValue");
        shiroService.getMethod();
        return  "ShiroAnnotation";
    }

    @RequestMapping("/login")
    public String shiroLogin(@RequestParam("username") String username, @RequestParam("password") String password){
        //   1.获取shiro框架对外api Subject
        Subject subject = SecurityUtils.getSubject();

        //  2.进行权限认证
        //  2.1isAuthenticated判断用户是否已被认证，即是否登录
        if(!subject.isAuthenticated()){
            // 2.2吧用户名密码封装到UsernamePasswordToken中 shiro框架会吧UsernamePasswordToken封装的内容自动封装到AuthenticatingRealm类的doGetAuthenticationInfo方法的形参中
            UsernamePasswordToken token=new UsernamePasswordToken(username,password);
            //  setRememberMe容许实现自动登录
            token.setRememberMe(true);
            try
            {
                //  2.3执行登录
                subject.login(token);
                return "redirect:/success.jsp";
            }catch (Exception e){
                System.out.println("登录失败 \t" +e.getMessage());
            }


        }

        return "/login";
    }
}
