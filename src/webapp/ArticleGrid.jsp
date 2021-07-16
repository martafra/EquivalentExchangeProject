<%@page import="logic.bean.UserBean" %>
<%@page import="logic.bean.ArticleBean" %>
<%@page import="logic.controller.application.ArticleRetrievalController" %>
<%
	
	if(request.getParameter("articleID") == null){
		%> <jsp:forward page="Home.jsp"/> <% 
	}
	
	ArticleRetrievalController controller = new ArticleRetrievalController();
	Integer articleID = Integer.valueOf(request.getParameter("articleID"));
	ArticleBean article = controller.getArticleData(articleID);
	
	String title = article.getTitle();
	String authorName = article.getAuthor().getUserID();
	
	
	String[] imagePaths = new String[4];
	for(int i = 0; i < article.getMediaPaths().size(); i++){
		imagePaths[i] = article.getMediaPaths().get(i);
	}
	String[] texts = new String[4];
	for(int i = 0; i < 4; i++){
		if(article.getText()[i] != null){
			texts[i] = article.getText()[i];
		}
		else{
			texts[i] = "";
		}
	}
	

%>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
	
	<head>
		<title>EE - Home</title>
		<link rel="stylesheet" href="Style/Style.css">
		<link rel="stylesheet" href="Style/HeaderBar.css">
		<link rel="stylesheet" href="Style/Profile.css">
		<link rel="stylesheet" href="Style/ArticleGrid.css">
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
			
			<div style="width: 100%; height: 50px"></div>
		
			<div id="articleHeader">
			
				<div id="articleTitle"><%=title %></div>
				<div id="header-right">
				<div class="rating">
							<img src="assets/images/empty-star.png" alt="empty_star" id="r1" onClick="fillfoo('r1')">
							<img src="assets/images/empty-star.png" alt="empty_star" id="r2" onClick="fillfoo('r2')">
							<img src="assets/images/empty-star.png" alt="empty_star" id="r3" onClick="fillfoo('r3')">
							<img src="assets/images/empty-star.png" alt="empty_star" id="r4" onClick="fillfoo('r4')">
							<img src="assets/images/empty-star.png" alt="empty_star" id="r5" onClick="fillfoo('r5')">
                </div>
                
                <form method="POST">
                	<input type="text" name="vote" id="r" style="width:0px; heiglht: 0px; visibility: hidden;"/>
                	<input type="submit" name="articleReview" value="Rate this Article" class="orange-clickable"/>
                </form>
                </div>
			
			</div>
			
			<div id="par1" class="textDiv">
				<% if(imagePaths[0] != null){ %>
					<img src="file?path=<%= imagePaths[0]%>" alt="artImage"/>
				<% } else { %>
					<div style="width: 300px; height: 300px;" class="substitute"></div>
				<% } %>
				<div id="text1"><%=texts[0] %></div>
			</div>
			<div id="par2" class="textDiv" >
				<div id="text2"><%=texts[1] %></div>
				<% if(imagePaths[1] != null){ %>
					<img src="file?path=<%= imagePaths[1] %>" alt="artImage"/>
				<% } else { %>
					<div style="width: 300px; height: 300px;" class="substitute"></div>
				<% } %>
			</div>
			<div id="par3" class="textDiv">
				<% if(imagePaths[2] != null){ %>
					<img src="file?path=<%= imagePaths[2] %>" alt="artImage"/>
				<% } else { %>
					<div style="width: 300px; height: 300px;" class="substitute"></div>
				<% } %>
				<div id="text3"><%=texts[2] %></div>
			</div>
			<div id="par4" class="textDiv">
				<div id="text4"><%=texts[3] %></div>
				<% if(imagePaths[3] != null){ %>
					<img src="file?path=<%= imagePaths[3] %>" alt="artImage"/>
				<% } else { %>
					<div style="width: 300px; height: 300px;" class="substitute"></div>
				<% } %>
			</div>
			
			<div id="authorBar">Written by <%=authorName %></div>
			
		
	</body>
	<script>
		$('#user').click(function(e){
			
			if($('#menu').css("visibility") == 'hidden')
				$('#menu').css("visibility", "visible");
			else
				$('#menu').css("visibility", "hidden");
		});	
		
		
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