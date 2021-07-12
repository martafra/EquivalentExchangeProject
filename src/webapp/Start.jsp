<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"
    
    %>
<%@page import= "logic.bean.UserBean"%>
<%@page import= "logic.bean.ItemDetailsBean"%>
<%@page import="logic.controller.application.ItemDetailsController" %>
 
<%
	ItemDetailsController controller = new ItemDetailsController();
	if(request.getParameter("goToCatalogue")!=null)   {
		UserBean loggedUser = controller.getUserData("EvilDwarf");
		session.setAttribute("loggedUser", loggedUser);
%>	
	<jsp:forward page="Catalogue.jsp"/>
<% 
}
	if(request.getParameter("removeAttribute")!=null)   {
		session.removeAttribute("loggedUser");
%>	
		<jsp:forward page="Catalogue.jsp"/>
<% 
}
	if(request.getParameter("goToWishlist")!=null){
		UserBean loggedUser = controller.getUserData("EvilDwarf");
		session.setAttribute("loggedUser", loggedUser);
%>
		<jsp:forward page="WishList.jsp"/>
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
		 	<input type="submit" name="removeAttribute" value="Remove Attribute user"/>
		 	<!-- <input type="submit" name="goToWishlist" value="Go to wishlist"/> -->
		</form>
	</body>
</html>