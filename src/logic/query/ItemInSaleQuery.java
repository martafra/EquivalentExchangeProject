package logic.query;

public class ItemInSaleQuery {
	private String table = "iteminsale";

	private String updateStr = "UPDATE " + table + " SET ";
	//private String selectAll = "SELECT * from " + table;
	private String insertStr = "INSERT INTO " + table;

	private String clItemInSaleID = "itemInSaleID";
	private String clPrice = "price";
	private String clSaleDescription = "saleDescription";
	private String clAvailability = "availability";
	private String clItemCondition = "itemCondition";
	private String clPreferredLocation = "preferredLocation";
	private String clReferredItemID = "referredItemID";
	private String clUserID = "userID";
	private String clReferredOrderID = "referredOrderID";
	
	public String selectItemInSale(int itemInSaleID) {
		return "SELECT * FROM " + table + " WHERE " + clItemInSaleID + "='" + itemInSaleID + "'";
	}
	
	public String insertItemInSale(int itemInSaleID, int price,  String preferredLocation, int referredItemID, String userID) {
		return insertStr + 
						"(" + clItemInSaleID + ", " + clPrice + ", " + clAvailability + ", " + clPreferredLocation + ", " + clReferredItemID + ", "
								+ clUserID + ") "
						+ "VALUES (" + itemInSaleID + "," + price + ", true, '" + preferredLocation + "'," + referredItemID + ",'"
								+ userID + "')";
	}
	
	public String insertItemInSale(int price,  String preferredLocation, int referredItemID, String userID) {
		return insertStr + 
						"(" + clPrice + ", " + clAvailability + ", " + clPreferredLocation + ", " + clReferredItemID + ", "
								+ clUserID + ") "
						+ "VALUES (" + price + ",true, '" + preferredLocation + "'," + referredItemID + ",'"
								+ userID + "')";
	}
	
	public String updatePrice(int itemInSaleID,int newPrice){
		return updateStr + clPrice + " = " + newPrice + " WHERE " + clItemInSaleID + "= " + itemInSaleID ;
	}
	
	public String updateSaleDescription(int itemInSaleID, String saleDescription){
		return updateStr + clSaleDescription + " = '" + saleDescription + "' WHERE " + clItemInSaleID + "= " + itemInSaleID;
	}
	
	public String updateAvailability(int itemInSaleID,boolean availability){
		return updateStr + clAvailability + " = " + availability + " WHERE " + clItemInSaleID + "= " + itemInSaleID ;
	}
	
	public String updateItemCondition(int itemInSaleID,char itemCondition){
		return updateStr + clItemCondition + " = '" + itemCondition + "' WHERE " + clItemInSaleID + "= " + itemInSaleID ;
	}
	
	public String updatePreferredLocation(int itemInSaleID,String peferredLocation){
		return updateStr + clPreferredLocation + " = '" + peferredLocation + "' WHERE " + clItemInSaleID + "= " + itemInSaleID ;
	}
	
	public String updateReferredOrderID(int itemInSaleID,int referredOrderID){
		return updateStr + clReferredOrderID + " = " + referredOrderID + " WHERE " + clItemInSaleID + "= " + itemInSaleID ;
	}
	
	public String removeItemInSale(int itemInSaleID) {
		return "DELETE FROM "+ table + " WHERE " + clItemInSaleID +"= " + itemInSaleID ;
	}
}
