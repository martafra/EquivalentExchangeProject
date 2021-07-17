<%@ page import="java.util.List" %>
<%@ page import="java.util.ArrayList" %>
<%@ page import="logic.bean.UserBean" %>
<%@ page import="logic.bean.OrderBean" %>
<%@ page import="logic.bean.ItemDetailsBean" %>
<%@ page import ="logic.controller.application.ItemDetailsController"%>
<%@ page import ="logic.controller.application.WalletController" %>
<%@ page import ="logic.controller.application.SellController" %>
<%@ page import ="logic.controller.application.BuyController" %>
<%@ page import="java.util.Date" %>
<%@ page import ="java.text.SimpleDateFormat" %>
<%@ page import ="java.text.DateFormat" %>
<%@ page import = "java.time.ZoneId" %>

<jsp:useBean id = "order" scope = "session" class = "logic.bean.OrderBean"/>


<%! ItemDetailsBean item = new ItemDetailsBean(); %>
<%! ItemDetailsController iController = new ItemDetailsController(); %>
<%! SellController sController = new SellController(); %>
<%! String statusDate; %>
<%! String sellerStatus; %>
<%! String buyerStatus; %>
<%! String startDate; %>
<%! String endDate; %>
<%! String involvedUser; %>
<%! String codeType;%>
<%! String verifyAb; %>
<%! String verifyVis; %>
<%! String codeField; %>
<%! String purchaseVis; %>
<%! String purchaseAb; %>

<%	if (session.getAttribute("loggedUser") == null){
	%>
	<jsp:forward page="Login.jsp"/>
	<% 
	} 
%>

<%	if (request.getParameter("verifyBtn")!=null){
		order.setCode(((String)request.getParameter("code")));
		if(Boolean.TRUE.equals(sController.verifyPaymentCode(order))) {
			%>
			<jsp:forward page="Wallet.jsp"/>
			<% 
		}
} %>

<%!OrderBean order = new OrderBean(); %>
<% if (request.getParameter("selectedOrder") != null){
	order = sController.generateOrderSummary(Integer.valueOf(((String)request.getParameter("selectedOrder"))));
	session.setAttribute("selectedOrder", order);
	item = iController.getItemDetails(order.getInvolvedItem().getItemID());
	} 
%>


<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html lang="en">
	
	<head>
		<title>EE - Order Summary</title>
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
    
    <div style="width: 40%; height: 650px; background-color: #FFFFFF; box-shadow: rgba(0, 0, 0, 0.35) 0px 5px 15px; margin: 106px auto">
    	<div>
    			<% if(!(order.getBuyer().getUserID().equals(loggedUser.getUserID()))) {
					involvedUser="Buyer: " + order.getBuyer().getUserID();
				}
				else {
					involvedUser="Seller: " + item.getSeller().getUserID();
				} %>
			<div style ="display:inline-block;margin-top:50px; margin-left:50px; float:left; width:200px; height:150px;"><img src="file?path=<%=order.getInvolvedItem().getMediaPath() %>" alt="missing" style="height:100%;width:100%;"></div>
			<div style="display:inline-block;margin-top:50px;margin-left:100px;font-size:15px;line-height:2;"><%=order.getInvolvedItem().getItemName() %><br>
																	Condition:<%=item.getCondition() %><br>
																	Price: <%=item.getPrice() %>coins<br>
																	<%=involvedUser %></div>
			<div style ="display:block;margin-top:60px; margin-left:50px;font-size:15px;line-height:2">
				<% if(order.getSellerStatus()) {
					sellerStatus = "Order accepted by seller";
				}
				else {
					sellerStatus = "Order not accepted by seller";
				}
				if(order.getBuyerStatus()) {
					buyerStatus = "Order accepted by buyer";
				}
				else {
					buyerStatus = "Order not accepted by buyer";
				} %>
				<%=sellerStatus %><br><%=buyerStatus %>
			</div>
			<div style ="display:block;margin-top:20px; margin-left:50px;font-size:15px;line-height:2">
				<%  DateFormat format = new SimpleDateFormat("dd/MM/yyyy");
					if( order.getStartDate() != null)
						startDate ="Order started on: " + format.format(order.getStartDate());
					else{startDate="Order not started yet";}
					if( order.getOrderDate() != null )
						endDate="Order finished on: " + format.format(order.getOrderDate());
					else{endDate = "";}%>
				<%=startDate %><br><%=endDate %>
			</div>
			<div style = "margin-top:20px; margin-left:50px;font-size:15px">
				<div >Payment code:</div>
				<form>
				<%  if(loggedUser.getUserID().equals(order.getBuyer().getUserID()) || order.getOrderDate()!= null || order.getStartDate()==null){
						codeField = order.getCode();
						codeType="readonly";
						verifyAb="disabled";
						verifyVis="visibility:hidden";
						codeField=order.getCode();}
					else{codeType="";verifyAb="";verifyVis="";codeField="";}
				%>
				<input type="text" style="display:inline-block;width:221px; height:35px;" id="code" name="code" value="<%= codeField %>" <%=codeType %>>
				<input type="submit" id="verifyBtn" name="verifyBtn" value="Verify" class="orange-clickable" style ="display:inline-block; height:35px; width:94px;<%=verifyVis%>"<%= verifyAb %>>
				</form>
				<div id="timer" style="display:inline-block"></div>
			</div>
			<form action="OrderReview.jsp" method="POST">
				<div>
					<% if( (loggedUser.getUserID().equals(order.getBuyer().getUserID())) && (order.getReview() == null)){
						purchaseVis="";
						purchaseAb="";
					}
					if( (!loggedUser.getUserID().equals(order.getBuyer().getUserID())) || (order.getReview() != null) || (order.getOrderDate() == null)){
						purchaseVis="visibility:hidden";
						purchaseAb="disabled";
					} %>
					<input type="submit" id="ratePurchase" name="ratePurchase" value="Rate your purchase" class="orange-clickable" style ="display:inline-block; height:35px; width:221px;margin-top:20px; margin-left:50px;<%=purchaseVis%>"<%=purchaseAb %>>
					<input type="text" id="selectedOrderReview" name="selectedOrderReview" value="<%= order.getOrderID() %>" style="visibility:hidden">				
				</div>
			</form>		

    	</div>
  		
    
    </div>

	</body>
	
	<script type="text/javascript">
	<%if ((new BuyController().checkRemainingTime(order))>0){
		%>timer = setInterval(myTimer, 1000);
		<%
	}%>
	
	<%Integer remTime = (new BuyController().checkRemainingTime(order));%>;
	var d = <%=remTime%>; 
	function myTimer() {
	  d = d-1;
	  if(d <= 0){
	  	clearInterval(timer)
	    window.location.replace("Wallet.jsp");
	  }
	  document.getElementById("timer").innerHTML = sec2hms(d);
	}

	function sec2hms(timect){

	  if(timect=== undefined||timect==0||timect === null){return ''};
	  var se=timect % 60; 
	  timect = Math.floor(timect/60);
	  var mi=timect % 60; 
	  timect = Math.floor(timect/60);
	  var hr = timect % 24; 
	  var dy = Math.floor(timect/24);
	  return padify (se, mi, hr, dy);
	}

	function padify (se, mi, hr, dy){
	  hr = hr<10?"0"+hr:hr;
	  mi = mi<10?"0"+mi:mi;
	  se = se<10?"0"+se:se;
	  dy = dy>0?dy+"d ":"";
	  return dy+hr+":"+mi+":"+se;
	}
	</script>

</html>