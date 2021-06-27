package logic.query;

public class WishlistQuery extends Query{
	
	public String selectAllFromWishlist(String userID) {
		userID = quote(userID);
		String query = "SELECT * FROM wishlist WHERE userID = %s;";
		return String.format(query, userID);		
	}
	
	public String checkItemInWishlist(String userID, Integer itemID) {
		userID = quote(userID);
		String query = "SELECT * FROM wishlist WHERE userID = %s AND itemID = %d;";
		return String.format(query, userID, itemID);		
	}
	
	public String insertToWishlist(String userID, Integer itemID) {
		userID = quote(userID);
		String query = "INSERT INTO wishlist (userID, itemID) VALUES (%s, %d);";
		return String.format(query, userID, itemID);		
	}
	
	
	public String deleteFromWishlist(String userID, Integer itemID) {
		userID = quote(userID);
		String query = "DELETE FROM wishlist WHERE itemID = %d AND userID = %s;";
		return String.format(query, itemID, userID);
	}
	
	

}
