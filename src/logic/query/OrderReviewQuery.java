package logic.query;

public class OrderReviewQuery extends Query{
	
	public String insertOrderReview(Integer rel, Integer ava, Integer cond, String note, Integer order) {
		String query = "INSERT INTO OrderReview (sellerReliability, sellerAvailability, itemCondition, buyerNote, orderID) VALUES (%d, %d, %d, %s, %d);";
		return String.format(query, rel, ava, cond, note, order);
	}
	
	public String selectOrderReview(Integer order) {
		String query = "SELECT * FROM OrderReview WHERE orderID = %d;";
		return String.format(query, order);
	}
}
