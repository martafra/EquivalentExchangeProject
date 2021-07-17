<%@ page import="java.util.List" %>
<%@ page import="java.util.ArrayList" %>
<%@ page import="logic.bean.UserBean" %>
<%@ page import="logic.bean.OrderBean" %>
<%@ page import="logic.bean.OrderReviewBean" %>
<%@ page import ="logic.controller.application.BuyController" %>
<%@ page import ="logic.controller.application.SellController" %>


<%! BuyController bController = new BuyController(); %>
<%! SellController sController = new SellController(); %>
<%! List<OrderBean> prevOrders = new ArrayList<>(); %>
<% OrderBean order = ((OrderBean)(session.getAttribute("selectedOrder"))); %>
<% OrderReviewBean review = new OrderReviewBean(); %>
<% if (session.getAttribute("loggedUser") == null){
	%>
	<jsp:forward page="Login.jsp"/>
	<% 
	} 
%>
<% if (session.getAttribute("selectedOrder") == null){
	%>
	<jsp:forward page="Wallet.jsp"/>
	<%
} %>

<%	if (request.getParameter("insertRevBtn") !=null){
		if(request.getParameter("a") != null && request.getParameter("r") != null && request.getParameter("c") != null &&
		   request.getParameter("note") != null){
			review.setOrderID(order.getOrderID());
			review.setSellerAvailability( Integer.valueOf(((String)request.getParameter("a"))));
			review.setSellerReliability( Integer.valueOf(((String)request.getParameter("r"))));
			review.setItemCondition( Integer.valueOf(((String)request.getParameter("c"))));
			review.setBuyerNote( ((String)request.getParameter("note")) );
			bController.updateReview(review);
			%>
			<jsp:forward page="Wallet.jsp"/>
			<%
		}
		
} %>





<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html lang="en">
	
	<head>
		<title>EE - Order Review</title>
		<script src="https://ajax.googleapis.com/ajax/libs/jquery/3.5.1/jquery.min.js"></script>
		<link rel="stylesheet" href="Style/Style.css">
		<link rel="stylesheet" href="Style/HeaderBar.css">
		<link rel="stylesheet" href="Style/Profile.css">
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
    
    <div style="width: 709px; height: 600px; padding-top:20px; background-color: #FFFFFF; box-shadow: rgba(0, 0, 0, 0.35) 0px 5px 15px; margin: 86px auto">
  		
    	<table id="ratings" style="margin-left:40px;">
                <tr>
                    <td>Seller availability: </td>
                    <td>

                        <div class="rating">
                          <img src="assets/images/empty-star.png" alt="empty_star" id="a1" onClick="fillfoo('a1')">
                          <img src="assets/images/empty-star.png" alt="empty_star" id="a2" onClick="fillfoo('a2')">
                          <img src="assets/images/empty-star.png" alt="empty_star" id="a3" onClick="fillfoo('a3')">
                          <img src="assets/images/empty-star.png" alt="empty_star" id="a4" onClick="fillfoo('a4')">
                          <img src="assets/images/empty-star.png" alt="empty_star" id="a5" onClick="fillfoo('a5')"> 
                        </div>

                    </td>
                </tr>
                <tr>
                    <td>Seller reliability: </td>
                    <td>

                        <div class="rating">
							<img src="assets/images/empty-star.png" alt="empty_star" id="r1" onClick="fillfoo('r1')">
							<img src="assets/images/empty-star.png" alt="empty_star" id="r2" onClick="fillfoo('r2')">
							<img src="assets/images/empty-star.png" alt="empty_star" id="r3" onClick="fillfoo('r3')">
							<img src="assets/images/empty-star.png" alt="empty_star" id="r4" onClick="fillfoo('r4')">
							<img src="assets/images/empty-star.png" alt="empty_star" id="r5" onClick="fillfoo('r5')">
                        </div>

                    </td>
                </tr>
                <tr>
                    <td>Product condition: </td>
                    <td>

                        <div class="rating">
                            <img src="assets/images/empty-star.png" alt="empty_star" id="c1" onClick="fillfoo('c1')">
                            <img src="assets/images/empty-star.png" alt="empty_star" id="c2" onClick="fillfoo('c2')">
                            <img src="assets/images/empty-star.png" alt="empty_star" id="c3" onClick="fillfoo('c3')">
                            <img src="assets/images/empty-star.png" alt="empty_star" id="c4" onClick="fillfoo('c4')">
                            <img src="assets/images/empty-star.png" alt="empty_star" id="c5" onClick="fillfoo('c5')">
                        </div>

                    </td>
                </tr>

            </table>
    	<form action="OrderReview.jsp">
    		<input type = "text" style="height:0px; width:0px; visibility:hidden" name="a" id="a">
    		<input type = "text" style="height:0px; width:0px; visibility:hidden" name="r" id="r">
    		<input type = "text" style="height:0px; width:0px; visibility:hidden" name="c" id="c">
    	<div>
    		<textarea id="note" name="note" placeholder = "Rate your purchase..." style = "width: 480px; height: 240px; display: inline-block; margin-top:50px; margin-left:50px; resize:none"></textarea>
    	</div>
    	<div>
    		<input type="submit" id="insertRevBtn" name="insertRevBtn" value="Insert review" class="orange-clickable" style ="height:34px; width:150px; display:inline.block; margin-top:50px;margin-left:50px;">
    	</div>
    	</form>
    </div>


	</body>
	
	<script type="text/javascript">
		function fillfoo(id){
			var f = id.charAt(0);
			var s = parseInt(id.charAt(1));
			document.getElementById(f).setAttribute("value",s*2);
			for(var i = 1; i<=5; i++){
				if(i<=s){
					console.log(f+i);
					document.getElementById(f+i).src = "assets/images/full-star.png";
				}
				else{
					document.getElementById(f+i).src = "assets/images/empty-star.png";
				}
			}
		}
	</script>

</html>