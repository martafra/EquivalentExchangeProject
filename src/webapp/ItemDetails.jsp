<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"
	%>
<%@page import="logic.controller.application.ItemDetailsController" %>
<%@page import="logic.controller.application.WishlistController" %>
<%@page import= "logic.bean.ItemDetailsBean"  %>
<%@page import= "logic.bean.ItemBean"  %>
<%@page import= "logic.bean.UserBean"  %>


<%
	response.setHeader("Pragma", "No-cache");
	response.setHeader("Cache-Control", "no-cache, no-store, must-revalidate");
	response.setDateHeader("Expires", -1);
	
	ItemDetailsController controller = new ItemDetailsController();
	WishlistController wishlistController = new WishlistController();
	UserBean loggedUser = (UserBean)session.getAttribute("loggedUser");
	String mainImg = null;
	
	int itemID;
	if (request.getParameter("buyItem")!= null){
		if(loggedUser == null){
			%>
			<jsp:forward page="Login.jsp"/>
			<% 
		}
		itemID = Integer.parseInt((String)request.getParameter("buyItem"));
		ItemDetailsBean itemDetails = controller.getItemDetails(itemID);
		%><jsp:forward page="SendRequest.jsp">
			<jsp:param name="itemID" value="<%=itemID %>"/>
			<jsp:param name="sellerID" value="<%=itemDetails.getSeller().getUserID() %>"/>
			<jsp:param name="itemName" value="<%=itemDetails.getItemName() %>"/>
			</jsp:forward>
		<% 
				
	}
	else if(request.getParameter("addItem")!= null){
		itemID = Integer.parseInt(request.getParameter("addItem"));
		ItemDetailsBean itemDetails = controller.getItemDetails(itemID);
		wishlistController.addToWishlist(loggedUser.getUserID(), itemDetails.getItemID());
	}
	else{
		itemID = Integer.parseInt(request.getParameter("itemID"));
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
	
	if (request.getParameter("send")!=null){
		System.out.println("Sono in send IF");
 		itemID = Integer.parseInt(request.getParameter("itemID"));
 		itemDetails = controller.getItemDetails(itemID);
 		String buyerID = loggedUser.getUserID();
 		Integer itemInSaleID = itemDetails.getItemInSaleID();
 		String requestText = request.getParameter("requestText");
 		controller.clickOnBuy(buyerID, itemInSaleID, requestText); 
	}
		
%>  


<!DOCTYPE html>
<html>

	<head>

		<link rel="stylesheet" href="Style/Style.css">
		<link rel="stylesheet" href="Style/HeaderBar.css">
		<link rel="stylesheet" href="Style/ItemDetails.css">
		<script src="https://ajax.googleapis.com/ajax/libs/jquery/3.5.1/jquery.min.js"></script>
		<meta charset="ISO-8859-1">
		<title>EE - ItemDetails</title>
	</head>
	
	<body>
			<div id="headerbar">
            	<img id="logoImage" src="assets/images/logo.png" alt="logoImage">
           	 	<span><span style="color: #FF6A00">E</span>QUIVALENT <span style="color: #5AC02A">E</span>XCHANGE</span>


            	<span><a href="Home.jsp" class ="link">HOME</a></span>
            	<span><a href="Catalogue.jsp" class ="link">CATALOGUE</a></span>
            	<span><a href="Community.jsp" class ="link">COMMUNITY</a></span>


            	<div id="login">
			<%	
				if(loggedUser == null){ %>
    				
    					<a href="Login.jsp"><input style="margin: 5px auto" type="button" name="Login" value="Login" id="loginButton" class="orange-clickable"></a>
    				
				<% }else {%>
						<div class="loggedUserLabel" id="user">
							<div> <%=loggedUser.getUserID()%> </div>
							<img src="E:/Desktop/avatar.png" alt="e"/>
						
						</div>
						<div id="menu">
							
							<div><a href="Profile.jsp">Profile</a></div>
							<div><a href="Wallet.jsp">Wallet</a></div>
							<div><a href="Chat.jsp">Chat</a></div>
							<div><a href="Wishlist.jsp">WishList</a></div>
							<div><a href="Logout.jsp">Logout</a></div>
							
						</div>
				<%}%>
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
					<img src="assets/images/avatar.png" alt="error" style="width:150px;height:150px;border-radius:110px;"/>
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
							if( !seller.getUserID().equals(loggedUser.getUserID()) 
								&& !controller.checkRequest(loggedUser.getUserID(), itemDetails.getItemInSaleID()) 
								&& itemDetails.getAvailability()){
							%>
					 			<button id = "buybtn" type="submit" name = "buyItem" value = "<%= itemID%>"> BUY ITEM</button>
					 		<%
					 		}else{%>
					 			<button id = "buybtn" type="submit" name = "buyItem" value = "<%= itemID%>" disabled="disabled" style = "background-color:grey; opacity: 0.3"> BUY ITEM</button>
					 		<%}
							if (controller.checkRequest(loggedUser.getUserID(), itemDetails.getItemInSaleID()) ) {%>
								<p style ="color:red;">Request Already Sent</p>
							<%}
							if (!seller.getUserID().equals(loggedUser.getUserID()) 
								&& !wishlistController.checkWishlist(loggedUser.getUserID(), itemDetails.getItemID()) 
								&& itemDetails.getAvailability()){
							%>
								<a href ="ItemDetails.jsp?addItem=<%=itemID%>" style="color:#5AC02A"> <br>Add To Wishlist </a>
							<%} 
							if (wishlistController.checkWishlist(loggedUser.getUserID(), itemDetails.getItemID())){
							%>
								<p style="color:#5AC02A"><br>In Wishlist</p>
							<%} %>
							
							
							
					 </form>
				</div>	
			</div>
	</body>
	
</html>