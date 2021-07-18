<%@ page import="logic.controller.application.ItemRetrieveController" %>
<%@ page import="logic.controller.application.WriteReviewController" %>
<%@ page import="logic.support.exception.MissingArticleParametersException" %>
<%@ page import="logic.bean.ArticleBean" %>
<%@ page import="logic.bean.UserBean" %>
<%@ page import="logic.bean.ItemBean" %>
<%@ page import="java.util.List" %>
<%@ page import="java.util.ArrayList" %>
<%@ page import="java.util.Map" %>
<%@ page import="java.util.HashMap" %>
<%!Map<String, ItemBean> itemMap = new HashMap<>(); %>
<%

	String errorLabel = "";
	ItemRetrieveController itemRetController = new ItemRetrieveController();
	List<ItemBean> items = new ArrayList<>(); 
	List<String> itemTypes = new ArrayList<>(List.of("Book", "Movie", "Videogame"));
	List<String> articleTypes = new ArrayList<>(List.of("Review", "Guide"));
	List<String> layoutTypes = new ArrayList<>(List.of("grid", "vertical"));
	WriteReviewController controller = new WriteReviewController();
	UserBean loggedUser = ((UserBean)session.getAttribute("loggedUser"));
	if(loggedUser == null){
		%> <jsp:forward page="Home.jsp"/> <%
	}
	
	
	if(request.getParameter("articleBtn") != null){
		
		ArticleBean articleData = new ArticleBean();
		articleData.setAuthor(loggedUser);
		articleData.setTitle(request.getParameter("title"));
		articleData.setType(request.getParameter("articleType"));
		articleData.setLayout(request.getParameter("layout"));
		articleData.setReferredItem(itemMap.get(request.getParameter("referredItem")));
		
		
		articleData.setText(0, request.getParameter("text1"));
		articleData.setText(1, request.getParameter("text2"));
		articleData.setText(2, request.getParameter("text3"));
		articleData.setText(3, request.getParameter("text4"));
		
		try{
			controller.saveArticle(articleData);
			%> <jsp:forward page="Home.jsp"/> <%
		}catch(MissingArticleParametersException e){
			errorLabel = "Some important fields are missing";
		}
		
	}
	
	
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
<html lang="en">
	
	<head>
		<title>EE - Home</title>
		<link rel="stylesheet" href="Style/Style.css">
		<link rel="stylesheet" href="Style/HeaderBar.css">
		<link rel="stylesheet" href="Style/WriteArticle.css">
		<script src="https://ajax.googleapis.com/ajax/libs/jquery/3.5.1/jquery.min.js"></script>
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
			
		</div>
		
		<div style="width: 100%; height: 50px;"></div>
		
		<form method="POST">
		<div id="writeReview">
			
			<input id="title" type="text" name="title" placeholder="Insert a title..."/>
			
			<div id="body">
				<textarea name="text1" id="text1" placeholder = "Insert text for the first paragraph..."></textarea>
				<textarea name="text2" id="text2" placeholder = "Insert text for the second paragraph..."></textarea>
				<textarea name="text3" id="text3" placeholder = "Insert text for the third paragraph..."></textarea>
				<textarea name="text4" id="text4" placeholder = "Insert text for the fourth paragraph..."></textarea>
			</div>
		
			<div id="metadata">
			<select name="articleType" id="articleType">
					<option value="" disabled selected>article type</option>
    				<% 
						for(String type: articleTypes){
					%>
						<option value = "<%= type%>"><%= type%></option>
					<% } %>
					
					
  			</select>
			
			<select name="type" id="type">
					<option value="" disabled selected>item type</option>
    				<% 
						for(String type: itemTypes){
					%>
						<option value = "<%= type%>"><%= type%></option>
					<% } %>
					
					
  			</select>
			<select name="referredItem" id="referredItem">
				<option value="" disabled selected>referred item</option>
					<% 
						for(ItemBean item: items){
					%>
						<option value = "<%= item.getItemName() %>"><%= item.getItemName()%></option>
					<% } %>
  			</select>
  			
  			<select name="layout" id="layout">
  				<option value="" disabled selected>Layout</option>
					<% 
						for(String layout: layoutTypes){
					%>
						<option value = "<%= layout %>"><%= layout%></option>
					<% } %>
  			
  			</select>
			
				<input type="submit" name="articleBtn" value="Publish"/>
				<div style="color: red; width: 45%; font-size: 14px; background-color: transparent"><%= errorLabel %></div>
			</div>
			
		</div>
		</form>
	</body>
	<script>
		$('#user').click(function(e){
			
			if($('#menu').css("visibility") == 'hidden')
				$('#menu').css("visibility", "visible");
			else
				$('#menu').css("visibility", "hidden");
		});	
		$('#type').on('change', function(){
	        $(this).closest('form').submit();
	    	});
	</script>
</html>