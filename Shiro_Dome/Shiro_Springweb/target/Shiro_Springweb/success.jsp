<%--
  Created by IntelliJ IDEA.
  User: Administrator
  Date: 2020/3/11
  Time: 15:54
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<%@ taglib uri="http://shiro.apache.org/tags" prefix="shiro"%>
<html>
<head>
    <title>Title</title>
</head>
<body>

<h3>登录成功</h3> <br/>
<%--
    principal:登陆用户名
    hasRole角色权限
--%>
欢迎你 ：<shiro:principal></shiro:principal> <br/>

<shiro:hasRole name="user">
<a href="user.jsp">user</a>  <br/>
</shiro:hasRole>

<shiro:hasRole name="admin">
<a href="admin.jsp">admin</a> <br/>
</shiro:hasRole>

<a href="shiro/testShiroAnnotation">ShiroAnnotation</a><br/>

<a href="shiro/logout">退出登录</a><br/>

</body>
</html>
