<%@ page import="java.util.List" %>
<%@ page import="java.util.ArrayList" %>
<%@ page import="logic.bean.UserBean" %>
<%@ page import="logic.bean.OrderBean" %>
<%@ page import ="logic.controller.application.BuyController" %>
<%@ page import ="logic.controller.application.SellController" %>




<%! BuyController bController = new BuyController(); %>
<%! SellController sController = new SellController(); %>
<%! List<OrderBean> prevOrders = new ArrayList<>(); %>
<% OrderBean order = new OrderBean(); %>
<% if (session.getAttribute("loggedUser") == null){
	%>
	<jsp:forward page="Login.jsp"/>
	<% 
	} 
%>
<%	if (request.getParameter("ratePurchase")!=null){
		order = sController.generateOrderSummary( Integer.valueOf(request.getParameter("selectedOrderReview")) );
} %>





<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
	
	<head>
		<title>EE - Order Review</title>
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


            <span>HOME</span>
            <span>CATALOGUE</span>
            <span>COMMUNITY</span>


            <div id="login">
            <%
                UserBean loggedUser = ((UserBean)session.getAttribute("loggedUser"));
                if(loggedUser==null){ %>

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
    
    <div style="width: 709px; height: 600px; padding-top:20px; background-color: #FFFFFF; box-shadow: rgba(0, 0, 0, 0.35) 0px 5px 15px; margin: 86px auto">
  
    	<div style ="margin-top:50px;margin-left:50px;font-size:15px;">Seller reliability</div>
    	<div style ="margin-top:20px;margin-left:50px;font-size:15px;">Seller availability</div>
    	<div style ="margin-top:20px;margin-left:50px;font-size:15px;">Product condition</div>
    	
    	<div>
    		<textarea id="description" name="description" placeholder = "Rate your purchase..." style = "width: 480px; height: 240px; display: inline-block; margin-top:50px; margin-left:50px; resize:none"></textarea>
    	</div>
    	<div>
    		<input type="submit" id="insertRevBtn" name="insertRevBtn" value="Insert review" class="orange-clickable" style ="height:34px; width:150px; display:inline.block; margin-top:50px;margin-left:50px;">
    	</div>
    	
    </div>


	</body>

</html>