package logic.query;

public class ArticleReviewQuery extends Query{

	public String selectReviews(Integer articleID) {
		String query = "SELECT * FROM ArticleReview WHERE articleID = %d;";
		return String.format(query, articleID);
	}
	
	public String inserReview(Integer articleID, String userID, Integer value) {
		
		userID = quote(userID);
		
		String query = "INSERT INTO ArticleReview (articleIDm userID, value) VALUES (%d, %s, %d);";
		return String.format(query, articleID, userID, value);
		
	}
	
}
