package logic.query;

public class OrderQuery {
	private String table = "itemorder";

	private String updateStr = "UPDATE " + table + " SET ";
	//private String selectAll = "SELECT * from " + table;
	private String insertStr = "INSERT INTO " + table;

	private String clOrderID = "orderID";
	private String clOrderDate = "orderDate";
	private String clStatus = "status";
	private String clCode = "code";
	private String clTimer = "timer";
	private String clSellerID = "sellerID";
	private String clBuyerID = "buyerID";
	private String clReferredItemID = "referredItemID";
	
	public String selectOrder(int orderID) {
		return "SELECT * FROM " + table + " WHERE " + clOrderID + "= "+  orderID;
	}
	
	public String selectOrdersBySeller(String seller) {//ordini conclusi
		return "SELECT * FROM " + table + " WHERE " + clSellerID +  "='"+  seller +"' AND " + clStatus + "= true";
	}
	
	public String selectOrdersByBuyer(String buyer) {//ordini conclusi
		return "SELECT * FROM " + table + " WHERE " + clBuyerID + "='"+ buyer +"' AND" + clStatus + "= true";
	}
	
	public String inserOrder(int orderID, String seller, String buyer, int referredItem) {
		return insertStr + 
				"(" + clOrderID + ", " + clSellerID + ", " + clBuyerID + ", " + clReferredItemID + ") "
				+ "VALUES (" + orderID + ",'" + seller + "','" + buyer + "'," + referredItem + ")";
	}
	
	public String inserOrder(String seller, String buyer, int referredItem) {
		return insertStr + 
				"(" + clSellerID + ", " + clBuyerID + ", " + clReferredItemID + ") "
				+ "VALUES ('" + seller + "','" + buyer + "'," + referredItem + ")";
	}
	
	public String updateOrderDate(int orderID, String orderDate) {
		return updateStr + clOrderDate + " = '" + orderDate + "' WHERE " + clOrderID + "= " + orderID ;
	}

	public String updateStatus(int orderID, boolean status) {
		return updateStr + clStatus + "=  " + status + " WHERE " + clOrderID + "= " + orderID;
	}

	public String updateCode(int orderID, String code) {
		return updateStr + clCode + " = '" + code + "' WHERE " + clOrderID + " = " + orderID ;
	}
	
	public String updateTimer(int orderID, int timer) {
		return updateStr + clTimer + "=  " + timer + " WHERE " + clOrderID + "= " + orderID;
	}
	
	
	

}
