package logic.dao;
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
	private static final  Character ESCAPE_CHARACTER = 'ç';
	private ArticleQuery articleQuery = new ArticleQuery();
	private ArticleMediaQuery mediaQuery = new ArticleMediaQuery();
	private ImageCache mediaCache = ImageCache.getInstance();
	private TagQuery tagQuery = new TagQuery();
	private DateFormat format = new SimpleDateFormat("yyyy-MM-dd");
	
	
	public void insertArticle(Article article) {
		Statement stmt = null;
		try {
			var con = connection.getConnection();
			stmt = con.createStatement();
			Integer articleID = article.getArticleID();
			String title = article.getTitle();
			StringBuilder body = new StringBuilder();
			for(Integer i = 0; i < 4; i++) {
				body.append(article.getText(i) + ESCAPE_CHARACTER.toString());
			}
			LayoutType layout = article.getLayout();
			var layoutString = layout.toString().substring(0,1);
			ArticleType type = article.getType();
			var typeString = type.toString().substring(0,1);
			String authorID = article.getAuthor().getUsername();
			Boolean validationStatus = article.isValidated();
			Integer reviewPoints = 0;
			var publishingDate = article.getPublishingDate();
			
			Integer referredItemID = article.getReferredItem().getItemID();
			
			String query = articleQuery.insertArticle(articleID, referredItemID, title, body.toString(), layoutString, typeString, validationStatus, authorID, reviewPoints, publishingDate);
			
			stmt.executeUpdate(query);
			
			Integer mediaID = 0;

			for(String mediaPath : article.getAllMedia()){
				query = mediaQuery.insertMedia(articleID, mediaPath, mediaID);
				stmt.executeUpdate(query);
				mediaID++;
			}
			
			for(String tag : article.getTags()) {
				query = tagQuery.insertTagForArticle(tag, articleID);
				stmt.executeUpdate(query);
			}
			
			

		} catch (SQLException e) {
			e.printStackTrace();

		} finally {
			try {
				if (stmt != null) {
					stmt.close();
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		
	}
	
	public void updateArticle(Article article) {
		Statement stmt = null;
		try {
			var con = connection.getConnection();
			stmt = con.createStatement();
			Integer articleID = article.getArticleID();
			String title = article.getTitle();
			StringBuilder body = new StringBuilder();
			for(Integer i = 0; i < 4; i++) {
				body.append(article.getText(i) + ESCAPE_CHARACTER.toString());
			}
			
			LayoutType layout = article.getLayout();
			var layoutString = layout.toString().substring(0,1);
			ArticleType type = article.getType();
			var typeString = type.toString().substring(0,1);
			Boolean validationStatus = article.isValidated();
			Integer reviewPoints = 0;
			
			String query = articleQuery.updateArticle(articleID, title, body.toString(), layoutString, typeString, validationStatus, reviewPoints);
			
			stmt.executeUpdate(query);
			} catch (SQLException e) {
				e.printStackTrace();

			} finally {
				try {
					if (stmt != null) {
						stmt.close();
					}
				} catch (SQLException e) {
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
			
			var con = connection.getConnection();
			stmt = con.createStatement();
			rs = stmt.executeQuery(query);
			
			if(!rs.next())
				return null;
			Boolean status = false;
			
			if(rs.getInt("validationStatus") == 1) {
				status = true;
			}
		
			
			var author = new UserDAO().selectUser(rs.getString("authorID"));
			
			
			var item = new ItemDAO().selectItem(rs.getInt("referredItemID"));
			
			article = new Article(
						rs.getInt("articleID"),
						rs.getString("title"),
						status,
						item,
						author
					);
			
			var dateString = rs.getString("publishingDate");
			Date date = stringToDate(dateString);
			
			article.setPublishingDate(date);
			article.setLayout(rs.getString("layout"));
			article.setType(rs.getString("articleType"));
			var body = rs.getString("body");
			String[] texts = body.split(ESCAPE_CHARACTER.toString());
			for(Integer i = 0; i < texts.length ; i++)
				article.setText(texts[i], i);
			query = mediaQuery.retrieveAllMedia(articleID);
			rs2 = stmt.executeQuery(query);
			while(rs2.next()) {
				Integer mediaIndex = rs2.getInt("imageIndex");
				String fileName = "A_" + articleID.toString() + "_" + mediaIndex.toString();
				String filePath = mediaCache.addImage(fileName, rs2.getBinaryStream("image"));
				article.addMedia(filePath);
			}
			
			if(article.getAllMedia().isEmpty())
			{
				article.addMedia("/logic/view/assets/images/missing.png");
			}
			
		}catch(SQLException e) {
			e.printStackTrace();
		}finally {
			try { if (rs != null) rs.close(); } catch (SQLException e) {e.printStackTrace();}
			try { if (rs2 != null) rs2.close(); } catch (SQLException e) {e.printStackTrace();}
			try { if (stmt != null) stmt.close(); } catch (SQLException e) {e.printStackTrace();}
		}
		return article;
	}
	
	public List<Article> getAllArticles(Boolean accepted, User author){
		
		List<Article> articles = new ArrayList<>();
		Statement stmt = null;
		ResultSet rs = null;
		ResultSet rs2 = null;
		
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
			rs = stmt.executeQuery(query);
				
			while(rs.next()) {
				Article article = rsToArticle(rs);	
				articles.add(article);
			}
			for(Article art : articles) {
				addImagesToArticle(stmt, art);
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
			try { if (rs != null) rs.close(); } catch (SQLException e) {e.printStackTrace();}
			try { if (rs2 != null) rs2.close(); } catch (SQLException e) {e.printStackTrace();}
			try { if (stmt != null) stmt.close(); } catch (SQLException e) {e.printStackTrace();}
		}
		
		
		return articles;
		
	}

	public void deleteArticle(Article article) {
		
		Statement stmt = null;
		try {

			var con = connection.getConnection();
			stmt = con.createStatement();
			String query = articleQuery.deleteArticle(article.getArticleID());
			stmt.executeUpdate(query);

		} catch (SQLException e) {

			e.printStackTrace();

		} finally {
			try {if (stmt != null) stmt.close(); } catch (SQLException e) {e.printStackTrace();}
		}
		
		
	}
	
	private Date stringToDate(String dateString) {
		Date date = null;
		
		if(dateString != null) {
			
			try {
				date = format.parse(dateString);
				return date;
			} catch (ParseException e) {
				return null;
			}	
		}
		return date;
	}
	
	private void addImagesToArticle(Statement stmt, Article art) throws SQLException {
		Integer articleID = art.getArticleID();
		String query = mediaQuery.retrieveAllMedia(articleID);
		ResultSet rs2 = stmt.executeQuery(query);
		while(rs2.next()) {
			Integer mediaIndex = rs2.getInt("imageIndex");
			String fileName = "A_" + articleID.toString() + "_" + mediaIndex.toString();
			String filePath = mediaCache.addImage(fileName, rs2.getBinaryStream("image"));
			art.addMedia(filePath);
		}
		
		if(art.getAllMedia().isEmpty())
		{
			art.addMedia("/logic/view/assets/images/missing.png");
		}
		
		rs2.close();
	}
	
	private Article rsToArticle(ResultSet rs) throws SQLException {
		Boolean status = false;
		
		if(rs.getInt("validationStatus") == 1) {
			status = true;
		}
		User author = new UserDAO().selectUser(rs.getString("authorID"));

		
		var item = new ItemDAO().selectItem(rs.getInt("referredItemID"));
		
		Article article = new Article(
					rs.getInt("articleID"),
					rs.getString("title"),
					status,
					item,
					author
				);
		
		var dateString = rs.getString("publishingDate");
		Date date = stringToDate(dateString);
		
		article.setPublishingDate(date);
		article.setLayout(rs.getString("layout"));
		article.setType(rs.getString("articleType"));
		var body = rs.getString("body");
		String[] texts = body.split(ESCAPE_CHARACTER.toString());
		for(Integer i = 0; i < texts.length  ; i++)
			article.setText(texts[i], i);
		return article;
	}
	
}
