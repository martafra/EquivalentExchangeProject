<%@ page import="logic.bean.LoginBean" %>
<%@ page import="logic.bean.UserBean" %>
<%@ page import="logic.controller.application.LoginController" %>


<jsp:useBean id = "loginBean" scope = "request" class = "logic.bean.LoginBean"/>
<jsp:useBean id = "loggedUser" scope = "session" class = "logic.bean.UserBean"/>
<jsp:setProperty name="loginBean" property ="*"/>

<% 
    if( request.getParameter("login") != null){
        LoginController logController=new LoginController();
        Boolean result = logController.login(loginBean);
        if(Boolean.TRUE.equals(result)){
            loggedUser = logController.getUserByLoginData(loginBean);
            request.getSession().setAttribute("loggedUser", loggedUser);
            %>
                <jsp:forward page="Home.jsp"/>
            <% 
            }
    }
%>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd%22%3E
<html>

    <head>

        <title>EE - Login</title>
        <link rel="stylesheet" href="Style/Style.css">
        <link rel="stylesheet" href="Style/HeaderBar.css">
        <style>
        </style>
    </head>

    <body class="bubble-background">

        <div id="headerbar">
            <img id="logoImage" src="assets/images/logo.png" alt="logoImage">
            <span><span style="color: #FF6A00">E</span>QUIVALENT <span style="color: #5AC02A">E</span>XCHANGE</span>


            <span>HOME</span>
            <span>CATALOGUE</span>
            <span>COMMUNITY</span>


            <div id="login">
                <button text="Login" class="orange-clickable">Login</button>
            </div>

        </div>


        <div style = "width: 321px; margin: 219px auto">
        <form action="Login.jsp" name = "login" method = "POST">
            <div>
                <div>username:</div>
                <input type="text" id="userID" name="userID" style = "width: 321px; height: 34px; display: block; margin: 0 auto">
            </div>
            <div style = "margin: 70px auto">
                <span>password:</span>
                <input type="password" id="password" name="password" style = "width: 321px; height: 34px; display: block; margin: 0 auto">
                <div style ="margin: 33px auto">
                	<a href="http://localhost:8080/EquivalentExchangeWeb/Registration.jsp"><input type="button" value="Create an account" class="orange-clickable" style="display:inline-block"/></a>
                    <input type="submit" id = "login" name = "login" value = "Login" class = "orange-clickable" style = "height:39px; display:block; margin:0 auto; float: right">
                    
                </div>
            </div>
        </form>
        </div>






    </body>

</html>