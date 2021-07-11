<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"
    
    %>
<%@page import= "logic.bean.UserBean"%>
<%@page import= "logic.bean.ItemDetailsBean"%>
<%@page import="logic.controller.application.ItemDetailsController" %>
 
<%
	ItemDetailsController controller = new ItemDetailsController();
	if(request.getParameter("goToCatalogue")!=null)   {
		UserBean loggedUser = controller.getUserData("EvilDwarf");
		//session.setAttribute("loggedUser", loggedUser);
%>	
	<jsp:forward page="Catalogue.jsp"/>
<% 
}
%>




<!DOCTYPE html>
<html>
	<head>
		<meta charset="ISO-8859-1">
		<title>Start</title>
	</head>
	<body>
		 <form action="Start.jsp" name="myform" method="POST">
		 	<input type="submit" name="goToCatalogue" value="Go to Catalogue"/>
		</form>
	</body>
</html>