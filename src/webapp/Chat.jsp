<%@ page import="logic.bean.UserBean" %>
<%@ page import="logic.bean.ChatBean" %>
<%@ page import="logic.bean.OrderBean" %>
<%@ page import="logic.controller.application.ChatController" %>
<%@ page import="java.util.List" %>
<%@ page import="java.util.ArrayList" %>
<%@ page import="java.text.SimpleDateFormat" %>
<%@ page import="logic.support.interfaces.SaleController" %>
<%@ page import="logic.controller.application.SellController" %>
<%@ page import="logic.controller.application.BuyController" %>

<jsp:useBean id = "currentChat" scope = "session" class = "logic.bean.UserBean"/>

<%

	UserBean loggedUser = (UserBean) session.getAttribute("loggedUser");
	if(loggedUser == null){
	 %>  <jsp:forward page="Login.jsp"/>  <%
	}
	
	ChatController controller = new ChatController();
	List<UserBean> activeChats = controller.getActiveChats(loggedUser);
	if(request.getParameter("selectedUser") != null){
		
		String selectedUserID = request.getParameter("selectedUser");
		for(UserBean user : activeChats){
			System.out.println(user.getUserID() + "==" + selectedUserID);
			if(user.getUserID().equals(selectedUserID)){
				
				currentChat = user;
				session.setAttribute("currentChat", currentChat);
				break;
			}
		}
	}else if(session.getAttribute("currentChat") != null){
		currentChat = (UserBean)session.getAttribute("currentChat");
	}else{
		currentChat = activeChats.get(0);
	}
	
	OrderBean order = controller.getActiveOrderByUsers(loggedUser, currentChat, false);
	
	if(order != null){
		Integer result = new BuyController().checkRemainingTime(order);
		if(result == 0 || result == -2)
			order = null;
	}
	
	SaleController sController;
	
	if(order != null){
	if(order.getBuyer().getUserID().equals(loggedUser.getUserID())){
		sController = new BuyController();
	}else{
		sController = new SellController();
	}
	
	if(request.getParameter("acceptOrder") != null){
		sController.acceptOrder(order);
		order = null;	
	}
	else if(request.getParameter("rejectOrder") != null){
		sController.rejectOrder(order);
		order = null;
	}
	
	}
	
	
	if(request.getParameter("messageButton") != null && request.getParameter("chatMessage") != null){
		
		String messageText = (String) request.getParameter("chatMessage");
		ChatBean newMessage = new ChatBean();
		newMessage.setMessageText(messageText);
		newMessage.setSender(loggedUser.getUserID());
		newMessage.setReceiver(currentChat.getUserID());
		controller.sendMessage(newMessage);
	}
	
	List<ChatBean> messages = new ArrayList<>();
	if(currentChat != null){
		messages = controller.getMessagesByUser(loggedUser, currentChat);
		
		for(ChatBean message : messages){
			System.out.println(message.getMessageText());
		}
		
	}
	
	
%>


<html>
	
	<head>
		<title>EE - Home</title>
		<link rel="stylesheet" href="Style/Style.css">
		<link rel="stylesheet" href="Style/HeaderBar.css">
		<link rel="stylesheet" href="Style/Chat.css" >
		<script src="https://ajax.googleapis.com/ajax/libs/jquery/3.5.1/jquery.min.js"></script>
		<style>
		</style>
	</head>

	<body>
		
		<div id="headerbar">
			<img id="logoImage" src="assets/images/logo.png" alt="logoImage">
			<span><span style="color: #FF6A00">E</span>QUIVALENT <span style="color: #5AC02A">E</span>XCHANGE</span>
			
			
			<span>HOME</span>
			<span>CATALOGUE</span>
			<span>COMMUNITY</span>
			
			
			<div id="login">
			<%
				if(loggedUser == null){ %>
    				
    					<a href="Login.jsp"><input style="margin: 5px auto" type="button" name="Login" value="Login" id="loginButton" class="orange-clickable"></a>
    				
				<% }else {%>
						<div class="loggedUserLabel" id="user">
							<div> <%=loggedUser.getUserID()%> </div>
							<img src="file?path=<%=loggedUser.getProfilePicPath() %>" alt="e"/>
						
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
		
		<div id="chat">
			
			<div id="usersChatBox">
			<form>
				<%
					
					for(UserBean activeChat : activeChats){
						
						String username = activeChat.getUserID();
						
						%> 
						   <div id="userBox"> <input type="submit" name="selectedUser" value="<%=username%>"> </div>
						<%
						
					}
				
				%>
			</form>	
			</div>
			<div id="chatView">
				<div id="chatHeader">
					
					<div id="currentChatUsername"><%= currentChat.getUserID() %></div>
					
				</div>
				
				<% if(order != null){ 
						String orderName = order.getInvolvedItem().getItemName();
				%>
					
					<div id="currentOrderContainer">
					<div id="orderName"><%=orderName%> Started On</div>
					
						<form>
							<input type="submit" name="rejectOrder" value="reject"/>
							<input type="submit" name="acceptOrder" value="accept"/>
						</form>
					
					</div>
				<%} %>
					
				<div id="chatBody" class="bubble-background">
				
					<% 
						for(ChatBean message : messages){
							String text = message.getMessageText();
							String date = new SimpleDateFormat("HH:mm").format(message.getDate());
							String orientation = "left";
							if(message.getSender().equals(loggedUser.getUserID())){
								orientation = "right";
							}
							
							%>
								<div class="message">
									<div class="messageBox" style="float: <%=orientation %>;">
										<div class="text"><%=text%></div>
										<div class="date"><%=date%></div>
									</div>
									
								</div>
								
							<%
						}
					
					%>
				
				
				</div>
				<div id="sendMessage">
				
				<form>
					<input type="text" name="chatMessage" placeholder="Write a message here..." id="messageBoxText"/>
					<input type="submit" name="messageButton" id="messageButton"/>
				</form>
				
				</div>
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
