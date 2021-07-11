<%@ page import="logic.bean.UserBean" %>
<%@ page import="logic.controller.application.LoginController" %>


<%
	UserBean loggedUser = (UserBean)session.getAttribute("loggedUser");
	LoginController controller = new LoginController();
	if(loggedUser.getUserID() != null){
		controller.logout(loggedUser);	
		session.removeAttribute("loggedUser");
	}
%>
	<jsp:forward page="Home.jsp"/>
