<%@ page import="java.util.List" %>
<%@ page import="java.util.ArrayList" %>
<%@ page import="logic.bean.UserBean" %>
<%@ page import="logic.bean.ItemInSaleBean" %>
<%@ page import="logic.bean.RequestBean" %>
<%@ page import="logic.bean.OrderReviewBean" %>
<%@ page import="logic.controller.application.SellController" %>
<%@ page import="logic.controller.application.ProfileController" %>
<%@page import="java.util.HashMap" %>
<%@page import="java.util.List" %>
<%@page import="java.util.ArrayList" %>


<%	if (session.getAttribute("loggedUser") == null){
	%>
	<jsp:forward page="Login.jsp"/>
	<% 
	} 
%>

<%!SellController sController = new SellController(); %>
<%!ProfileController pController = new ProfileController(); %>
<%List<RequestBean> requests = sController.getRequestList(((UserBean)session.getAttribute("loggedUser")));%>
<%List<OrderReviewBean> reviews = pController.getReviewList(((UserBean)session.getAttribute("loggedUser"))); %>

<%	HashMap<String, RequestBean> reqMap = new HashMap<>();
	for(RequestBean req : requests){
		reqMap.put(req.getBuyer().concat((req.getReferredItem().toString())), req);}					
						
%>
<%List<ItemInSaleBean> items = sController.getItemList(((UserBean)session.getAttribute("loggedUser"))); %>
<%	if(request.getParameter("accept") != null && reqMap.get((request.getParameter("reqBean")))!=null){
		sController.acceptRequest(reqMap.get(request.getParameter("reqBean")));
		requests = sController.getRequestList(((UserBean)session.getAttribute("loggedUser")));
		reqMap.remove(request.getParameter("reqBean"));
		items = sController.getItemList(((UserBean)session.getAttribute("loggedUser")));
		reviews = pController.getReviewList(((UserBean)session.getAttribute("loggedUser")));
} %>
<%	if( request.getParameter("reject") != null && reqMap.get((request.getParameter("reqBean")))!=null){
		sController.rejectRequest(reqMap.get(request.getParameter("reqBean")));
		reqMap.remove(request.getParameter("reqBean"));
		items = sController.getItemList(((UserBean)session.getAttribute("loggedUser")));
		reviews = pController.getReviewList(((UserBean)session.getAttribute("loggedUser")));
} %>



<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
	
	<head>
		<title>EE - Seller Panel</title>
		<script src="https://ajax.googleapis.com/ajax/libs/jquery/3.5.1/jquery.min.js"></script>
		<link rel="stylesheet" href="Style/Style.css">
		<link rel="stylesheet" href="Style/HeaderBar.css">
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

	<script>
        $('#user').click(function(e){

            if($('#menu').css("visibility") == 'hidden')
                $('#menu').css("visibility", "visible");
            else
                $('#menu').css("visibility", "hidden");
        });
    </script>

	
		<div style ="width:52%;height:1080px;display:inline-block;float:left">
   			
   			<div style="height:50%; margin-top:5%;margin-left:2%; background-color:#FFFFFF; box-shadow: rgba(0, 0, 0, 0.35) 0px 5px 15px;">
   				<div style="margin-top:65px;margin-left:2px;height:25px;font-size:16;color:#FF6A00">Products in sale<div style="float:right; margin-right:10px"><a href="PostAd.jsp">post an ad</a></div></div>
   				
   				<div style="width:100%;height:96%;display:inline-block;overflow:scroll">
   					<div>
		
					<%for(ItemInSaleBean item : items){
						Integer price = item.getPrice();
						String name = item.getItemName();
					%>
					<div style="background-color:#D7E9D8; display:inline-block; margin-top:10px;margin-left:5px; width:180px;height:250px;">
						<div style = "width:125px;height:125px;margin-top:15px;margin:10px auto;"><img src="file?path=<%=item.getMediaPath() %>" alt="missing" style="height:100%;width:100%;"></div>
						<div style="font-size:14px; margin-top:15px; text-align:center"><%=name %><br><br><%=price %>coins</div>
					</div>
					<% } %>
					</div>
					
   				</div>
   				
   			
   			</div>
   			
   			<div style="height:35.0%; margin-top:5%; margin-left:2%;background-color:#FFFFFF; box-shadow: rgba(0, 0, 0, 0.35) 0px 5px 15px;">
   				<div style="margin-top:5px;margin-left:2px;height:25px;font-size:16;color:#FF6A00">Requests from buyers</div>
   				
   				<div style="width:100%;height:97%;display:inline-block;overflow:scroll">
   					<div>
		
					<%for(String key : reqMap.keySet()){
						RequestBean reqB = reqMap.get(key);
						
					%>
					<div style="width:100%;height:80px;border-width:0px 0px 2px 0px;border-style:solid;border-color:#D4CEAB;">
						<div style = "width:70px;height:70px;display:inline-block;margin-top:5px;margin-left:10px;float:left"><img src="file?path=<%=reqB.getReferredItemBean().getMediaPath() %>" alt="missing" style="height:100%;width:100%;"></div>
						<div style = "display:inline-block;font-size:14px;margin-top:8px;margin-left:15px;float:left;"><%=reqB.getReferredItemBean().getItemName() %><br><br><br><%= reqB.getNote() %></div>
						<div style="display:inline-block;font-size:14px;margin-top:8px;margin-left:350px;float:left;"><%=reqB.getBuyer() %></div>
						
						<form action="SellerPanel.jsp" method="POST">
						<div style="height:80px; width:80px; float:right">
							<input type="text" style="height:0px;width:0px;visibility:hidden" name="reqBean" value="<%=key%>">
							<div><input type="submit" value="accept" name="accept" class="green-clickable-big" style="width:90px;height:30px;margin-top:5px;font-size:12px;margin-right:5px;float:right;"></div>
							<div><input type="submit" value="reject" name="reject" class="green-clickable-big" style="width:90px;height:30px;margin-top:5px;font-size:12px;margin-right:5px;float:right;"></div>
						</div>
						</form>
					</div>
					<% } %>
					</div>
					
   				</div>
   			
   			</div>
   		
   		</div>
   		
   		<div style="height:1080px;width:47.5%;display:inline-block;float:right">
   		
   			<div style="height:50.0%; margin-top:5%; margin-right:2%;background-color:#FFFFFF; box-shadow: rgba(0, 0, 0, 0.35) 0px 5px 15px;">
   				<div style="margin-top:65px;margin-left:2px;height:25px;font-size:16;color:#FF6A00">Average rating</div>
   				
   			</div>
   			
   			<div style="height:35.0%; margin-top:5%; margin-right:2%;background-color:#FFFFFF; box-shadow: rgba(0, 0, 0, 0.35) 0px 5px 15px;">
   				<div style="margin-top:5px;margin-left:2px;height:25px;font-size:16;color:#FF6A00">Reviews from buyers</div>
   				<div style="width:100%;height:97%;display:inline-block;overflow:scroll">
   					<div>
		
					<%for(OrderReviewBean review: reviews){
						
						
					%>
					<div style="width:100%;height:150px;border-width:0px 0px 2px 0px;border-style:solid;border-color:#D4CEAB;">
						mettere qui stelline
					</div>
					<% } %>
					</div>
					
   				</div>
   			</div>
   			
   			
   		</div>

	</body>

</html>