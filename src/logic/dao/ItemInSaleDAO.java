
package logic.dao;

import java.sql.Connection;

import java.util.Map;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import logic.support.database.MyConnection;
import logic.support.other.ImageCache;
import logic.entity.ItemInSale;
import logic.query.ItemInSaleQuery;
import logic.query.MediaQuery;

public class ItemInSaleDAO {

	MyConnection connection = MyConnection.getInstance();
	ItemInSaleQuery itemInSaleQ = new ItemInSaleQuery();
	MediaQuery mediaQuery = new MediaQuery();
	String itemInSaleIDStr = "itemInSaleID";
	String priceStr = "price";
	String saleDescriptionStr = "saleDescription";
	String availabilityStr = "availability";
	String itemConditionStr = "itemCondition";
	String preferredLocationStr = "preferredLocation";
	String referredItemIDStr = "referredItemID";
	String userIDStr = "userID";
	String missingImg = "/logic/view/assets/images/missing.png";
	
	public ItemInSale makeItemInSale(ResultSet rs, ItemDAO itemDAO, UserDAO userDAO) throws SQLException {
		
		return new ItemInSale(rs.getInt(itemInSaleIDStr), 
				rs.getInt(priceStr),
				rs.getString(saleDescriptionStr), 
				rs.getBoolean(availabilityStr), 
				rs.getString(itemConditionStr),
				rs.getString(preferredLocationStr), 
				itemDAO.selectItem(rs.getInt(referredItemIDStr)), 
				userDAO.selectUser(rs.getString(userIDStr)));
		
	}
	
	public void putImg(Statement stmt, ItemInSale itemInSale) throws SQLException {
		ResultSet rs = null;
		
		String query = mediaQuery.selectItemMedia(itemInSale.getItemInSaleID());
		rs = stmt.executeQuery(query);
		ImageCache mediaCache = ImageCache.getInstance();
		
		while(rs.next()) {
			Integer mediaIndex = rs.getInt("imageIndex");
			Integer itemID = itemInSale.getItemInSaleID();
			String fileName = itemID.toString() + "_" + mediaIndex.toString();
			String filePath = mediaCache.addImage(fileName, rs.getBinaryStream("image"));
			itemInSale.addMedia(filePath);
		}
		
		if(itemInSale.getMedia().isEmpty()){
			itemInSale.addMedia(missingImg);
		}
		
		rs.close();
			
	}
	
	public List<ItemInSale> selectItems(String query) {
		ArrayList<ItemInSale> itemInSaleList = new ArrayList<>();
		ItemInSale itemInSale = null;
		Statement stmt = null;
		ResultSet rs = null;	
		
		try {

			Connection con = connection.getConnection();
			stmt = con.createStatement();
			rs = stmt.executeQuery(query);
			
			ItemDAO itemDAO= new ItemDAO();
			UserDAO userDAO= new UserDAO();
			
			while (rs.next()) {
				itemInSale = makeItemInSale(rs, itemDAO, userDAO);
				itemInSaleList.add(itemInSale);
			}
			for(ItemInSale item : itemInSaleList) {
				putImg(stmt, item);
			}

		} catch (SQLException e) {

			e.printStackTrace();

		} finally {
			closeRs(rs);
			closeStmt(stmt);
		}
		return itemInSaleList;
	}
	
	private void closeRs(ResultSet rs) {
		try { if (rs != null) rs.close(); } catch (SQLException e) {e.printStackTrace();}
	}
	
	private void closeStmt(Statement stmt) {
		try { if (stmt != null) stmt.close(); } catch (SQLException e) {e.printStackTrace();}
	}
	
	public String selectQuery(ItemInSale itemInSale, String queryType) {
		Integer itemInSaleID = itemInSale.getItemInSaleID();
		Integer price = itemInSale.getPrice();
		String saleDescription = itemInSale.getDescription();
		Integer availability = 0;
		if(Boolean.TRUE.equals(itemInSale.getAvailability()))
			availability = 1;
		String itemCondition = itemInSale.getCondition().toString().substring(0,1);
		String preferredLocation = itemInSale.getAddress();
		Integer referredItem = itemInSale.getReferredItem().getItemID();
		String userID = itemInSale.getSeller().getUsername();
		String query;		
		if(queryType.equals("insert")) {
			query = itemInSaleQ.insertItemInSale(itemInSaleID, price, saleDescription, availability, 
					itemCondition, preferredLocation, referredItem, userID);
		}
		else {
			query = itemInSaleQ.updateItemInSale(itemInSaleID, price, saleDescription, availability, 
					itemCondition, preferredLocation, referredItem, userID);	
		}
		return query;
	}
	
	public List<ItemInSale> selectItemsInSaleByUser(String userID){
		String query = itemInSaleQ.selectItemsByUser(userID);
		return selectItems(query);	
		
	}
	public ItemInSale selectItemInSale(int itemInSaleID) {
		String query = itemInSaleQ.selectItemInSale(itemInSaleID);
		List<ItemInSale> itemList = selectItems(query);	
		if(itemList.isEmpty()) {
			return null;
		}
		return itemList.get(0);

	}
	
	public void insertItemInSale(ItemInSale itemInSale) {
		Statement stmt = null;
		
		try {
			Connection con = connection.getConnection();
			stmt = con.createStatement();
			String query = selectQuery(itemInSale, "insert");
			stmt.executeUpdate(query);

			Integer mediaID = 0;

			for(String mediaPath : itemInSale.getMedia()){
				query = mediaQuery.insertItemMedia(mediaPath, mediaID, itemInSale.getItemInSaleID());
				stmt.executeUpdate(query);

				mediaID++;
			}

		} catch (SQLException e) {

			e.printStackTrace();

		} finally {
			try {
				if (stmt != null) {
					stmt.close();
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		
	}
	
	
	public void updateItemInSale(ItemInSale itemInSale) {
		Statement stmt = null;
		try {

			Connection con = connection.getConnection();
			stmt = con.createStatement();
			String query = selectQuery(itemInSale, "update");
			stmt.executeUpdate(query);

		} catch (SQLException e) {

			e.printStackTrace();

		} finally {
			try {
				if (stmt != null) {
					stmt.close();
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}
	
	
	
	public void deleteItemInSale(int itemInSaleID) {
		Statement stmt = null;
		Statement stmtm = null;
		try {

			Connection con = connection.getConnection();
			stmt = con.createStatement();
			stmtm = con.createStatement();
			String querym = mediaQuery.removeItemMedia(itemInSaleID);
			stmtm.executeUpdate(querym);
			String query = itemInSaleQ.deleteItemInSale(itemInSaleID);
			stmt.executeUpdate(query);

		} catch (SQLException e) {

			e.printStackTrace();

		} finally {
			try { if (stmtm != null) stmtm.close(); } catch (SQLException e) {e.printStackTrace();}
			try { if (stmt != null) stmt.close(); } catch (SQLException e) {e.printStackTrace();}
		}
		
	}
	
	
	public List<ItemInSale> getOtherItem(String seller, String itemName){
		String query = itemInSaleQ.getOtherItemInSale(seller, itemName);
		return selectItems(query);
			
	}
	
	public List<ItemInSale> getItemsInSaleListFiltered(String loggedUser, Map<String, String> filters){
		String query = itemInSaleQ.getItemsInSaleFiltered(loggedUser, filters);
		return selectItems(query);
			
	}
	
	
	public List<ItemInSale> getItemInSaleWishlist(String userID){
		String query = itemInSaleQ.getItemInSaleWishlist(userID);
		return selectItems(query);
		
	}
	
	

}
