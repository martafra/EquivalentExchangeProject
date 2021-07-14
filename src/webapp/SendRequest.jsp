<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>

<%@page import= "logic.bean.UserBean"  %>


 <%
 
	UserBean loggedUser = (UserBean)session.getAttribute("loggedUser");
 	if (request.getParameter("itemID")== null || request.getParameter("sellerID")== null || request.getParameter("itemName")==null){
		%>
		<jsp:forward page="Home.jsp"/>
		<% 
	}
 	int itemID = Integer.parseInt(request.getParameter("itemID"));
	String sellerID = request.getParameter("sellerID");
	String itemName = request.getParameter("itemName");
	
 	
 %>
<!DOCTYPE html>
<html>
	<head>
		<link rel="stylesheet" href="Style/Style.css">
		<link rel="stylesheet" href="Style/HeaderBar.css">
		<script src="https://ajax.googleapis.com/ajax/libs/jquery/3.5.1/jquery.min.js"></script>
		<meta charset="ISO-8859-1">
		<title>Insert title here</title>
		
	</head>
	
	<body class="bubble-background">
	<div id="headerbar">
            	<img id="logoImage" src="assets/images/logo.png" alt="logoImage">
           	 	<span><span style="color: #FF6A00">E</span>QUIVALENT <span style="color: #5AC02A">E</span>XCHANGE</span>


            	<span><a href="Home.jsp" class ="link">HOME</a></span>
            	<span><a href="Catalogue.jsp" class ="link">CATALOGUE</a></span>
            	<span>COMMUNITY</span>


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
		
		<form action="ItemDetails.jsp?itemID=<%=itemID %>" method="POST">
			<div style = "margin-top: 15%;margin-left:auto;margin-right:auto;width:800px">
				<p style ="display:inline-block; width:590px; font-size:15pt;"> Enter a message to send to <b><%=sellerID %></b> for the item <b><%= itemName %></b> : </p>
				<label style ="font-size:15pt; margin-left:3%;"> Max character: 300 </label>
     			<textarea name="requestText" maxlength="300" placeholder="Enter your text..." style = "width:800px;height:200px; font-size:15pt;"></textarea>
     		
     			<input class = "orange-clickable" type="submit" name ="send" value="SEND" style ="display:block; margin-top:2%;margin-left:90%">
     		</div>
		</form>
	</body>
</html>