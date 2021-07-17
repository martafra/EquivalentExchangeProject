<%@page import="logic.bean.UserBean" %>



<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html lang="en">
	
	<head>
		<title>EE - Home</title>
		<link rel="stylesheet" href="Style/Style.css">
		<link rel="stylesheet" href="Style/HeaderBar.css">
		<script src="https://ajax.googleapis.com/ajax/libs/jquery/3.5.1/jquery.min.js"></script>
		<style>
			
			.HomeImage{
				width: 250px;
				height: 250px;
			}
		
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
			<%
				UserBean loggedUser = ((UserBean)session.getAttribute("loggedUser"));
				
				if(loggedUser == null){ %>
    				
    					<a href="Login.jsp"><input style="margin: 5px auto" type="button" name="Login" value="Login" id="loginButton" class="orange-clickable"></a>
    				
				<% }else {%>
						<div class="loggedUserLabel" id="user">
							<div> <%=loggedUser.getUserID()%> </div>
							<img src="file?path=<%=loggedUser.getProfilePicPath() %>" onerror="this.src='assets/images/avatar.png';" alt="e"/>
						
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
			
			<div style="width:100%; display: flex; justify-content: center; margin-top: 20px;">
			<table style="justify-content: center;">
				
				<tr><td>
				<a href="Catalogue.jsp" style="margin: 125px;">
					<img class="HomeImage" src="assets/images/buy.png" alt="buy">
				</a>
				</td></tr>
				<tr><td>
				<p style="width: 500px; text-align: center;">if you don't have enough credit to buy an item right now, or simply you would like to track the price of an object to be notified if it goes down at any time,then add the item to your wishlist and never worry again about the deals you may miss!</p>
				</td></tr>
			</table>	
			</div>
			
			<div style="width:100%; display: flex; justify-content: center; margin-top: 20px;">
			<table style="justify-content: center;">
				<tr><td>
				<a href="Wishlist.jsp" style="margin: 125px;">
					<img class="HomeImage" src="assets/images/wishlist.png" alt="wishlist">
				</a>
				</td></tr>
				<tr><td>
				<p style="width: 500px; text-align: center;">if you do have the same passions as ours, you will be surelly full of plenty of old items like books or DVDs, so give them a new life selling these articles on our marketplace. You can trade all your stuff for in-app currency, that you can spend for other incredible stuff that other users want to sell. All of this without taking and to your wallet!</p>
				</td></tr>
			</table>
			
			<div style="width: 30%;"></div>
							
			<table style="justify-content: center;">
				<tr><td>
				<a href="ReviewerPanel.jsp" style="margin: 125px;">
					<img class="HomeImage" src="assets/images/article.png" alt="write article">
				</a>
				</td></tr>
				<tr><td>
				<p style="width: 500px; text-align: center;">Write down your toughts about your favorite books, movies and videogames, in the form of a professional review or, in case you are a gamer, share your tips and tricks with other people by writing a guide or a tutorial.Every contribute to the community is essential and will be rewarded with in-app currency!</p>
				</td></tr>
			</table>
			</div>
			
			<div style="width: 50%; margin: 10px 25%;">
			<p style="width: 100%; text-align: center;"><strong>OUR MISSION</strong></p>
			<p style="width: 100%; text-align: center;">Our goal is to create a place where people with the same passions can share them with others.We want to create a community where anyone can trade their used goods, from videogames to books,to let these objects to have a second life, also allowing his previous owner to make a profit out of them.</p>
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
</html>