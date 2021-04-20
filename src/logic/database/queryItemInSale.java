package logic.database;

import java.sql.ResultSet;

public class queryItemInSale {
	MyDB myDB;
	
	public queryItemInSale() {
		myDB = new MyDB();
		myDB.connect();	
	}
	
	public void insertItemInSale(int itemInSaleID, int price, String saleDescription, boolean availability, char itemCondition, String preferredLocation, int referredItemID, String userID, int referredOrderID) {
		myDB.doUpdate("INSERT INTO iteminsale values("+ itemInSaleID + "," + price + ",'" + saleDescription + "'," + availability + ",'" + itemCondition + "','" + preferredLocation + "'," + referredItemID + ",'" + userID + "'," +referredOrderID);	
	}
	
	public void insertItemInSale(int itemInSaleID, int price, String saleDescription, boolean availability, char itemCondition, String preferredLocation, int referredItemID, String userID) {
		myDB.doUpdate("INSERT INTO iteminsale values(" + itemInSaleID + "," + price + ",'" + saleDescription + "'," + availability + ",'" + itemCondition + "','" + preferredLocation + "'," + referredItemID + ",'" + userID + "', null)");	
	}
	
	public void insertItemInSale(int itemInSaleID, int price,  String preferredLocation, int referredItemID, String userID) {
		myDB.doUpdate("INSERT INTO iteminsale values(" + itemInSaleID + "," + price + ", null," + " true " + ", null ,'" + preferredLocation + "'," + referredItemID + ",'" + userID + "', null)");	
	}
	
	
	public void updatePrice(int newPrice, int itemInSaleID){
		myDB.doUpdate("UPDATE itemInSale SET price = "+ newPrice + " WHERE itemInSaleID = " + itemInSaleID);
	}
	
	public void updateSaleDescription(String saleDescription, int itemInSaleID){
		myDB.doUpdate("UPDATE itemInSale SET saleDescription = '"+ saleDescription + "' WHERE itemInSaleID = " + itemInSaleID);
	}
	
	public void updateAvailability(boolean availability, int itemInSaleID){
		myDB.doUpdate("UPDATE itemInSale SET availability = "+ availability + " WHERE itemInSaleID = " + itemInSaleID);
	}
	
	public void updateItemCondition(char itemCondition, int itemInSaleID){
		myDB.doUpdate("UPDATE itemInSale SET itemCondition = '"+ itemCondition + "' WHERE itemInSaleID = " + itemInSaleID);
	}
	public void updatePreferedLocation(String preferredLocation, int itemInSaleID){
		myDB.doUpdate("UPDATE itemInSale SET preferredLocation = '"+ preferredLocation + "' WHERE itemInSaleID = " + itemInSaleID);
	}
	public void updateReferredOrderID(int referredOrderID, int itemInSaleID){
		myDB.doUpdate("UPDATE itemInSale SET referredOrderID = "+ referredOrderID + " WHERE itemInSaleID = " + itemInSaleID);
	}
	
	
	
	public void selectItemInSale(int ID) {
		ResultSet rs = myDB.makeQuery("SELECT * FROM iteminsale WHERE itemInSaleID = " + ID );
		//da finire
		myDB.showAll(rs);	
	}
	
	public void removeItemInSale(int ID) {
		myDB.doUpdate("DELETE FROM iteminsale where itemInSaleID = " + ID );
	}
	
}
