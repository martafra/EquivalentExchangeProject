<%@ page import="java.util.List" %>
<%@ page import="java.util.ArrayList" %>
<%@ page import="logic.bean.UserBean" %>
<%@ page import="logic.bean.OrderBean" %>
<%@ page import ="logic.controller.application.WalletController" %>
<%@ page import ="logic.controller.application.BuyController" %>
<%@page import="java.util.Date" %>
<%@ page import ="java.text.SimpleDateFormat" %>
<%@ page import ="java.text.DateFormat" %>
<%@ page import = "java.time.ZoneId" %>


<%! WalletController wController = new WalletController();%>
<%! BuyController bController = new BuyController(); %>
<%! List<OrderBean> prevOrders = new ArrayList<>(); %>
<%! String statusDate; %>
<%! String involvedUser; %>
<% if (session.getAttribute("loggedUser") == null){
	%>
	<jsp:forward page="Login.jsp"/>
	<% 
	} 
%>

<% prevOrders = wController.getOrderList((UserBean)session.getAttribute("loggedUser")); %>




<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html lang="en">
	
	<head>
		<title>EE - Wallet</title>
		<script src="https://ajax.googleapis.com/ajax/libs/jquery/3.5.1/jquery.min.js"></script>
		<link rel="stylesheet" href="Style/Style.css">
		<link rel="stylesheet" href="Style/HeaderBar.css">
		<style>
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
                if(loggedUser==null){ %>

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

        </div>

	<script>
        $('#user').click(function(e){

            if($('#menu').css("visibility") == 'hidden')
                $('#menu').css("visibility", "visible");
            else
                $('#menu').css("visibility", "hidden");
        });
    </script>
    
    <div style="width: 709px; height: 80px; background-color: #FFFFFF; box-shadow: rgba(0, 0, 0, 0.35) 0px 5px 15px; margin: 86px auto">
    	
    	<div style="padding-top:30px">
    		<div style="text-align:center"> <%= wController.getCredit(loggedUser).toString() %> </div>
    	</div>
    	<div style = "width:709px; height:800%; overflow: scroll; background-color:#FFFFFF; box-shadow: rgba(0, 0, 0, 0.35) 0px 5px 15px; margin:66px auto">
			<%	for (OrderBean prevOrder: prevOrders){
					bController.checkRemainingTime(prevOrder);
			} %>
			<% 	List<OrderBean> orders = wController.getOrderList(loggedUser);
				for(OrderBean order: orders){
				
				%><div style="height:120px;width:100%;border-bottom-style:solid;border-bottom-widht:2px;border-color: #D4CEAB;">
					<% 
					DateFormat format = new SimpleDateFormat("dd/MM/yyyy");
					if(order.getOrderDate() != null) {
						statusDate = "finished on: " + format.format(order.getOrderDate());
					}
					else if(order.getStartDate() != null) {
						statusDate = "started on: " + format.format(order.getStartDate());
					}
					else {
						statusDate = "In Progress";
					} %>
					<% if(!(order.getBuyer().getUserID().equals(loggedUser.getUserID()))) {
						involvedUser="Buyer: " + order.getBuyer().getUserID();
					}
					else {
						involvedUser="Seller: " + order.getInvolvedItem().getSeller().getUserID();
					} %>
					<div>
						<div style="height:80px;width:80px;margin-top:20px; margin-left:15px; display:inline-block; float:left"><img src="file?path=<%=order.getInvolvedItem().getMediaPath() %>" alt="missing" onerror="this.src='assets/images/missing.png';" style="height:100%;width:100%;"></div>
						<div style="width:220px;margin-left:35px;margin-top:0px;display:inline-block;float:left;">
							<div style=""><form action="OrderSummary.jsp" name = "orderSum" method = "POST"><div style="display:inline-block;font-size:12px">Order ID: #<input type="submit" name="selectedOrder" style="display:inline-block;margin-top:24px;font-size:12px;border-color:#FFFFFF;background-color:#FFFFFF;border:0px" value="<%= order.getOrderID() %>"></div></form></div>
							<div style="margin-top:40px;font-size:12px;"><%= order.getInvolvedItem().getItemName() %></div>
						</div>
						<div style="width:160px;display:inline-block;margin-left:20px;margin-top:24px;float:left">
							<div style="font-size:12px;color:#797979;"><%=statusDate %></div>
							<div style="margin-top:40px;font-size:12px;"><%=involvedUser %></div>
						</div>
						<div style="display:inline-block; margin-right:30px;margin-top:24px;float:right">
							<div style="margin-top:0px;font-size:12px;"><%= order.getInvolvedItem().getPrice() %>coins</div>
						</div>
					</div>
					
					
				</div>
			<% } %>
	
		</div>
    
    </div>


	</body>

</html>