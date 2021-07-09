<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"
    
    %>
<%@page import= "logic.bean.UserBean"%>
<%@page import= "logic.bean.ItemDetailsBean"%>
<%@page import="logic.controller.application.ItemDetailsController" %>
 
<%
	ItemDetailsController controller = new ItemDetailsController();
%>    
<% if(request.getParameter("goToItemDetails")!=null)   {
	UserBean loggedUser = controller.getUserData("EvilDwarf");
	session.setAttribute("loggedUser", loggedUser);
	session.setAttribute("itemID", "1");
%>	
	<jsp:forward page="ItemDetails.jsp"/>

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
		 <input type="submit" name="goToItemDetails" value="Go to itemDetails"/>
	</form>
</body>
</html>