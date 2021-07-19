<%@ page import="logic.bean.LoginBean" %>
<%@ page import="logic.bean.UserBean" %>
<%@ page import="logic.controller.application.LoginController" %>
<%@ page import="logic.support.exception.WrongLoginCredentialsException" %>


<jsp:useBean id = "loginBean" scope = "request" class = "logic.bean.LoginBean"/>
<jsp:setProperty name="loginBean" property ="*"/>

<%String loginRes ="";  %>
<% 
    if( request.getParameter("login") != null){
        LoginController logController=new LoginController();
        try{
        	logController.login(loginBean);
        	%> <jsp:useBean id = "loggedUser" scope = "session" class = "logic.bean.UserBean"/> <%
                    loggedUser = logController.getUserByLoginData(loginBean);
                    request.getSession().setAttribute("loggedUser", loggedUser);
                    %>
                        <jsp:forward page="Home.jsp"/>
                    <% 
        }catch (WrongLoginCredentialsException e1){
        	loginRes = "Wrong credentials supplied, check your user ID and password!";
        }
        
        	
            
    }
%>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html lang="en">

	<head>
		<title>EE - Login</title>
		<script src="https://ajax.googleapis.com/ajax/libs/jquery/3.5.1/jquery.min.js"></script>
		<link rel="stylesheet" href="Style/Style.css">
		<link rel="stylesheet" href="Style/HeaderBar.css">
		<style>
		</style>
	</head>

	<body class="bubble-background">
		
		<div id="headerbar">
            <img id="logoImage" src="assets/images/logo.png" alt="logoImage">
            <span><span style="color: #FF6A00">E</span>QUIVALENT <span style="color: #5AC02A">E</span>XCHANGE</span>


            <span><a href="Home.jsp" class ="link">HOME</a></span>
            <span><a href="Catalogue.jsp" class ="link" id="catalogue">CATALOGUE</a></span>
            <span><a href="Community.jsp" class ="link">COMMUNITY</a></span>


            <div id="login">
          
                 <a href="Login.jsp"><input style="margin: 5px auto" type="button" name="Login" value="Login" id="loginButton" class="orange-clickable"></a>

            </div>

        </div>

	<script>
        $('#user').click(function(e){

            if($('#menu').css("visibility") == 'hidden')
                $('#menu').css("visibility", "visible");
            else
                $('#menu').css("visibility", "hidden");
        });
    </script>


        <div style = "width: 420px; margin: 219px auto">
        <form action="Login.jsp" name = "login" method = "POST">
            <div>
                <div>username:</div>
                <input type="text" id="userID" name="userID" style = "width: 100%; height: 34px; display: block; margin: 0 auto">
            </div>
            <div style = "margin: 70px auto">
                <span>password:</span>
                <input type="password" id="password" name="password" style = "width: 100%; height: 34px; display: block; margin: 0 auto">
                <div style ="margin: 33px auto">
                <input type="submit" id = "loginBtn" name = "login" value = "Login" class = "orange-clickable" style = "height:39px; display:block; margin:0 auto; float: right">
                <a href="Registration.jsp"><input type="button" value="Create an account" class="orange-clickable" style="display:inline-block"/></a>
                    
                    
                </div>
                <div style = "margin:23px auto; color:red"><%=loginRes %></div>
            </div>
        </form>
        </div>






    </body>

</html>
