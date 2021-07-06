package logic.query;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

public class ArticleQuery extends Query{
	
	DateFormat format = new SimpleDateFormat(dateTimeFormat);
	
	public String deleteArticle(Integer articleID) {
		String query = "DELETE from article WHERE articleID = %d;";
		return String.format(query, articleID);
	}
	
	public String updateArticle(Integer articleID, String title, String body, String layout, String type, Boolean status, Integer points) {
		
		
		title = quote(title);
		body = quote(body);
		layout = quote(layout);
		type = quote(type);
		
		Integer validationStatus = 0;
		
		if(Boolean.TRUE.equals(status)) {
			validationStatus = 1;
		}
		
		
		String query = "UPDATE Article SET "
					 + "title = %s, "
					 + "body = %s, "
					 + "layout = %s, "
					 + "articleType = %s, "
					 + "validationStatus = %d, "
					 + "reviewPoints = %d "
					 + "WHERE articleID = %d";
		
		return String.format(query, title, body, layout, type, validationStatus, points, articleID);
		
		
	}
	
	public String insertArticle(Integer articleID, Integer itemID, String title, String body, String layout, String type, Boolean status, String author, Integer points, Date date){
		
		
		String pubDate = "null";
		
		if(date != null) {
			pubDate = quote(format.format(date));
		}
		
		
		title = quote(title);
		body = quote(body);
		layout = quote(layout);
		type = quote(type);
		
		Integer validationStatus = 0;
		
		if(Boolean.TRUE.equals(status)) {
			validationStatus = 1;
		}
		
		author = quote(author);
		
		String query = "INSERT INTO Article (ArticleID, referredItemID, title, body, layout, articleType, validationStatus, authorID, reviewPoints, publishingDate) " +
					   "VALUES (%d, %d, %s, %s, %s, %s, %d, %s, %d, %s);";

		return String.format(query, articleID, itemID, title, body, layout, type, validationStatus, author, points, pubDate);
	}
	
	public String selectAllArticles(String authorUserID) {
		
		String query = "SELECT * FROM Article";
		
		if(authorUserID != null) {
			
			authorUserID = quote(authorUserID);
			query = query + " WHERE authorID = %s";
			
		}
		
		return String.format(query, authorUserID);
	}
	
	public String selectAcceptedArticles(String authorUserID) {
		
		String query = selectAllArticles(authorUserID);
		
		if(authorUserID != null) {
			
			query = query + " AND ";
			
		}
		
		query = query + "validationStatus = 1;";
		
		return query;
		
	}

	
	
}
