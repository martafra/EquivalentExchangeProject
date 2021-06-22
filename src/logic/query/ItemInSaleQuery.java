package logic.query;

import java.util.Map;

public class ItemInSaleQuery extends Query{

	public String selectItemsByUser(String userID){
		userID = quote(userID);
		String query = "SELECT * FROM iteminsale WHERE userID = %s;";
		return String.format(query, userID);
	}

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
		
		String query = "UPDATE iteminsale SET " +
						"price = %d," +
						"saleDescription = %s," +
						"availability = %d," +
						"itemCondition = %s," +
						"preferredLocation = %s," +
						"referredItemID = %d," +
						"userID = %s " +
						"WHERE itemInSaleID = %d;";

		return String.format(query, price, saleDescription, availability, itemCondition, preferredLocation, referredItem, userID, itemInSaleID);
	}
	
	public String deleteItemInSale(int itemInSaleID) {
		String query = "DELETE FROM itemInSale WHERE itemInSaleID = %d;";
		return String.format(query, itemInSaleID);
	}
	
	public String getOtherItemInSale(String seller, String itemName) {
		itemName = quote(itemName);
		seller = quote(seller);
		String query = "SELECT * FROM ItemInSale WHERE referredItemID in (SELECT itemId FROM Item WHERE itemName = %s) AND userID != %s";
		return String.format(query, itemName, seller);
	}
	

	public String getItemsInSaleFiltered(String loggedUser, Map<String, String> filters) {
		
		String queryFilters = "";
		
		if ( loggedUser != null) { 
			queryFilters += " AND userID != '" + loggedUser + "' ";
		}
		
		
		if ( filters.containsKey("searchKey") || filters.containsKey("type") ) { 
			queryFilters += getItemFilters(filters);
		}

		if (filters.containsKey("orderBy")) {
			queryFilters += getOrderFilter(filters.get("orderBy"));
		}
			
		String query = 	"SELECT * FROM ItemInSale WHERE availability = 1 %s"  ;
		return String.format(query, queryFilters);
		
	}
	
	public String getOrderFilter(String filter) {
		String sortBy = "";
		if (filter.equals("Rising Price")){
			sortBy = "price";
		}
		else if (filter.equals("Decreasing Price")){
			sortBy = "price DESC";
		}
		
		return " ORDER BY " + sortBy;
	}
	
	public String getItemFilters(Map<String,String> filters) {
		String filter = "";
		if (filters.containsKey("searchKey") ) { // Se e' presente la parola di ricerca
			filter = " itemName like '%%" + filters.get("searchKey") + "%%' ";
			if (filters.containsKey("type")) {
				filter += " and ";
			}
		}
		
		if (filters.containsKey("type")) { // Abbiamo il tipo
			
			filter += " itemType = '" + filters.get("type") + "' ";
			
			if (filters.containsKey("genre")) { // Abbiamo il genere del tipo
				filter += " AND genre = '" + filters.get("genre") + "' ";
			}
			
			if (filters.containsKey("console")) { // abbiamo un secondo attributo di filtro --> dovrebbe essere solo la console in caso di videogame
				filter += " AND itemID in (SELECT itemId FROM Console WHERE consoleName = '" + filters.get("console") + "') "; // TODO controllare tabelle database
			}
		}
		return " AND referredItemID in (SELECT itemId FROM Item WHERE " + filter + ") ";
	}
	
	
	
	
}
