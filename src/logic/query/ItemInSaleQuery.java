package logic.query;

public class ItemInSaleQuery extends Query{

	public String selectItemInSale(Integer itemInSaleID) {
		String query = "SELECT * FROM iteminsale WHERE itemInSaleID = %d;";
		return String.format(query, itemInSaleID);
	}
	
	public String insertItemInSale(Integer itemInSaleID, Integer price, String saleDescription, Integer availability, String itemCondition, String preferredLocation, Integer referredItem, String userID) {
		
		saleDescription = quote(saleDescription);
		itemCondition = quote(itemCondition);
		preferredLocation = quote(preferredLocation);
		userID = quote(userID);

		String query = "INSERT INTO iteminsale "+
					   "(itemInSaleID, price, saleDescription, availability, itemCondition,"+
					   "preferredLocation, referredItemID, userID) VALUES (%d, %d, %s, %d, %s, %s, %d, %s);";

		query = String.format(query, itemInSaleID, price, saleDescription, availability, 
							  itemCondition, preferredLocation, referredItem, userID);
		return query;
		
	}
	
	
	public String updateItemInSale(Integer itemInSaleID, Integer price, String saleDescription, Integer availability, String itemCondition, String preferredLocation, Integer referredItem, String userID) {
		
		saleDescription = quote(saleDescription);
		itemCondition = quote(itemCondition);
		preferredLocation = quote(preferredLocation);
		userID = quote(userID);
		
		String query = "UPDATE TABLE iteminsale SET " +
						"price = %d," +
						"saleDescription = %s," +
						"availability = %d," +
						"itemCondition = %s," +
						"preferredLocation = %s," +
						"referredItemID = %d," +
						"userID = %s," +
						"WHERE itemInSaleID = %d;";

		return String.format(query, price, saleDescription, availability, itemCondition, preferredLocation, referredItem, userID, itemInSaleID);
	}
	
	public String deleteItemInSale(int itemInSaleID) {
		String query = "DELETE FROM itemInSale WHERE itemInSaleID = %d;";
		return String.format(query, itemInSaleID);
	}
	
	
}
