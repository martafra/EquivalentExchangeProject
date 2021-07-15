<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
    
<%@page import="logic.controller.application.WishlistController" %>
<%@page import= "logic.bean.UserBean"  %>
<%@page import= "logic.bean.ItemInSaleBean"  %>
<%@ page import="java.util.List" %>
<%
	WishlistController controller = new WishlistController();
	UserBean loggedUser = (UserBean)session.getAttribute("loggedUser");
	
	
	if(request.getParameter("removeItem")!=null){
		int itemID = Integer.parseInt((String)request.getParameter("removeItem"));
		controller.removeFromWishlist(loggedUser.getUserID(), itemID);	
	}
	
	List<ItemInSaleBean> wishlist = controller.getItemInWishlist(loggedUser);
%>
<!DOCTYPE html>
<html>
	<head>
		<meta charset="ISO-8859-1">
		<title>EE - Wishlist</title>
		<link rel="stylesheet" href="Style/Style.css">
		<link rel="stylesheet" href="Style/HeaderBar.css">
		<link rel="stylesheet" href="Style/Wishlist.css">
		<script src="https://ajax.googleapis.com/ajax/libs/jquery/3.5.1/jquery.min.js"></script>
	</head>
	<body class="bubble-background">
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
	</body>
	<script>
		$('#user').click(function(e){
			
			if($('#menu').css("visibility") == 'hidden')
				$('#menu').css("visibility", "visible");
			else
				$('#menu').css("visibility", "hidden");
		});	
	</script>
		
		<div class ="box" >
			<% for(ItemInSaleBean item: wishlist) {
			%>
				<div class ="item"> 
					<div class="infoLeft">
						<a href ="ItemDetails.jsp?itemID=<%= item.getItemID() %>" class="link"> <%= item.getItemName() %> </a>
						<%if (item.getAvailability()) { %>
							<p>Available</p>
						<%} else { %>
							<p style = "color:red;">Unavailable</p>
						<%} %>
					</div>
					<div class= "infoRight">
						<p> Seller: <%= item.getSeller().getUserID() %></p>
						<p> <%= item.getPrice() %> Coins</p>
					</div>
						<form action="Wishlist.jsp" name="myform" method="POST">
							<button id = "removebtn" class="orange-clickable" type="submit" name = "removeItem" value = "<%= item.getItemID()%>" style="float : right;"> REMOVE</button>
						</form>
				</div>
			<%} %>
		</div>
	</body>
</html>