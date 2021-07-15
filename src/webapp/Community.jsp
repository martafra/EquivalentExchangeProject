<%@page import="logic.bean.UserBean" %>
<%@page import="logic.bean.ArticleBean" %>
<%@page import="logic.controller.application.CommunityController" %>
<%@page import="java.util.List" %>

<%
	CommunityController controller = new CommunityController();
	List<ArticleBean> articles;

	String filter = (String) request.getParameter("filter");
	if(filter == null){
		filter = "All";
	}
	
	switch(filter){
	
	
	case "Books":
		articles = controller.getBookReviews();
		break;
	case "Videogame Reviews":
		articles = controller.getVideogameReviews();
		break;
	case "Videogame Guides":
		articles = controller.getVideogameGuides();
		break;
	case "Movies":
		articles = controller.getMovieReviews();
		break;
	
	case "search":
		if(request.getParameter("filterText") != null){
		
			articles = controller.getInputArticles(request.getParameter("filterText"));
			break;
		}
	
	case "All":
	default:
		articles = controller.getAllAcceptedArticles();
	
	}
	
	
%>


<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
	
	<head>
		<title>EE - Community</title>
		<link rel="stylesheet" href="Style/Style.css">
		<link rel="stylesheet" href="Style/HeaderBar.css">
		<link rel="stylesheet" href="Style/Community.css">
		<script src="https://ajax.googleapis.com/ajax/libs/jquery/3.5.1/jquery.min.js"></script>
		<style>
		</style>
	</head>

	<body class="bubble-background">
		
		<div id="headerbar">
			<img id="logoImage" src="assets/images/logo.png" alt="logoImage">
			<span><span style="color: #FF6A00">E</span>QUIVALENT <span style="color: #5AC02A">E</span>XCHANGE</span>
			
			
			<span><a href="Home.jsp" class ="link">HOME</a></span>
            <span><a href="Catalogue.jsp" class ="link">CATALOGUE</a></span>
            <span><a href="Community.jsp" class ="link">COMMUNITY</a></span>
			
			
			<div id="login">
			<%
				UserBean loggedUser = ((UserBean)session.getAttribute("loggedUser"));
				
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
		<div id="filterText">
			<form method="POST">
				<input id="searchBar" type="text" name="filterText" />
				<input id="searchBtn" type="submit" name="filter" value="search" />
			</form>
		</div>
		<div id="navBar">
			<form method="POST">
				<input id="All" type="submit" name="filter" value="All"/>
				<input id="Books" type="submit" name="filter" value="Books"/>
				<input id="Videogame_Reviews"type="submit" name="filter" value="Videogame Reviews"/>
				<input id="Videogame_Guides" type="submit" name="filter" value="Videogame Guides"/>
				<input id="Movies" type="submit" name="filter" value="Movies"/>
			</form>
		</div>
		<div id="reviewsContainer">
		
			<% for(ArticleBean article : articles){
					String title = article.getTitle();
					String author = article.getAuthor().getUserID();
					String text = article.getText()[0];
					String type = article.getType();
					String color;
					if(type.equals("Guide")){
						color = "#FFD9BD";
					}else{
						color = "#D7E9D8";
					}
					
			%>
			<div class="articleCase" style="background-color: <%= color %>">
				<div class="image"></div>
				<div class="title"><form action="ArticlePage.jsp" method="POST"><input type="submit" name="selectedArticle" value = "<%= title %>"></form></div>
				<div class="text"><%= text %></div>
				<div class="rating">
				
					<%
						int i = 0;
						int vote = article.getVote();
						for(i = 2; i <= vote; i+=2){
							%> <img src="assets/images/full-star.png" alt="full_star"> <%
						}
						if(vote % 2 != 0){
							%> <img src="assets/images/semi-star.png" alt="half_star"> <%
							i+=2;
						}
						for(int j = i; j <= 10; j+=2){
							%> <img src="assets/images/empty-star.png" alt="empty_star"> <%
						}
					
					%>
				
				</div>
				<div class="author"><%= author %></div>
				
			</div>
		
		
		<% } %>
		</div>
	</body>
	<script>
		$('#<%= filter.replace(' ', '_') %>').css("background-color", "#D7E9D8");
	
		$('#user').click(function(e){
			
			if($('#menu').css("visibility") == 'hidden')
				$('#menu').css("visibility", "visible");
			else
				$('#menu').css("visibility", "hidden");
		});	
	</script>
</html>
