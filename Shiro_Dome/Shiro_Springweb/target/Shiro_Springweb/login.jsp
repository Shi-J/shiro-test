<%--
  Created by IntelliJ IDEA.
  User: Administrator
  Date: 2020/3/10
  Time: 22:50
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Title</title>
</head>
<body>

<h3> login page /</h3>

<form action="shiro/login" method="post">
   用户 ： <input type="text" name="username"/> <br/>
    密码 ：<input type="password" name="password"/> <br/>
    <input type="submit" name="登录"/> <br/>
</form>

</body>
</html>
