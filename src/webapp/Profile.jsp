<%@page import="logic.bean.UserBean" %>
<%@page import="logic.bean.UserProfileBean" %>
<%@page import="logic.bean.ArticleBean" %>
<%@page import="logic.bean.ItemInSaleBean" %>
<%@page import="java.util.List" %>
<%@page import="logic.controller.application.ProfileController" %>
<%@page import="logic.controller.application.WriteReviewController" %>


<%
	UserBean loggedUser = ((UserBean)session.getAttribute("loggedUser"));
	if(loggedUser == null){
		%> <jsp:forward page="Login.jsp"/> <%
	}

	ProfileController controller = new ProfileController();
	WriteReviewController modController = new WriteReviewController();
	List<ArticleBean> articles = controller.getArticlesByUser(loggedUser, 4);
	List<ItemInSaleBean> items = controller.getProductsByUser(loggedUser, 4);
	UserProfileBean profileBean = controller.getUserProfileData(loggedUser);
	String name = profileBean.getName();
	String lastName = profileBean.getLastName();
	String username = profileBean.getUserID();
	Integer age = profileBean.getAge();
	String city = profileBean.getCity();
	String bio = profileBean.getDescription();
	String picPath = profileBean.getProfilePicPath();
	String coverPic = profileBean.getCoverPicPath();
	
	
	
	if(bio == null){
		bio = "There is no description for this user...";
	}
	

%>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html lang="en">
	
	<head>
		<title>EE - Home</title>
		<link rel="stylesheet" href="Style/Style.css">
		<link rel="stylesheet" href="Style/HeaderBar.css">
		<link rel="stylesheet" href="Style/Profile.css">
		<script src="https://ajax.googleapis.com/ajax/libs/jquery/3.5.1/jquery.min.js"></script>
		<style>
		</style>
	</head>

	<body>
		
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
		<%if(coverPic == null){
			%> <img id="banner" src="assets/images/ee-bg.png" alt="e"/> <%
		}
		else{
			%> <img id="banner" src="file?path=<%= coverPic %>" onerror="this.src='assets/images/ee-bg.png';"alt="e"/> <%
		}
		%>
		<div id="personalInfo">
			<img id="profileImage" src="file?path=<%=picPath %>" onerror="this.src='assets/images/avatar.png';" alt="e"/>
			<div id="name"><%= name + " " + lastName %></div>
			<div id="username"><%= username %></div>
			<div id="overallRatings">
				<%
				int i = 0;
				int vote = profileBean.getSellerVote();
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
			<div id="gender"></div>
			<div id="age"> I'm <%=age %> years old</div>
			<div id="city"></div>
		</div>
		
		<div id="centralInfo">
			
			<div id="description"><%=bio %></div>
			
			<div id="products">
			
				<%
					for(ItemInSaleBean item : items){
						
						String itemName = item.getItemName();
						Integer price = item.getPrice();
						String priceText = price.toString() + "coins";
						
						
						%> <div class="product">
							<div class="productImage">
								<img src="file?path=<%=item.getMediaPath() %>" onerror="this.src='assets/images/missing.png';" alt="product"/>
								
							</div>
							<form action="ItemDetails.jsp" method="POST">
							<input type="text" style="width: 0px; height: 0px; visibility:hidden;" name="itemID" value = "<%=item.getItemID() %>"/>
							<input type="submit" class="productName" name="itemName" value="<%=itemName %>">
							</form>
							
							<div class="price"><%=priceText %></div>
						</div><%
						
					}
					
				%>
			
			</div>
			<div class="page-link"><a href="SellerPanel.jsp" class="green-clickable">Go to seller panel</a></div>
			<div id="articles">
			
				<%
					for(ArticleBean art : articles){
						
						String title = art.getTitle();
						String articleText = art.getText()[0];
						if(articleText == null){
							articleText = "There is no preview for this article";
						}
						
						%> <div class="article">
							<div class="articleImage">

								<img src="file?path=<%=art.getMediaPaths().get(0)%>"  onerror="this.src='assets/images/missing.png';" alt="product"/>
							</div>
							
							<div class="title"><%=title %></div>
							<div class="articleText"><%=articleText %></div>
						</div><%
						
					}
					
				%>
			
			</div>
			<div class="page-link"><a href="ReviewerPanel.jsp" class="green-clickable">Go to reviewer panel</a></div>
		</div>
		
		<div id="rightPanel">
			<div style="color: #FF6A00; font-size: 20px; padding: 10px;">Average ratings</div>
			<table id="ratings">
				<caption></caption>
				<tr><th id="tab"></th></tr>
				<tr>
					<td>Seller availability: </td>
					<td>
					
						<div class="rating">
							<%
								i = 0;
								vote = profileBean.getOverallAvailabilityValue();
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
					
					</td>
				</tr>
				<tr>
					<td>Seller reliability: </td>
					<td>
					
						<div class="rating">
							<%
								i = 0;
								vote = profileBean.getOverallReliabiltyValue();
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
					
					</td>
				</tr>
				<tr>
					<td>Product condition: </td>
					<td>
					
						<div class="rating">
							<%
								i = 0;
								vote = profileBean.getOverallConditionsValue();
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
					
					</td>
				</tr>
			
			</table>
			<% if(loggedUser.isModerator()){ %>
			
			<div id="moderatorRequests">
				
				<% 
					List<ArticleBean> pendingArticles = modController.getPendingArticles();
					pendingArticles = articles;
					for(ArticleBean article : pendingArticles){
						String title = article.getTitle();
						Integer articleID = article.getID();
						String author = article.getAuthor().getUserID();
						
						%> <div class="request">
							<form method="POST">
								<div style="float: left;">
								<div class="rArticleTitle"><%=title %></div>
								<div class="rAuthor"><%=author %></div>
								</div>
								<div style="float: right">
								<input type="text" name="articleID" value ="<%=articleID %> " style="visibility: hidden; width: 0px; height: 0px;"/>
								<input class="orange-clickable" type="submit" name="articleRequest" value=" ->"/>
								</div>
							</form>
						
						</div> <% 
						
					 } %>
	
			</div>
			
			<% } %>
		</div>
		
	</body>
	<script>
		$('#user').click(function(e){
			
			if($('#menu').css("visibility") == 'hidden')
				$('#menu').css("visibility", "visible");
			else
				$('#menu').css("visibility", "hidden");
		});	
	</script>
</html>