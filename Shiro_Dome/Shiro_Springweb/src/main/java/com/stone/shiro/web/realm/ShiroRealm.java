package com.stone.shiro.web.realm;

import org.apache.shiro.authc.*;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.crypto.hash.SimpleHash;
import org.apache.shiro.realm.AuthenticatingRealm;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.realm.Realm;
import org.apache.shiro.subject.PrincipalCollection;
import org.apache.shiro.util.ByteSource;

import java.util.HashSet;
import java.util.Set;


//  如果只需要认证可以只继承AuthorizingRealm类
public class ShiroRealm extends AuthorizingRealm
{

    //  认证方法
    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken token)
            throws AuthenticationException {
        //  在认证时，shiro封装的UsernamePasswordToken会保存到AuthenticationToken中

        //  1.吧AuthenticationToken重新转换为UsernamePasswordToken
        UsernamePasswordToken uptoken= (UsernamePasswordToken) token;
        //  2.从UsernamePasswordToken中获取username
        String username = uptoken.getUsername();
        //  3.从数据库中获取数据信息
        System.out.println("模拟从数据库中获取数据信息 username " +username+"所对应的用户信息");
        //  4.用户判断
        if("zs".equals(username)){
            throw  new LockedAccountException("此用户被锁定");
        }else if("aa".equals(username)){
            throw  new UnknownAccountException("用户不存在");
        }
        //  5.根据用户的判断情况，来构建AuthenticationInfo对象并返回 通过情况使用SimpleAuthenticationInfo
        /*
           SimpleAuthenticationInfo参数：
           1.Object principal：认证的实体信息 可以是username
           2.Object credentials:  密码 password
           3.ByteSource credentialssalt :盐值加密最后结果
           4.String realmName：当前realm对象的name，调用父类的方法即可
           6.Object hashedCredentials :计算的盐值


        */
        Object principal = username; // 用户

        //  获取盐值加密密码
        Object credentials= null;
        if("shij".equals(username)){
            credentials = shiroMd5("MD5", "123456", "shij", 2);
        }else if("user".equals(username)){
            credentials= shiroMd5("MD5","123456","user",2);
        }else if("admin".equals(username)){
            credentials= shiroMd5("MD5","123456","admin",2);
        }

//        Object credentials="4280d89a5a03f812751f504cc10ee8a5"; // 加密后密码
        //  当前realm对象的name
        String realmName=getName();
        //  盐值
        ByteSource credentialssalt = ByteSource.Util.bytes(username); //  得到输入账号的盐值
//        SimpleAuthenticationInfo saif=new SimpleAuthenticationInfo(principal,credentials,realmName);
        SimpleAuthenticationInfo saif=new SimpleAuthenticationInfo(principal,credentials,credentialssalt,realmName);

        return saif;
    }

    public static void main(String [] agrs){
        /*
            SimpleHash方法查看加密结果
            String algorithmName:加密的方法
            Object source:需要加密的内容
            Object salt:盐值  ByteSource.Util.bytes 可以获取盐值
            int hashlterations:加密次数
        */
        String algorithmName = "MD5";//4280d89a5a03f812751f504cc10ee8a5
        Object source="123456";
        Object salt= ByteSource.Util.bytes("shij");
        int hashlterations=2;
        Object result=new SimpleHash(algorithmName,source,salt,hashlterations);
        System.out.println(algorithmName+"加密方法，加密"+source+"\t"+hashlterations+"次之后的的结果为:"+result);
    }


    public static Object shiroMd5(String algorithmName, Object source,Object saltValue, int hashlterations){
        Object salt= ByteSource.Util.bytes(saltValue);
        Object result=new SimpleHash(algorithmName,source,salt,hashlterations);
        return result;
    }

    public static Object shiroMd5(String algorithmName, Object source, int hashlterations){
        Object salt = null;
        Object result=new SimpleHash(algorithmName,source,salt,hashlterations);
        return result;
    }


    //  授权方法
    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principals)
    {
        //  1.从PrincipalCollection中获取登时信息
        Object primaryPrincipal = principals.getPrimaryPrincipal();
        //  2.利用登陆的用户信息来获取当前登陆用户的角色
        Set<String> roles=new HashSet<>();
        if("admin".equals(primaryPrincipal)){
            //  添加角色
            roles.add("admin");
        }

        if("user".equals(primaryPrincipal) || "shij".equals(primaryPrincipal)){
            //  添加角色
            roles.add("user");
        }

        //  3.创建SimpleAuthorizationInfo，并设置reles属性
        SimpleAuthorizationInfo sainfo=new SimpleAuthorizationInfo(roles);
        //  4.返回SimpleAuthorizationInfo对象
        return sainfo;
    }
}
