<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"
	%>
<%@page import="logic.controller.application.ItemDetailsController" %>
<%@page import= "logic.bean.ItemDetailsBean"  %>
<%@page import= "logic.bean.UserBean"  %>

<%
	int itemID = Integer.parseInt(request.getParameter("itemID"));
	ItemDetailsController controller = new ItemDetailsController();
	ItemDetailsBean itemBean = controller.getItemDetails(itemID);
	UserBean seller = itemBean.getSeller();
		
%>  

<!DOCTYPE html>
<html>

	<head>
		<link rel="stylesheet" href="Style/ItemDetails.css">
		<link rel="stylesheet" href="Style/Style.css">
		<meta charset="ISO-8859-1">
		<title>ItemDetails</title>
		
	</head>
	
	<body>
		<form action="ItemDetails.jsp" name="myform" method="GET">
		
			<div class = "box">
				<div class = "itemBox">
					<p id="title"> <%= itemBean.getItemName() %> </p>
					<p id="description"><%= session.getAttribute("Descrizione") %></p>
				</div>
			</div>
			<div class = "box">
				<div class = "sellerBox">
					<p id ="sellerLabel">Seller's Information</p>
					<p id ="sellerName"> <%= seller.getUserID() %></p>
				</div>
			</div>
			
		</form>

	</body>
	
</html>