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
	HashMap<String, String> filters = new HashMap<>();
	ArrayList<String> genres = new ArrayList<>();
	int pageNumber = 0;
	int maxItem = 8;
	String searchStr = null;
	String typeStr ="A";
	String genreStr ="";
	
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
			
			if(request.getParameter("genre")!=null){
				genreStr=request.getParameter("genre");
				if(genres.contains(genreStr)){
					filters.put("genre", genreStr);
				}
				
			}
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
		<meta charset="ISO-8859-1">
		<title>Catalogue</title>
	</head>
	<body>
		<div id="headerbar">
			<img id="logoImage" src="assets/images/logo.png" alt="logoImage">
			<span><span style="color: #FF6A00">E</span>QUIVALENT <span style="color: #5AC02A">E</span>XCHANGE</span> 
			
			<span>HOME</span> 
			<span>CATALOGUE</span>
			<span>COMMUNITY</span>

			<div id="login">
				<button text="Login" class="orange-clickable">Login</button>
			</div>
		</div>
		
		<form action="Catalogue.jsp">
			<div class="cataloguePage">
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
						<select name="genre" onChange="this.form.submit()" style="margin-left:5%;">
							<option value = "nullGenre" ></option>
							<% 
								for(String gen : genres) {
									%>
									<option value="<%= gen %>" <%if(genreStr.equals(gen)) {%> selected ="selected" <% } %>><%= gen %></option>
							<%
								}
							%>
						</select>
					<%} %>
						
				</div>
		
				<div class = "catalogue">
					<div class = "search">
			 	
						<input id = "searchBar" type="text" name="searchBar" placeholder="Search.." autocomplete="off" 
						<%if (searchStr!=null){%> 
							value ="<%= searchStr %>" 
						<%} %>>
						<input id ="searchBtn" type="submit" name="searchBtn" value="SEARCH">
				
					</div>
					<div class ="items">
						<% for (int i = 0; i < maxItem && ( i+(maxItem*pageNumber) < itemInSaleBeanList.size() ); i++) { %>
						<div class= "itemBox">
							<a href ="ItemDetails.jsp?itemID=<%=itemInSaleBeanList.get(i+(maxItem*pageNumber)).getItemID()%>" style = "text-decoration : none; color: black;"> 
								<%= itemInSaleBeanList.get(i+(maxItem*pageNumber)).getItemName() %> 
							</a>
							<p id ="price"> <%= itemInSaleBeanList.get(i+(maxItem*pageNumber)).getPrice()  %> COINS</p>
						</div>
						<%} %>
					</div>
					<div class ="page">
				
						<% if (pageNumber > 0){ %>
							<input id = "pageBtn" class="orange-clickable" type="submit" name="prevBtn" value ="PREVIOUS">
						<%}else{%>
							<input id = "pageBtn" class="orange-clickable" type="submit" name="prevBtn" value ="PREVIOUS" disabled = "disabled" style = "background-color:grey">
						<%} %>
							<input id ="pageNumber" type ="number" name = "pageNumber" value = "<%= pageNumber %>" readonly> 
						<%if(maxItem >= itemInSaleBeanList.size() || (pageNumber+1)*maxItem >= itemInSaleBeanList.size() ){ %>
							<input id = "pageBtn" class="orange-clickable" type="submit" name="nextBtn" value = "NEXT" disabled = "disabled" style = "background-color:grey">
						<%}else{ %>
							<input id = "pageBtn" class="orange-clickable" type="submit" name="nextBtn" value = "NEXT">
						<%} %>
					</div>
				</div>
			</div>
		</form>
	</body>
</html>