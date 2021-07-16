<%@ page import="logic.bean.RegistrationBean" %>
<%@ page import="logic.controller.application.LoginController" %>
<%@page import="java.util.Date" %>
<%@ page import ="java.text.SimpleDateFormat" %>
<%@ page import ="java.text.DateFormat" %>
<%@ page import = "java.time.ZoneId" %>

<jsp:useBean id = "registrationBean" scope = "request" class = "logic.bean.RegistrationBean"/>


<%
	if(request.getParameter("registration") != null){
		registrationBean.setName((String)request.getParameter("name"));
		registrationBean.setLastName((String)request.getParameter("lastName"));
		registrationBean.setEmail((String)request.getParameter("email"));
		registrationBean.setUsername((String)request.getParameter("username"));
		if( !(((String)request.getParameter("password")).equals(((String)request.getParameter("confirmPassword"))))   ){
			%>
    		<jsp:forward page="Registration.jsp"/>
		<% 
		}
		else{
			registrationBean.setPassword((String)request.getParameter("password"));
		}
		
		String date=(String)request.getParameter("birthDate");
		System.out.println(date);
		DateFormat format = new SimpleDateFormat("yyyy-MM-dd");
		Date birthDate = format.parse(date);
		registrationBean.setBirthDate(birthDate);
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
			
			
			<span><a href="Home.jsp" class ="link">HOME</a></span>
            <span><a href="Catalogue.jsp" class ="link" id="catalogue">CATALOGUE</a></span>
            <span><a href="Community.jsp" class ="link">COMMUNITY</a></span>
			
			
			<div id="login">
				<a href="Login.jsp"><input style="margin: 5px auto" type="button" name="Login" value="Login" id="loginButton" class="orange-clickable"></a>
			</div>
			
		</div>	
		
		<div style = "width: 420px; margin: 147px auto">
		<form action="Registration.jsp" name = "registration" method = "POST">
			<div>
				<div>
					<input type="text" id="name" name="name" placeholder = "name" style = "width: 200px; height: 34px; display: inline-block">
					<input type="text" id="lastName" name="lastName" placeholder = "last name" style = "width: 200px; height: 34px; display: block; margin: 0 auto; float: right">
				</div>
				<div style = "margin: 33px auto">
					<input type="text" id="email" name="email" placeholder = "email" style = "width: 200px; height: 34px; display: inline-block">
					<input type="text" id="username" name="username" placeholder = "username" style = "width: 200px; height: 34px; display: block; margin: 0 auto; float: right">
				</div>
				<div>
					<input type="password" id="password" name="password" placeholder = "password" style = "width: 200px; height: 34px; display:inline-block">
					<input type="password" id="confirmPassword" name="confirmPassword" placeholder = "confirm password" style = "width: 200px; height: 34px; display:block; margin:0 auto; float:right">
					
				</div>
				<div style="margin: 33px auto">
				<input type="date" name = "birthDate" id="birthDate" placeholder = "gg/mm/aaaa" style = "width:200px; height: 34px; display:inline-block">
				<input type="submit" id = "registration" name = "registration" value = "Register" class = "orange-clickable" style = "height: 39px; display:block; margin:0 auto; float: right">
				</div>
			</div>
		</form>
		</div>	
		
		
		
	</body>

</html>