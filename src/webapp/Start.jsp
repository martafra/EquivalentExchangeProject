<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"
    
    %>
    
<% if(request.getParameter("goToItemDetails")!=null)   {
	session.setAttribute("Descrizione", "AAAAA");
%>	

	<jsp:forward page="ItemDetails.jsp">
	<jsp:param name="itemID" value="10"/>
	</jsp:forward>
<% 
}
%>


<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>Start</title>
</head>
<body>
	 <form action="Start.jsp" name="myform" method="POST">
		 <input type="submit" name="goToItemDetails" value="Go to itemDetails"/>
	</form>
</body>
</html>