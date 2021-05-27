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
	
	public String insertOrder(Integer id, Date orderDate, Integer status, String code, Date startDate, String seller, String buyer, Integer item) {
		
		DateFormat format = new SimpleDateFormat("yyyy-mm-dd hh:mm:ss");
		String start = quote(format.format(startDate));
		String end = quote(format.format(orderDate));
		code = quote(code);
		seller = quote(seller);
		buyer = quote(buyer);
		
		String query = "INSERT INTO itemorder (itemInSaleID, price, saleDescription, availability, itemCondition, preferredLocation, "
				+ "referredItemID, userID, referredOrderID) VALUES (%d, %d, %s, %d, %s, %s, %d, %s, %d);";
		return String.format(query, id, end, status, code, start, seller, buyer, item);
	}
	
	public String updateOrder(Integer id, Date orderDate, Date startDate, Integer status, String code) {
	
		DateFormat format = new SimpleDateFormat("yyyy-mm-dd hh:mm:ss");
		String end = quote(format.format(orderDate));
		String start = quote(format.format(startDate));
		code = quote(code);

		String query = "UPDATE TABLE itemorder SET orderDate = %s, startDate = %s, status = %d, code = %s WHERE id = %d;";
		return String.format(query, end, start, status, code);
		
	}
	
	public String deleteOrder(Integer orderID) {
		String query = "DELETE FROM itemOrder WHERE orderID = %d;";
		return String.format(query, orderID);
	}
	
	

}
