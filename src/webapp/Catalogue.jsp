<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@page import="logic.controller.application.CatalogueController" %>
<%@page import= "logic.bean.ItemInSaleBean"  %>
<%@page import= "logic.bean.UserBean"  %>   
<%@ page import="java.util.List" %>
<%@ page import="java.util.HashMap" %>

<%
	CatalogueController controller = new CatalogueController();
	UserBean loggedUser = (UserBean)session.getAttribute("loggedUser");
	HashMap<String, String> filters = new HashMap<>();
	
	String username = null;
	if (session.getAttribute("loggedUser") != null) {
		username = loggedUser.getUserID();
	}
	List<ItemInSaleBean> itemInSaleBeanList = controller.getListItemInSaleBeanFiltered(username, filters);	

%>
<!DOCTYPE html>
<html>
	<head>
		<link rel="stylesheet" href="Style/Catalogue.css">
		<link rel="stylesheet" href="Style/Style.css">
		<link rel="stylesheet" href="Style/HeaderBar.css">
		<meta charset="ISO-8859-1">
		<title>Catalogue</title>
	</head>
	<body>
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
		
		<div class = "catalogue">
			<div class = "search">
				<input id = "searchBar" type="text" placeholder="Search..">
				<input id ="searchBtn" type="submit" name="searchBtn" value="SEARCH">
			</div>
			<div class ="items">
				<% for (int i = 0; i < 10; i++) { %>
				<div class= "itemBox">
					<a href ="ItemDetails.jsp?itemID=<%=itemInSaleBeanList.get(i).getItemID()%>" style = "text-decoration : none; color: black;" > <%= itemInSaleBeanList.get(i).getItemName() %> </a>
					<p id ="price"> <%= itemInSaleBeanList.get(i).getPrice()  %> COINS</p>
				</div>
				<%} %>
			
			</div>
		</div>
	</body>
</html>