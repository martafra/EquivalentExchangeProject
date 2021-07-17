<%@ page import="java.util.List" %>
<%@ page import="java.util.ArrayList" %>
<%@ page import="logic.bean.UserBean" %>
<%@ page import="logic.bean.ArticleBean" %>
<%@page import="logic.controller.application.CommunityController" %>
<%@page import="java.util.List" %>
<%@page import="java.util.ArrayList" %>

<%	if (session.getAttribute("loggedUser") == null){
	%>
	<jsp:forward page="Login.jsp"/>
	<% 
	} 
%>

<%!CommunityController comController= new CommunityController(); %>
<%List<ArticleBean> articles = comController.getAllAcceptedArticles(((UserBean)session.getAttribute("loggedUser")));%>
<%List<ArticleBean> reviews = new ArrayList<>(); %>
<%List<ArticleBean> guides = new ArrayList<>(); %>
<%for(ArticleBean article: articles){
	if(article.getType().equalsIgnoreCase("review")){
		reviews.add(article);
	}
	else{
		guides.add(article);
	}
} %>







<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html lang="en">
	
	<head>
		<title>EE - Order Summary</title>
		<script src="https://ajax.googleapis.com/ajax/libs/jquery/3.5.1/jquery.min.js"></script>
		<link rel="stylesheet" href="Style/Style.css">
		<link rel="stylesheet" href="Style/HeaderBar.css">
		<link rel="stylesheet" href="Style/Community.css">
		<style>
			.articleCase{
				width: 406px;
				height: 319px;
				margin-top: 10px;
				margin-left: 45px;
				border-radius: 10px;
				padding : 2%;
			}
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

	<div style ="width:35%;height:1080px;display:inline-block;background-color:#FFFFFF;margin-left:5px;float:left">
    		<div style="margin-top:65px;margin-left:2px;height:25px;font-size:16;color:#5AC02A">Accepted reviews</div>
    		<div style="height:1080px; overflow: scroll;">
    			<div>
		
			<% for(ArticleBean review : reviews){
					String title = review.getTitle();
					String author = review.getAuthor().getUserID();
					String text = review.getText()[0];
					String type = review.getType();
					String mediaPath = review.getMediaPaths().get(0);
					String color = "#D7E9D8";
					
					
			%>
			<div class="articleCase" style="background-color: <%= color %>">
				<img class="image" src="file?path=<%=mediaPath%>" onerror="this.src='assets/images/missing.png';" alt="e"/>
				<div class="title"><form action="ArticlePage.jsp" method="POST"><input type="submit" name="selectedArticle" value = "<%= title %>"></form></div>
				<div class="text"><%= text %></div>
				<div class="rating">
				
					<%
						int i = 0;
						int vote = review.getVote();
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
		
		</div>
		<% } %>
    	</div>
    	</div>
    	
    	<div style ="width:35%;height:1080px;display:inline-block;background-color:#FFFFFF;margin-left:5px;float:left">
    		<div style="margin-top:65px;margin-left:2px;height:25px;font-size:16;color:#FF6A00">Accepted guides</div>
    		<div style="height:1080px; overflow-y: scroll;overflow-x:hidden">
    		<div>
		
			<% for(ArticleBean review : guides){
					String title = review.getTitle();
					String author = review.getAuthor().getUserID();
					String text = review.getText()[0];
					String type = review.getType();
					String mediaPath = review.getMediaPaths().get(0);
					String color = "#FFD9BD";
					
					
			%>
			<div class="articleCase" style="background-color: <%= color %>">
				<img class="image" src="file?path=<%=mediaPath%>" onerror="this.src='assets/images/missing.png';" alt="e"/>
				<div class="title"><form action="ArticlePage.jsp" method="POST"><input type="submit" name="selectedArticle" value = "<%= title %>"></form></div>
				<div class="text"><%= text %></div>
				<div class="rating">
				
					<%
						int i = 0;
						int vote = review.getVote();
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
		
		</div>
		<% } %>
    		
    	</div>
    	</div>
    	
    	<div style= "width:18%;height:720px;display:inline-block;float:left">
    		<a href="WriteArticle.jsp"><input type="button" value="Write article" class="green-clickable-big" style="display:inline-block; margin-top:135%;margin-left:50%;"/></a>
    	</div>
	</body>

</html>