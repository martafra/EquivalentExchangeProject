package logic.query;

import logic.entity.ItemInSale;


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
	//private String clReferredOrderID = "referredOrderID";
	
	private String columnsName = " (" + clItemInSaleID + ", "   + clPrice + ", " + clSaleDescription + ", "  + clAvailability+ ", " +
			clItemCondition+ ", " +   clPreferredLocation + ", " +   clReferredItemID + ", " +  clUserID + /*", " +  clReferredOrderID +*/ ") ";
	
	
	public String selectItemInSale(int itemInSaleID) {
		return "SELECT * FROM " + table + " WHERE " + clItemInSaleID + "= " + itemInSaleID ;
	}
	
	public String changeStr(String str) {
		if(str !=null) {
			return "'" + str + "'";
		}
		return str;
	}
	
	public String insertItemInSale(ItemInSale itemInSale) {
		String query = insertStr;
		int itemInSaleID = itemInSale.getItemInSaleID();
		int price = itemInSale.getPrice();
		String saleDescription = changeStr(itemInSale.getDescription());
		String availability = itemInSale.getAvailability().toString();
		String itemCondition = null; //eventuale modifica: inserire nella enumerazione Non Specificato
		if(itemInSale.getCondition()!=null) {
			itemCondition = "'" + itemInSale.getCondition().toString().charAt(0) + "'";
		}
		String preferredLocation = changeStr(itemInSale.getAddress());
		int referredItemID = itemInSale.getReferredItem().getItemID();
		String userID =changeStr(itemInSale.getSeller().getUsername());
		//String referredOrderID = changeStr(itemInSale.get);
		
		String str= String.format(query + columnsName + " VALUES (%d,%d,%s,%s,%s,%s,%d,%s)", itemInSaleID, price, saleDescription,
				availability, itemCondition, preferredLocation, referredItemID, userID);
		return str;
		
	}
	
	
	public String updateItemInSale(ItemInSale itemInSale) {
		String query = updateStr;
		int itemInSaleID = itemInSale.getItemInSaleID();
		int price = itemInSale.getPrice();
		String saleDescription = changeStr(itemInSale.getDescription());
		String availability = itemInSale.getAvailability().toString();
		String itemCondition = null;
		if(itemInSale.getCondition()!=null) {
			itemCondition = "'" + itemInSale.getCondition().toString().charAt(0) + "'";
		}
		String preferredLocation = changeStr(itemInSale.getAddress());
		int referredItemID = itemInSale.getReferredItem().getItemID();
		String userID =changeStr(itemInSale.getSeller().getUsername());

		String str= String.format(query + "  %s = %d, %s = %s, %s = %s, %s = %s, %s = %s, %s = %d, %s = %s WHERE %s = %d", 
				clPrice, price, clSaleDescription, saleDescription, clAvailability, availability, clItemCondition, itemCondition,
				clPreferredLocation, preferredLocation, clReferredItemID, referredItemID, clUserID, userID, clItemInSaleID, itemInSaleID);
		return str;
	}
	
	public String deleteItemInSale(int itemInSaleID) {
		return "DELETE FROM " + table + " WHERE " + clItemInSaleID +" = " + itemInSaleID ;
	}
	
	
}
