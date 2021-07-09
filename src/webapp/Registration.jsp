<%@ page import="logic.bean.RegistrationBean" %>
<%@ page import="logic.controller.application.LoginController" %>
<%@page import="java.sql.Date" %>
<%@ page import = "java.time.ZoneId" %>

<jsp:useBean id = "registrationBean" scope = "request" class = "logic.bean.RegistrationBean"/>
<jsp:setProperty name="registrationBean" property ="*"/>

<%
	if(request.getParameter("registration") != null){
		String date=(String)request.getParameter("birthDate");
		registrationBean.setBirthDate((Date.valueOf(date)));
		LoginController regController = new LoginController();
		if(Boolean.TRUE.equals(regController.register(registrationBean))){
			%>
        		<jsp:forward page="Login.jsp"/>
    		<% 
		}
	}	
%>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
	
	<head>
		<title>EE - Registration</title>
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
		
		<div style = "width: 420px; margin: 147px auto">
		<form action="Registration.jsp" name = "registration" method = "POST">
			<div>
				<div>
					<input type="text" id="name" name="name" style = "width: 200px; height: 34px; display: inline-block">
					<input type="text" id="lastName" name="lastName" style = "width: 200px; height: 34px; display: block; margin: 0 auto; float: right">
				</div>
				<div>
					<input type="text" id="email" name="email" style = "width: 200px; height: 34px; display: inline-block">
					<input type="text" id="username" name="username" style = "width: 200px; height: 34px; display: block; margin: 0 auto; float: right">
				</div>
				<div>
					<input type="password" id="password" name="password" style = "width: 200px; height: 34px; display:inline-block">
					<input type="date" name = "birthDate" id="birthDate" name="trip-start">
				</div>
				<input type="submit" id = "registration" name = "registration" value = "Register" class = "orange-clickable" style = "height: 39; float: left">
			</div>
		</form>
		</div>	
		
		
		
	</body>

</html>