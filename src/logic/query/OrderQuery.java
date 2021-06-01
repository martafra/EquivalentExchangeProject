package logic.query;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

public class OrderQuery extends Query{
	
	public String selectOrder(int orderID) {
		String query =  "SELECT * FROM itemorder WHERE orderID = %d;";
		return String.format(query, orderID);
	}
	
	public String selectOrdersByUser(String user) {
		user = quote(user);
		String query = "SELECT * FROM itemorder WHERE sellerID = %s OR buyerID = %s;";
		return String.format(query, user, user);
	}
	
	public String insertOrder(Integer id, Date orderDate, String code, Date startDate, String seller, String buyer, Integer item, Integer buyerStatus, Integer sellerStatus) {
		
		DateFormat format = new SimpleDateFormat(dateTimeFormat);
		String start = quote(format.format(startDate));
		String end = quote(format.format(orderDate));
		code = quote(code);
		seller = quote(seller);
		buyer = quote(buyer);
		
		String query = "INSERT INTO itemorder (itemInSaleID, price, saleDescription, itemCondition, preferredLocation, "
				+ "referredItemID, userID, referredOrderID, sellerStatus, buyerStatus) VALUES (%d, %d, %s, %d, %s, %s, %d, %s, %d, %d, %d);";
		return String.format(query, id, end, code, start, seller, buyer, item, sellerStatus, buyerStatus);
	}
	
	public String updateOrder(Integer id, Date orderDate, Date startDate, String code, Integer buyerStatus, Integer sellerStatus) {
	
		DateFormat format = new SimpleDateFormat(dateTimeFormat);
		String end = "null";
		if(orderDate != null)
			end = quote(format.format(orderDate));
		String start = "null";
		if(startDate != null)
			start = quote(format.format(startDate));
		code = quote(code);

		String query = "UPDATE itemorder SET orderDate = %s, startDate = %s, code = %s, buyerStatus = %d, sellerStatus = %d WHERE orderID = %d;";
		return String.format(query, end, start, code, buyerStatus, sellerStatus, id);
		
	}
	
	public String deleteOrder(Integer orderID) {
		String query = "DELETE FROM itemOrder WHERE orderID = %d;";
		return String.format(query, orderID);
	}
	
	

}
