package logic.query;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

public class ArticleQuery extends Query{
	
	public String insertArticle(Integer articleID, Integer itemID, String title, String body, String layout, String type, Boolean status, String author, Integer points, Date date){
		DateFormat format = new SimpleDateFormat(dateTimeFormat);
		
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
}
