package logic.DAO;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import logic.entity.ArticleReview;
import logic.entity.User;
import logic.query.ArticleReviewQuery;
import logic.support.database.MyConnection;


public class ArticleReviewDAO {
	
	private MyConnection connection = MyConnection.getInstance();
	private ArticleReviewQuery reviewQuery = new ArticleReviewQuery();

	public List<ArticleReview> getReviewVotes(Integer articleID){
		
		List<ArticleReview> votes = new ArrayList<>();
		
		Statement stmt = null;
		ResultSet rs = null;
		
		try {

			Connection con = connection.getConnection();
			stmt = con.createStatement();
			String query = reviewQuery.selectReviews(articleID);
			rs = stmt.executeQuery(query);
			while (rs.next()) {
				User author = new UserDAO().selectUser(rs.getString("userID"));
				votes.add(new ArticleReview(rs.getInt("value"), author));
			}
		} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();

			} finally {
				try {
					if (rs != null) {
						rs.close();
					}
					if (stmt != null) {
						stmt.close();
					}
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		
		return votes;
	}
	
	public void addReview(Integer articleID, ArticleReview review) {
		Statement stmt = null;
		
		try {

			Connection con = connection.getConnection();
			stmt = con.createStatement();
			
			String userID = review.getAuthor().getUsername();
			Integer value = review.getValue();
			
			String query = reviewQuery.inserReview(articleID, userID, value);
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
