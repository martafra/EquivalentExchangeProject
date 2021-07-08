package logic.DAO;

import java.io.IOException;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import logic.entity.Article;
import logic.entity.Item;
import logic.entity.ItemInSale;
import logic.entity.User;
import logic.enumeration.ArticleType;
import logic.enumeration.LayoutType;
import logic.query.ArticleMediaQuery;
import logic.query.ArticleQuery;
import logic.query.TagQuery;
import logic.support.database.MyConnection;
import logic.support.other.ImageCache;

public class ArticleDAO {
	
	private MyConnection connection = MyConnection.getInstance();
	private static Character ESCAPE_CHARACTER = 'ç';
	private ArticleQuery articleQuery = new ArticleQuery();
	private ArticleMediaQuery mediaQuery = new ArticleMediaQuery();
	private TagQuery tagQuery = new TagQuery();
	private DateFormat format = new SimpleDateFormat("yyyy-MM-dd");
	
	
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
			Date publishingDate = article.getPublishingDate();
			
			Integer referredItemID = article.getReferredItem().getItemID();
			
			String query = articleQuery.insertArticle(articleID, referredItemID, title, body, layoutString, typeString, validationStatus, authorID, reviewPoints, publishingDate);
			
			stmt.executeUpdate(query);
			
			Integer mediaID = 0;

			for(String mediaPath : article.getAllMedia()){
				query = mediaQuery.insertMedia(articleID, mediaPath, mediaID);
				System.out.println(query);
				stmt.executeUpdate(query);
				mediaID++;
			}
			
			for(String tag : article.getTags()) {
				query = tagQuery.insertTagForArticle(tag, articleID);
				stmt.executeUpdate(query);
			}
			
			

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
	
	public void updateArticle(Article article) {
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
			Boolean validationStatus = article.isValidated();
			Integer reviewPoints = 0;
			
			String query = articleQuery.updateArticle(articleID, title, body, layoutString, typeString, validationStatus, reviewPoints);
			
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
	
	public Article selectArticle(Integer articleID) {
		Article article = null;
		Statement stmt = null;
		ResultSet rs = null;
		ResultSet rs2 = null;
		String query = articleQuery.selectArticleByID(articleID);
		
		try {
			
			Connection con = connection.getConnection();
			stmt = con.createStatement();
			System.out.println(query);
			rs = stmt.executeQuery(query);
			
			if(!rs.next())
				return null;
			Boolean status = false;
			
			if(rs.getInt("validationStatus") == 1) {
				status = true;
			}
		
			
			User author = new UserDAO().selectUser(rs.getString("authorID"));
			
			
			Item item = new ItemDAO().selectItem(rs.getInt("referredItemID"));
			
			article = new Article(
						rs.getInt("articleID"),
						rs.getString("title"),
						status,
						item,
						author
					);
			
			String dateString = rs.getString("publishingDate");
			Date date = null;
			
			if(dateString != null) {
				
				try {
					date = format.parse(dateString);
				} catch (ParseException e) {
					date = null;
				}
				
			}
			
			article.setPublishingDate(date);
			article.setLayout(rs.getString("layout"));
			article.setType(rs.getString("articleType"));
			String body = rs.getString("body");
			String[] texts = body.split(ESCAPE_CHARACTER.toString());
			for(Integer i = 0; i < texts.length - 1; i++)
				article.setText(texts[i], i);
			ImageCache mediaCache = ImageCache.getInstance();
			query = mediaQuery.retrieveAllMedia(articleID);
			rs2 = stmt.executeQuery(query);
			while(rs2.next()) {
				Integer mediaIndex = rs2.getInt("imageIndex");
				String fileName = "A_" + articleID.toString() + "_" + mediaIndex.toString();
				String filePath = mediaCache.addImage(fileName, rs2.getBinaryStream("image"));
				article.addMedia(filePath);
			}
		}catch(SQLException e) {
			e.printStackTrace();
		}finally {
			try {
				if (rs != null) {
					rs.close();
				}
				if (rs2 != null) {
					rs2.close();
				}
				if (stmt != null) {
					stmt.close();
				}
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return article;
	}
	
	public List<Article> getAllArticles(Boolean accepted, User author){
		
		List<Article> articles = new ArrayList<>();
		Statement stmt = null;
		ResultSet rs = null;
		ResultSet rs2 = null;
		Article article = null;
		
		String query;
		
		if(author == null)
			author = new User();
		
		if(Boolean.TRUE.equals(accepted)) {
			query = articleQuery.selectAcceptedArticles(author.getUsername());
		}else {
			query = articleQuery.selectNotAcceptedArticles(author.getUsername());
		}
		
		try {
			
			Connection con = connection.getConnection();
			stmt = con.createStatement();
			System.out.println(query);
			rs = stmt.executeQuery(query);
			
			
			while(rs.next()) {
				
				Boolean status = false;
				
				if(rs.getInt("validationStatus") == 1) {
					status = true;
				}
			
				if(author.getUsername() == null)
				{
					author = new UserDAO().selectUser(rs.getString("authorID"));
				}
				
				Item item = new ItemDAO().selectItem(rs.getInt("referredItemID"));
				
				article = new Article(
							rs.getInt("articleID"),
							rs.getString("title"),
							status,
							item,
							author
						);
				
				String dateString = rs.getString("publishingDate");
				Date date = null;
				
				if(dateString != null) {
					
					try {
						date = format.parse(dateString);
					} catch (ParseException e) {
						date = null;
					}
					
				}
				
				article.setPublishingDate(date);
				article.setLayout(rs.getString("layout"));
				article.setType(rs.getString("articleType"));
				String body = rs.getString("body");
				String[] texts = body.split(ESCAPE_CHARACTER.toString());
				for(Integer i = 0; i < texts.length - 1; i++)
					article.setText(texts[i], i);	
				articles.add(article);
			}
			
			ImageCache mediaCache = ImageCache.getInstance();
			
			for(Article art : articles) {
				Integer articleID = art.getArticleID();
				query = mediaQuery.retrieveAllMedia(articleID);
				rs2 = stmt.executeQuery(query);
				while(rs2.next()) {
					Integer mediaIndex = rs2.getInt("imageIndex");
					String fileName = "A_" + articleID.toString() + "_" + mediaIndex.toString();
					String filePath = mediaCache.addImage(fileName, rs2.getBinaryStream("image"));
					art.addMedia(filePath);
				}
				rs2.close();	
			}
			for(Article art : articles) {
				Integer articleID = art.getArticleID();
				query = tagQuery.retrieveTags(articleID);
				rs2 = stmt.executeQuery(query);
				
				while(rs2.next()) {
					art.addTag(rs2.getString("tagValue"));
				}
				
				rs2.close();
			}
			
			
			
			
		}catch(SQLException e) {
			e.printStackTrace();
		}finally {
			try {
				if (rs != null) {
					rs.close();
				}
				if (rs2 != null) {
					rs2.close();
				}
				if (stmt != null) {
					stmt.close();
				}
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
		
		return articles;
		
	}

	public void deleteArticle(Article article) {
		
		Statement stmt = null;
		try {

			Connection con = connection.getConnection();
			stmt = con.createStatement();
			String query = articleQuery.deleteArticle(article.getArticleID());
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
