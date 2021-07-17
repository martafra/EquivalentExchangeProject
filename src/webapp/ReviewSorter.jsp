<%@page import="logic.bean.ArticleBean" %>
<%@page import="logic.controller.application.ArticleRetrievalController" %>

<%
	
	String articleID = request.getParameter("articleID");
	if(articleID == null){
		%> <jsp:forward page="Home.jsp"/> <%
	}
	
	Integer artID = Integer.valueOf(articleID);
	
	ArticleRetrievalController controller = new ArticleRetrievalController();
	
	ArticleBean articleData = controller.getArticleData(artID);
	
	if(articleData == null){
	%>	<jsp:forward page="Home.jsp"/> <%
	}

	switch(articleData.getLayout()){
	case "vertical":
		%>	<jsp:forward page="ArticleVertical.jsp">
			<jsp:param name="articleID" value="<%=artID%>" ></jsp:param>
			</jsp:forward>
		 <%
		break;
	case "grid":
	default:
		%>	<jsp:forward page="ArticleGrid.jsp">
			<jsp:param name="articleID" value="<%=artID%>" ></jsp:param>
			</jsp:forward> 
			<%
		break;
	}

%>