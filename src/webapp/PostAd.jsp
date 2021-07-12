<%@ page import="logic.controller.application.ItemAdController" %>
<%@ page import="logic.controller.application.ItemRetrieveController" %>
<%@ page import="logic.bean.ItemDetailsBean" %>
<%@ page import="logic.bean.ItemBean" %>
<%@ page import="logic.bean.UserBean" %>
<%@ page import="logic.bean.ItemBean" %>
<%@ page import="java.util.List" %>
<%@ page import="java.util.Map" %>
<%@ page import="java.util.HashMap" %>
<%@ page import="java.util.ArrayList" %>

<jsp:useBean id = "adBean" scope = "request" class = "logic.bean.ItemDetailsBean"/>



<%!List<String> itemConditions = new ArrayList<>();%>
<%!List<ItemBean> items = new ArrayList<>();%>
<%!Map<String, ItemBean> itemMap = new HashMap<>(); %>
<%!ItemAdController itemController = new ItemAdController(); %>
<%!ItemRetrieveController itemRetController = new ItemRetrieveController(); %>
<%!List<String> itemTypes = new ArrayList<>(List.of("Book", "Movie", "Videogame"));%>
<%!List<ItemBean> itemList = new ArrayList<>(); %>
<% UserBean loggedUser = (UserBean)session.getAttribute("loggedUser");%>
<% 
	
	itemConditions = itemController.getConditionTypes();

%>

<% 
	if(request.getParameter("publishBtn")!=null){
		adBean = new ItemDetailsBean();
		adBean.setReferredItemID(itemMap.get(((String)request.getParameter("referredItem"))).getItemID());
		adBean.setPrice(Integer.valueOf(((String)request.getParameter("price"))));
		adBean.setDescription((String)request.getParameter("description"));
		adBean.setAddress((String)request.getParameter("address"));
		adBean.setCondition((String)request.getParameter("condition"));
		adBean.setSeller(loggedUser);
		if(Boolean.TRUE.equals(itemController.post(adBean))){
			%>
    		<jsp:forward page="Home.jsp"/>
		<% 
		}
	}
%>

<% 
	if(request.getParameter("type")!=null){
		if(((String)request.getParameter("type")).equals("Book")){
			items = itemRetController.getBooksList();
		}
		if(((String)request.getParameter("type")).equals("Movie")){
			items = itemRetController.getMoviesList();
		}
		if(((String)request.getParameter("type")).equals("Videogame")){
			items = itemRetController.getVideogamesList();
		}
		
		for(ItemBean item: items){
			itemMap.put(item.getItemName(), item);
		}
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
		
		<div style="width:1010px; margin: 76px auto">
		<form action="PostAd.jsp" name = "publishAD" method = "POST">
			<div>			
				<input type="text" id="price" name="price" placeholder = "Type here the price of your product..." style = "width: 454px; height: 35px; display: inline-block">
				
				<select name="type" id="type" style = "width: 251px; height: 35px; background-color:#FF6A00; display:block; margin:0 auto; float:right;">
					<option value="item type">item type</option>
    				<% 
						for(String type: itemTypes){
					%>
						<option value = "<%= type%>"><%= type%></option>
					<% } %>
  				</select>	
			</div>
			
			
			<div style ="margin: 34px auto">
				<textarea id="description" name="description" placeholder = "Type here the description of your product..." style = "width: 454px; height: 177px; display: inline-block; resize:none"><% adBean.getDescription(); %></textarea>
				<select name="referredItem" id="referredItem" style = "width: 430px; height: 35px; color:#5AC02A; margin: auto; float: right">
				<option value="" disabled selected>referred item</option>
					<% 
						for(ItemBean item: items){
					%>
						<option value = "<%= item.getItemName() %>"><%= item.getItemName()%></option>
					<% } %>
  				</select>
				
			</div>
			
			<div style ="margin: 34px auto">
				<input type="text" id="address" name="address" value = "<% adBean.getAddress(); %>" placeholder = "Type here where you want to sell your product..." style = "width: 454px; height: 35px; display: inline-block;">
				<input type="submit" id="publishBtn" name="publishBtn" value="Publish" class="orange-clickable" style ="height:35px; width:94px; margin: auto; float: right;" />
			</div>
			
			
			<div style ="margin: 34px auto">
				<select name="condition" id="condition" style = "width: 251px; height: 35px; background-color:#FF6A00">
				<option value="" disabled selected>condition</option>
					<% 
						for(String condition: itemConditions){
					%>
						<option value = "<%= condition%>"><%= condition%></option>
					<% } %>
  				</select>
			</div>
			
			<div>
			<input type="submit" id="addImg" name="addImg" value="+" class="orange-clickable" style ="height:115px; width:64px; display:inline-block;" />
			</div>

		
		</form>
		</div>		
		
		
		
	</body>
	
	<script type="text/javascript">
		$('#type').on('change', function(){
        $(this).closest('form').submit();
    	});
	</script>
	
	

</html>