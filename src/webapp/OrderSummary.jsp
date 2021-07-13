<%@ page import="java.util.List" %>
<%@ page import="java.util.ArrayList" %>
<%@ page import="logic.bean.UserBean" %>
<%@ page import="logic.bean.OrderBean" %>
<%@ page import="logic.bean.ItemDetailsBean" %>
<%@ page import ="logic.controller.application.ItemDetailsController"%>
<%@ page import ="logic.controller.application.WalletController" %>
<%@ page import ="logic.controller.application.SellController" %>
<%@page import="java.util.Date" %>
<%@ page import ="java.text.SimpleDateFormat" %>
<%@ page import ="java.text.DateFormat" %>
<%@ page import = "java.time.ZoneId" %>



<%! ItemDetailsBean item = new ItemDetailsBean(); %>
<%! ItemDetailsController iController = new ItemDetailsController(); %>
<%! String statusDate; %>
<%! String involvedUser; %>
<% if (session.getAttribute("loggedUser") == null){
	%>
	<jsp:forward page="Login.jsp"/>
	<% 
	} 
%>
<%!OrderBean order = new OrderBean(); %>
<% if (request.getParameter("selectedOrder") != null){
	SellController sController = new SellController();
	order = sController.generateOrderSummary(Integer.valueOf(((String)request.getParameter("selectedOrder"))));
	item = iController.getItemDetails(order.getInvolvedItem().getItemID());
	} 
%>




<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
	
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
    
    <div style="width: 709px; height: 600px; background-color: #FFFFFF; box-shadow: rgba(0, 0, 0, 0.35) 0px 5px 15px; margin: 86px auto">
    	<div>
    			<% if(!(order.getBuyer().getUserID().equals(loggedUser.getUserID()))) {
					involvedUser="Buyer: " + order.getBuyer().getUserID();
				}
				else {
					involvedUser="Seller: " + item.getSeller().getUserID();
				} %>
			<div style ="display:inline-block;margin-top:50px; margin-left:50px; float:left; width:200px; height:150px; background-color:red;">img placeholder</div>
			<div style="display:inline-block;margin-top:50px;margin-left:100px;font-size:15px;line-height:2;"><%=order.getInvolvedItem().getItemName() %><br>
																	Condition:<%=item.getCondition() %><br>
																	Price: <%=item.getPrice() %>coins<br>
																	<%=involvedUser %></div>
			

    	</div>
  		
    
    </div>

	</body>

</html>