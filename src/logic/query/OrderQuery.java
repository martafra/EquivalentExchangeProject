package logic.query;


import logic.entity.Order;

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
	
	private String columnsName = " (" + clOrderID + ", "   + clOrderDate + ", " + clStatus + ", "  + clCode + ", " +
			clTimer+ ", " +   clSellerID + ", " +  clBuyerID + ", " +  clReferredItemID + ") ";
	
	public String selectOrder(int orderID) {
		return "SELECT * FROM " + table + " WHERE " + clOrderID + "= "+  orderID;
	}
	
	public String selectOrdersBySeller(String seller) {//ordini conclusi
		return "SELECT * FROM " + table + " WHERE " + clSellerID +  "='"+  seller +"' AND " + clStatus + "= true";
	}
	
	public String selectOrdersByBuyer(String buyer) {//ordini conclusi
		return "SELECT * FROM " + table + " WHERE " + clBuyerID + "='"+ buyer +"' AND" + clStatus + "= true";
	}
	
	public String changeStr(String str) {
		if(str !=null) {
			return "'" + str + "'";
		}
		return str;
	}
	
	public String insertOrder(Order order) {
		String query = insertStr;
		int orderID = order.getOrderID();
		String orderDate = null; 
		if(order.getOrderDate()!=null) {
			orderDate = changeStr(order.getOrderDate().toString());
		}
		String status = order.getOrderStatus().toString();
		String code = changeStr(order.getCode());
		String timer = null;
		String sellerID = changeStr(order.getinvolvedItem().getSeller().getUsername());
		String buyerID = changeStr(order.getBuyer().getUsername());
		int referredItemID = order.getinvolvedItem().getItemInSaleID();
		
		String str= String.format(query + columnsName + " VALUES (%d,%s,%s,%s,%s,%s,%s,%d)", orderID, orderDate, status,
				code, timer, sellerID, buyerID, referredItemID);
		return str;
	}
	
	public String updateOrder(Order order) {
		String query = updateStr;
		int orderID = order.getOrderID();
		String orderDate = null; 
		if(order.getOrderDate()!=null) {
			orderDate = changeStr(order.getOrderDate().toString());
		}
		String status = order.getOrderStatus().toString();
		String code = changeStr(order.getCode());
		String timer = null;
		String sellerID = changeStr(order.getinvolvedItem().getSeller().getUsername());
		String buyerID = changeStr(order.getBuyer().getUsername());
		int referredItemID = order.getinvolvedItem().getItemInSaleID();

		String str= String.format(query + "  %s = %s, %s = %s, %s = %s, %s = %s, %s = %s, %s = %s, %s = %d WHERE %s = %d", 
				clOrderDate, orderDate, clStatus, status, clCode, code,
				clTimer, timer, clSellerID, sellerID, clBuyerID, buyerID, clReferredItemID, referredItemID, clOrderID, orderID);
		return str;
	}
	
	public String deleteOrder(int orderID) {
		return "DELETE FROM " + table + " WHERE " + clOrderID +" = " + orderID  ;
	}
	
	

}
