package logic.DAO;

import java.sql.Connection;


import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;



import logic.support.database.MyConnection;
import logic.entity.ItemInSale;
import logic.query.ItemInSaleQuery;
import logic.query.MediaQuery;

public class ItemInSaleDAO {

	MyConnection connection = MyConnection.getInstance();
	ItemInSaleQuery itemInSaleQ = new ItemInSaleQuery();
	MediaQuery mediaQuery = new MediaQuery();
	
	public ItemInSale selectItemInSale(int itemInSaleID) {
		ItemInSale itemInSale = null;
		Statement stmt = null;
		ResultSet rs = null;
		try {

			Connection con = connection.getConnection();
			stmt = con.createStatement();
			String query = itemInSaleQ.selectItemInSale(itemInSaleID);
			rs = stmt.executeQuery(query);

			if (!rs.next()) {
				return null;
			}
			
			ItemDAO itemDAO= new ItemDAO();
			UserDAO userDAO= new UserDAO();
			
			itemInSale = new ItemInSale(rs.getInt("itemInSaleID"), 
										rs.getInt("price"),
										rs.getString("saleDescription"), 
										rs.getBoolean("availability"), 
										rs.getString("itemCondition"),
										rs.getString("preferredLocation"), 
										itemDAO.selectItem(rs.getInt("referredItemID")), 
										userDAO.selectUser(rs.getString("userID")));

		} catch (SQLException e) {
			// TODO Auto-generated catch block

			e.printStackTrace();
			System.out.println("Attenzione: Errore nella ItemInSaleDao.selectItemInSale()");

		} finally {
			try {
				if (rs != null) {
					rs.close();
				}

				if (stmt != null) {
					stmt.close();
				}
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return itemInSale;

	}
	
	public void insertItemInSale(ItemInSale itemInSale) {
		Statement stmt = null;
		
		try {
			Connection con = connection.getConnection();
			stmt = con.createStatement();
			Integer itemInSaleID = itemInSale.getItemInSaleID();
			Integer price = itemInSale.getPrice();
			String saleDescription = itemInSale.getDescription();
			Integer availability = 0;
			if(itemInSale.getAvailability())
				availability = 1;
			String itemCondition = itemInSale.getCondition().toString().substring(0,1);
			String preferredLocation = itemInSale.getAddress();
			Integer referredItem = itemInSale.getReferredItem().getItemID();
			String userID = itemInSale.getSeller().getUsername();

			String query = itemInSaleQ.insertItemInSale(itemInSaleID, price, saleDescription, availability, 
														itemCondition, preferredLocation, referredItem, userID);
			stmt.executeUpdate(query);

			//TODO valutare se avviare un thread per caricare ogni singola immagine
			Integer mediaID = 0;

			for(String mediaPath : itemInSale.getMedia()){
				query = mediaQuery.insertItemMedia(mediaPath, mediaID, itemInSale.getItemInSaleID());
				System.out.println(query);
				stmt.executeUpdate(query);

				mediaID++;
			}



		} catch (SQLException e) {
			// TODO Auto-generated catch block

			e.printStackTrace();
			System.out.println("Attenzione: Errore nella ItemInSaleDao.insertItemInSale()"); //TODO verbose - a cosa serve se c'è printStackTrace?

		} finally {
			try {
				if (stmt != null) {
					stmt.close();
				}
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
	}
	

	
	public void updateItemInSale(ItemInSale itemInSale) {
		Statement stmt = null;
		try {

			Connection con = connection.getConnection();
			stmt = con.createStatement();
			Integer itemInSaleID = itemInSale.getItemInSaleID();
			Integer price = itemInSale.getPrice();
			String saleDescription = itemInSale.getDescription();
			Integer availability = 0;
			if(itemInSale.getAvailability())
				availability = 1;
			String itemCondition = itemInSale.getCondition().toString().substring(0,1);
			String preferredLocation = itemInSale.getAddress();
			Integer referredItem = itemInSale.getReferredItem().getItemID();
			String userID = itemInSale.getSeller().getUsername();

			String query = itemInSaleQ.updateItemInSale(itemInSaleID, price, saleDescription, availability, 
														itemCondition, preferredLocation, referredItem, userID);
			stmt.executeUpdate(query);

		} catch (SQLException e) {
			// TODO Auto-generated catch block

			e.printStackTrace();
			System.out.println("Attenzione: Errore nella ItemInSaleDao.updateItemInSale()");

		} finally {
			try {
				if (stmt != null) {
					stmt.close();
				}
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
	
	
	
	public void deleteItemInSale(int itemInSaleID) {
		Statement stmt = null;
		try {

			Connection con = connection.getConnection();
			stmt = con.createStatement();
			String query = itemInSaleQ.deleteItemInSale(itemInSaleID);
			stmt.executeUpdate(query);

		} catch (SQLException e) {
			// TODO Auto-generated catch block

			e.printStackTrace();
			System.out.println("Attenzione: Errore nella ItemInSaleDao.deleteItemInSale()");

		} finally {
			try {
				if (stmt != null) {
					stmt.close();
				}
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
	}
	
	
}
