<%@ page import="logic.controller.application.ItemAdController" %>
<%@ page import="logic.bean.UserBean" %>
<%@ page import="logic.bean.ItemBean" %>
<%@ page import="java.util.List" %>
<%@ page import="java.util.ArrayList" %>



<%!List<String> itemConditions = new ArrayList<>();%>
<%!List<String> itemTypes = new ArrayList<>(List.of("Book", "Movie", "Videogame"));%>
<%!List<ItemBean> itemList = new ArrayList<>(); %>
<% 
	ItemAdController itemController = new ItemAdController();
	itemConditions = itemController.getConditionTypes();

%>

<%  %>



<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
	
	<head>
		<title>EE - Post an advertisement</title>
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
		
		<div style="width:1010px; margin: 76px auto">
		
			<div>			
				<input type="number" id="price" name="price" placeholder = "Type here the price of your product..." style = "width: 454px; height: 35px; display: inline-block">
				
				<select name="type" id="type" style = "width: 251px; height: 35px; background-color:#FF6A00; display:block; margin:0 auto; float:right;">
					<option value="" disabled selected>item type</option>
    				<% 
						for(String type: itemTypes){
					%>
						<option value = "<%= type%>"><%= type%></option>
					<% } %>
  				</select>	
			</div>
			
			
			<div style ="margin: 34px auto">
				<textarea id="description" name="description" placeholder = "Type here the description of your product..." style = "width: 454px; height: 177px; display: inline-block; resize:none"> </textarea>
				<select name="refItem" id="refItem" style = "width: 430px; height: 35px; color:#5AC02A; margin: auto; float: right">
				<option value="" disabled selected>referred item</option>
					<% 
						for(String condition: itemConditions){
					%>
						<option value = "<%= condition%>"><%= condition%></option>
					<% } %>
  				</select>
				
			</div>
			
			<div style ="margin: 34px auto">
				<input type="text" id="address" name="address" placeholder = "Type here where you want to sell your product..." style = "width: 454px; height: 35px; display: inline-block;">
			</div>
			
			
			<div style ="margin: 34px auto">
				<select name="condition" id="condition" style = "width: 251px; height: 35px; background-color:#FF6A00">
				<option value="" disabled selected>condition</option>
					<% 
						for(String condition: itemConditions){
					%>
						<option value = "<%= condition%>"><%= condition%></option>
					<% } %>
  				</select>
			</div>
			
			<div>
				<form action="PostAd.jsp" method="post">
    				<input type="submit" name="button1" value="+" class="orange-clickable" style ="height:115px; width:64px" />
				</form>
			</div>
		
		
		
		</div>		
		
		
		
	</body>

</html>