package logic.query;

public class RequestQuery extends Query{

		public String insertRequest(String buyer, Integer item, Integer status, String note) {
            buyer = quote(buyer);
            note = quote(note);
			String query = "INSERT INTO request (buyer, referredItemID, requestStatus, note) VALUES (%s, %d, %d, %s);";
			return String.format(query, buyer, item, status, note);
		}
		
		public String selectRequest(String buyer, Integer item) {
			buyer = quote(buyer);
			String query = "SELECT * FROM request WHERE buyer = %s AND referredItemID = %d;";
			return String.format(query, buyer, item);
		}
		
		public String selectAllRequests(String buyer) {
			buyer = quote(buyer);
			String query = "SELECT * FROM request WHERE buyer = %s;";
			return String.format(query, buyer);
		}
		
		public String deleteRequest(String buyer, Integer item) {
			
			buyer = quote(buyer);
			String query = "DELETE FROM request WHERE buyer = %s AND referredItemID = %d;";
			return String.format(query, buyer, item);
		}
}
