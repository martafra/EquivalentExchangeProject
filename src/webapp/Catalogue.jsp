<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@page import="logic.controller.application.CatalogueController" %>
<%@page import= "logic.bean.ItemInSaleBean"  %>
<%@page import= "logic.bean.UserBean"  %>   
<%@ page import="java.util.List" %>
<%@ page import="java.util.ArrayList" %>
<%@ page import="java.util.HashMap" %>

<%
	CatalogueController controller = new CatalogueController();
	UserBean loggedUser = (UserBean)session.getAttribute("loggedUser");
	if(loggedUser !=null){
		System.out.println("Catalogo: loggedUser = "+loggedUser.getUserID());
	}
	else{
		System.out.println("Catalogo: loggedUser==null");
	}
	HashMap<String, String> filters = new HashMap<>();
	ArrayList<String> genres = new ArrayList<>();
	ArrayList<String> consoles = new ArrayList<>();
	int pageNumber = 0;
	int maxItem = 12;
	String searchStr = null;
	String typeStr ="A";
	String genreStr ="";
	String consoleStr ="";
	String orderStr ="";
	
	String username = null;
	if (session.getAttribute("loggedUser") != null) {
		username = loggedUser.getUserID();
	}
	if(request.getParameter("searchBar")!=null){
			searchStr = request.getParameter("searchBar");
			filters.put("searchKey",request.getParameter("searchBar"));
	}	
	if (request.getParameter("nextBtn")!= null){
		pageNumber = Integer.parseInt(request.getParameter("pageNumber"));
		pageNumber += 1;
	}
	if (request.getParameter("prevBtn")!= null){
		pageNumber = Integer.parseInt(request.getParameter("pageNumber"));
		pageNumber -= 1;
	}
	if (request.getParameter("type")!=null){
		typeStr = request.getParameter("type");
		if ( !typeStr.equals("A")){
			filters.put("type", typeStr);
			genres = (ArrayList<String>) controller.getGenre(typeStr.charAt(0));
			
			System.out.println("Parameter genre: " + request.getParameter("genre"));
			if(request.getParameter("genre")!=null){
				System.out.println(request.getParameter("genre"));
				genreStr=request.getParameter("genre");
				if(genres.contains(genreStr)){
					filters.put("genre", genreStr);
				}
				
			}
			
			if(typeStr.equals("V")){	
				consoles = (ArrayList<String>) controller.getConsole();
				if(request.getParameter("console")!=null){
					consoleStr = request.getParameter("console");
					if(!consoleStr.equals("nullConsole")){
						filters.put("console", consoleStr);
					}
				}
				
			}
		}
	
	}
	
	if(request.getParameter("orderBy")!=null){
		orderStr = request.getParameter("orderBy");
		if(!orderStr.equals("nullOrder")){
			filters.put("orderBy", orderStr);
		}
		else{
			filters.remove("orderBy");
		}
		
	}
	
	
	List<ItemInSaleBean> itemInSaleBeanList = controller.getListItemInSaleBeanFiltered(username, filters);	

%>
<!DOCTYPE html>
<html>
	<head>
		<link rel="stylesheet" href="Style/Catalogue.css">
		<link rel="stylesheet" href="Style/Style.css">
		<link rel="stylesheet" href="Style/HeaderBar.css">
		<script src="https://ajax.googleapis.com/ajax/libs/jquery/3.5.1/jquery.min.js"></script>
		<meta charset="ISO-8859-1">
		<title>Catalogue</title>
	</head>
	<body>
			<div id="headerbar">
            	<img id="logoImage" src="assets/images/logo.png" alt="logoImage">
           	 	<span><span style="color: #FF6A00">E</span>QUIVALENT <span style="color: #5AC02A">E</span>XCHANGE</span>


            	<span><a href="Home.jsp" class ="link">HOME</a></span>
            	<span><a href="Catalogue.jsp" class ="link">CATALOGUE</a></span>
            	<span>COMMUNITY</span>


            	<div id="login">
			<%	
				if(loggedUser == null){ %>
    				
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
		
		<form action="Catalogue.jsp">
			<div class="cataloguePage" style ="margin-top: 3%;">
				<div class = "filters">
					<p style="font-size:16pt">Help us to understand what you really wish</p>
					<p class = "filtersLabel"> CATEGORY </p>
						<Label class = "typeLabel"><input class ="type" type="radio" name="type" value ="A" onclick="this.form.submit()" 
						<%if(typeStr.equals("A")){ %> 
							checked 
						<%} %>
						>ALL</Label>
						<Label class = "typeLabel"><input class ="type" type="radio" name="type" value ="B" onclick="this.form.submit()"
						<%if(typeStr.equals("B")){ %> 
							checked 
						<%} %>
						>BOOKS</Label>
						<Label class = "typeLabel"><input class ="type" type="radio" name="type" value ="M" onclick="this.form.submit()"
						<%if(typeStr.equals("M")){ %> 
							checked 
						<%} %>
						>DVDs</Label>
						<Label class = "typeLabel"><input class ="type" type="radio" name="type" value ="V" onclick="this.form.submit()"
						<%if(typeStr.equals("V")){ %> 
							checked 
						<%} %>
						>VIDEOGAMES</Label>
						
					<%if(!typeStr.equals("A")){ %>
						<p class = "filtersLabel"> GENRE </p>
						<select name="genre" class = "list" onChange="this.form.submit()">
							<option value = "nullGenre" ></option>
							<% for(String gen : genres) {
									%>
									<option value="<%= gen %>" <%if(genreStr.equals(gen)) {%> selected ="selected" <% } %>><%= gen %></option>
							<%
								}
							%>
						</select>
						<%if (typeStr.equals("V")){ %>
							<p class = "filtersLabel"> CONSOLE </p>
							<select name="console" class = "list" onChange="this.form.submit()" >
								<option value = "nullConsole"></option>
								<% for(String con : consoles) {
									%>
									<option value="<%= con %>" <%if(consoleStr.equals(con)) {%> selected ="selected" <% } %>><%= con %></option>
								<%
								}
							%>		
							</select>
						<% } %>
					<% } %>
				</div>
		
				<div class = "catalogue">
					<div class = "search">
			 	
						<input id = "searchBar" type="text" name="searchBar" placeholder="Search..." autocomplete="off" 
						<%if (searchStr!=null){%> 
							value ="<%= searchStr %>" 
						<%} %>>
						<input id ="searchBtn" type="submit" name="searchBtn" value="SEARCH">
						
						
					</div>
					<div class ="items">
						<label style="margin-bottom:10px; font-size:14pt; margin-left:1%;"> Order by: </label>
							<select name="orderBy" class = "list" onChange="this.form.submit()" style="margin-bottom:10px;">
								<option value="nullOrder" <%if (orderStr.equals("nullOrder")){ %> selected ="selected" <% } %> ></option>
								<option value="Rising Price" <%if (orderStr.equals("Rising Price")){ %> selected ="selected" <% } %>>Rising Price</option>
								<option value="Decreasing Price" <%if (orderStr.equals("Decreasing Price")){ %> selected ="selected" <% } %>>Decreasing Price</option>
							</select>
						<br>
						<% for (int i = 0; i < maxItem && ( i+(maxItem*pageNumber) < itemInSaleBeanList.size() ); i++) { %>
						<div class= "itemBox">
							<img src="assets/images/missing.png" alt="error" style="width:200px; height:200px; margin-left:2%; margin-right:2%;"/>
							<br>
							<a href ="ItemDetails.jsp?itemID=<%=itemInSaleBeanList.get(i+(maxItem*pageNumber)).getItemID()%>" class="link" > 
								<%= itemInSaleBeanList.get(i+(maxItem*pageNumber)).getItemName() %> 
							</a>
							<p id ="price" style="color:#FF6A00;"> <%= itemInSaleBeanList.get(i+(maxItem*pageNumber)).getPrice()  %> COINS</p>
						</div>
						<%} %>
					</div>
					<div class ="page">
				
						<% if (pageNumber > 0){ %>
							<input id = "pageBtn" class="orange-clickable" type="submit" name="prevBtn" value ="PREVIOUS">
						<%}else{%>
							<input id = "pageBtn" class="orange-clickable" type="submit" name="prevBtn" value ="PREVIOUS" disabled = "disabled" style = "background-color:grey; opacity: 0.3">
						<%} %>
							<input id ="pageNumber" type ="number" name = "pageNumber" value = "<%= pageNumber %>" readonly> 
						<%if(maxItem >= itemInSaleBeanList.size() || (pageNumber+1)*maxItem >= itemInSaleBeanList.size() ){ %>
							<input id = "pageBtn" class="orange-clickable" type="submit" name="nextBtn" value = "NEXT" disabled = "disabled" style = "background-color:grey; opacity: 0.3">
						<%}else{ %>
							<input id = "pageBtn" class="orange-clickable" type="submit" name="nextBtn" value = "NEXT">
						<%} %>
					</div>
				</div>
			</div>
		</form>
	</body>
</html>