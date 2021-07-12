<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"
	%>
<%@page import="logic.controller.application.ItemDetailsController" %>
<%@page import= "logic.bean.ItemDetailsBean"  %>
<%@page import= "logic.bean.ItemBean"  %>
<%@page import= "logic.bean.UserBean"  %>


<%
	ItemDetailsController controller = new ItemDetailsController();
	UserBean loggedUser = (UserBean)session.getAttribute("loggedUser");
	String mainImg = null;
	
	if(loggedUser !=null){
		System.out.println("item Details: loggedUser =" + loggedUser.getUserID());
		}
	else{
		System.out.println("Item Details: loggeduser == null");
	}
	//int itemID = Integer.parseInt((String)session.getAttribute("itemID"));
	int itemID;
	if (request.getParameter("buyItem")!= null){
		if(loggedUser == null){
			%>
			<jsp:forward page="Login.jsp"/>
			<% 
		}
		itemID = Integer.parseInt((String)request.getParameter("buyItem"));
		ItemDetailsBean itemDetails = controller.getItemDetails(itemID);
		String buyerID = loggedUser.getUserID();
    	Integer itemInSaleID = itemDetails.getItemInSaleID();
    	controller.clickOnBuy(buyerID, itemInSaleID, "ciao sono " + buyerID +" e voglio comprare " + itemDetails.getItemName()); 
	}
	else{
		itemID = Integer.parseInt((String)request.getParameter("itemID"));
	}
	
	ItemDetailsBean itemDetails = controller.getItemDetails(itemID);
	UserBean seller = itemDetails.getSeller();
	ItemBean item = controller.getItemByID(itemDetails.getReferredItemID());
	
	if(request.getParameter("selectedImg") !=null){
		mainImg = request.getParameter("selectedImg");
	}
	else{
		System.out.println(request.getParameter("selectedImg"));
		mainImg = itemDetails.getMediaPath(); /*"././thewitcher.jpg";*/
	}
		
%>  


<!DOCTYPE html>
<html>

	<head>

		<link rel="stylesheet" href="Style/Style.css">
		<link rel="stylesheet" href="Style/HeaderBar.css">
		<link rel="stylesheet" href="Style/ItemDetails.css">
		<meta charset="ISO-8859-1">
		<title>ItemDetails</title>
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
			<div class = "box" style="margin-left:10%;">
				<div class="imgItem">
					<% for (String photo : itemDetails.getMedia()){ %>
						<a href="ItemDetails.jsp?itemID=<%= itemID %>&selectedImg=<%= photo %>">
						<img  src="<%= photo %>" alt = "Image not found" style="width:180px; height:180px;"/>	
						</a>
					<%} %>
					
				</div>
			</div>
			<div class = "box">
				<div class = "itemBox">
					<p id="title"> <%= itemDetails.getItemName() %> </p>
					<img src="<%= mainImg %>" alt ="Image not found" style ="width:300px; height:300px; display: block;margin-left: auto;margin-right: auto; margin-bottom:10px"/>
								
					<div class ="description">
						<p id ="descriptionLabel"> Description: </p>
						<p id="description"><%= itemDetails.getDescription() %></p>
					</div>
					<div class ="info">
						<label id ="type"> 
						<% if (item.getType() =='B') {
				    			out.print("BOOK");
				    		}
				    		else if(item.getType() =='M') {
				    			out.print("MOVIE");
				    		}
				    		else {
				    			out.print("VIDEOGAME");
				    		}
				    	%> </label>
				    	<label id ="genre">  <%= item.getGenre() %>  </label>
				    	<label id = "condition"> <%= itemDetails.getCondition() %> </label>					
					</div>
					<label id ="itemDetailsLabel"> Item Details:  </label>
					<div class ="details">
						<% 
							String language = "";
							String details;
			    			if (item.getLanguage() != null) {
								language = item.getLanguage().toLowerCase();
							}
			    			if (item.getType() =='B') {
			    		%> 
						<p id ="detailsItem">
			    			Author: <% 	out.print(item.getAuthor()); %> <br>
			    			Edition: <% 	out.print(item.getEdtion()); %> <br>
			    			Number of Page: <% 	out.print(item.getNumberOfPages()); %> <br>
			    			Publishing House:<% 	out.print(item.getPublishingHouse()); %> <br>
			    			Language:<% out.print(language); %>
			    			
			    			<% }else if(item.getType() =='M') {%>
			    		<p id ="detailsItem">
			    		Duration:<% out.print(item.getDuration()); %> min <br>
			    		Language:<% out.print(language); %>
			    		<% } else {
			    			String console = "";
    							if (item.getConsole() != null) {
    					 			console = item.getConsole().toLowerCase();
    							}	
    					%>
			    		<p id ="detailsItem">
			    		Console: <% out.print(console); %> <br>
			    		Language: <% out.print(language); } %>

						</p>
					</div>
				</div>
			</div>
			<div class = "box">
				<div class = "sellerBox">
					<p id ="sellerLabel">Seller's Information</p>
					<p id ="sellerName"> <%= seller.getUserID() %></p>
				</div>
				
				<div class = "buyBox">
					<p id="price"> <%= itemDetails.getPrice() %> COINS </p>
					<form action="ItemDetails.jsp" name="myform" method="POST">
						
						<%	if (loggedUser ==  null){
							%>
								<button id = "buybtn" type="submit" name = "buyItem" value = "<%= itemID%>"> BUY ITEM</button>
							<% 
								return;
							}
							if( !seller.getUserID().equals(loggedUser.getUserID()) && !controller.checkRequest(loggedUser.getUserID(), itemDetails.getItemInSaleID())){
							%>
					 			<button id = "buybtn" type="submit" name = "buyItem" value = "<%= itemID%>"> BUY ITEM</button>
					 		<%
					 		}
							if (controller.checkRequest(loggedUser.getUserID(), itemDetails.getItemInSaleID()) ) {%>
								<button id = "buybtn" type="submit" name = "buyItem" value = "<%= itemID%>" disabled="disabled" style = "background-color:grey; opacity: 0.3"> BUY ITEM</button>
								<p>Request Already Sent</p>
							<%} %>
					 </form>
				</div>	
			</div>
	</body>
	
</html>