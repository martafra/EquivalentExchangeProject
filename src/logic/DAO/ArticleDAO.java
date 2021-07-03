package logic.DAO;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Date;

import logic.entity.Article;
import logic.enumeration.ArticleType;
import logic.enumeration.LayoutType;
import logic.query.ArticleQuery;
import logic.support.database.MyConnection;

public class ArticleDAO {
	
	private MyConnection connection = MyConnection.getInstance();
	private static Character ESCAPE_CHARACTER = 'ç';
	private ArticleQuery articleQuery = new ArticleQuery();
	
	public void insertArticle(Article article) {
		Statement stmt = null;
		try {
			Connection con = connection.getConnection();
			stmt = con.createStatement();
			Integer articleID = article.getArticleID();
			String title = article.getTitle();
			String body = "";
			for(Integer i = 0; i < 4; i++) {
				body += article.getText(i) + ESCAPE_CHARACTER.toString();
			}
			LayoutType layout = article.getLayout();
			String layoutString = layout.toString().substring(0,1);
			ArticleType type = article.getType();
			String typeString = type.toString().substring(0,1);
			String authorID = article.getAuthor().getUsername();
			Boolean validationStatus = article.isValidated();
			Integer reviewPoints = 0;
			Date publishingDate = new Date();
			
			Integer referredItemID = article.getReferredItem().getItemID();
			
			String query = articleQuery.insertArticle(articleID, referredItemID, title, body, layoutString, typeString, validationStatus, authorID, reviewPoints, publishingDate);
			
			stmt.executeUpdate(query);

		} catch (SQLException e) {
			// TODO Auto-generated catch block

			e.printStackTrace();

		} finally {
			try {
				if (stmt != null) {
					stmt.close();
				}
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
	}
	
}
