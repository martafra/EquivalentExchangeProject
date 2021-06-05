package logic.query;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

public class ArticleQuery extends Query{
	
	public String insertArticle(Integer id, Integer item, String title, String body, String layout, String typr, Integer status, String author, Integer points, Date date){
		DateFormat format = new SimpleDateFormat(dateTimeFormat);
		String pub = quote(format.format(date));
	/*articleID int,
    referredItemID int,
    title varchar(64) not null,
    body varchar(10000) not null,
    layout varchar(1) not null,
    articleType varchar(1) not null,
    validationStatus int default 0,
    authorID varchar(25),
    reviewPoints int,
    publishingDate date,*/

		return "";
	}
}
