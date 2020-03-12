package com.stone.shiro.helloworld;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.*;
import org.apache.shiro.config.IniSecurityManagerFactory;
import org.apache.shiro.mgt.DefaultSecurityManager;
import org.apache.shiro.mgt.SecurityManager;
import org.apache.shiro.realm.Realm;
import org.apache.shiro.realm.text.IniRealm;
import org.apache.shiro.session.Session;
import org.apache.shiro.subject.Subject;
import org.apache.shiro.util.Factory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;



public class Quickstart {

    private static final transient Logger log = LoggerFactory.getLogger(Quickstart.class);


    public static void main(String[] args) {
        //  1.得到shiro.init文件
//        Factory<SecurityManager> factory = new IniSecurityManagerFactory("classpath:shiro.ini");
//        SecurityManager securityManager = factory.getInstance();
        DefaultSecurityManager defaultSecurityManager=new DefaultSecurityManager();
        IniRealm iniRealm=new IniRealm("classpath:shiro.ini");
        defaultSecurityManager.setRealm(iniRealm);

        //  2.吧defaultSecurityManager绑定到setSecurityManager
        SecurityUtils.setSecurityManager(defaultSecurityManager);


        // 获取当前的Subject,调用SecurityUtils.getSubject
        Subject currentUser = SecurityUtils.getSubject();

        // 测试使用session
        Session session = currentUser.getSession();
        session.setAttribute("someKey", "aValue");
        String value = (String) session.getAttribute("someKey");

        if (value.equals("aValue")) {
            log.info("===== \t Retrieved the correct value! [" + value + "]");
        }

        // 测试当前的用户是否已被认证，即是否登录
        if (!currentUser.isAuthenticated()) {
            //  吧用户名密码封装成UsernamePasswordToken对象
            UsernamePasswordToken token = new UsernamePasswordToken("lonestarr", "vespa");
            token.setRememberMe(true);
            try {
                //  没有异常就执行登录
                currentUser.login(token);
            } catch (UnknownAccountException uae) { // UnknownAccountException错误提示:未知账户
                log.info("There is no user with username of " + token.getPrincipal());
                return;
            } catch (IncorrectCredentialsException ice) { // IncorrectCredentialsException错误提示:密码不对
                log.info("Password for account " + token.getPrincipal() + " was incorrect!");
                return;
            } catch (LockedAccountException lae) { // LockedAccountException错误提示 ： 用户被锁定
                log.info("The account for username " + token.getPrincipal() + " is locked.  " +
                        "Please contact your administrator to unlock it.");
                return;
            }
            catch (AuthenticationException ae) { // 所有认证时异常的父类
                return;
            }
        }

        //  登录成功打印
        log.info("===== \t User [" + currentUser.getPrincipal() + "] logged in successfully.");

        //判断该用户是否有该该角色
        if (currentUser.hasRole("schwartz")) {
            log.info("===== \t May the Schwartz be with you!");
        } else {
            log.info("===== \t Hello, mere mortal.");
        }

        //  判断该用户是否有具备某个行为
        if (currentUser.isPermitted("lightsaber:wield")) {
            log.info("===== \t You may use a lightsaber ring.  Use it wisely.");
        } else {
            log.info("===== \t Sorry, lightsaber rings are for schwartz masters only.");
        }

        //  判断该用户是否有具备某个行为 (比前面那个更加具体)
        if (currentUser.isPermitted("winnebago:drive:eagle5")) {
            log.info("===== \t You are permitted to 'drive' the winnebago with license plate (id) 'eagle5'.  " +
                    " Here are the keys - have fun!");
        } else {
            log.info("===== \t Sorry, you aren't allowed to drive the 'eagle5' winnebago!");
        }


        System.out.println("完成操作前用户是否认证"+currentUser.isAuthenticated());
        // 完成操作之后退出
        currentUser.logout();

        System.out.println("完成操作后用户是否认证"+currentUser.isAuthenticated());
        //  程序结束
        System.exit(0);
    }
}
