package logic.DAO;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;


import logic.database.MyConnection;
import logic.entity.Item;
import logic.entity.ItemInSale;
import logic.entity.User;
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
			Item item = itemDAO.selectItem(rs.getInt("referredItemID"));
			
			UserDAO userDAO= new UserDAO();
			User user = userDAO.selectUser(rs.getString("userID"));
			
			itemInSale = new ItemInSale(rs.getInt("itemInSaleID"), rs.getInt("price"),rs.getString("saleDescription"), rs.getBoolean("availability"), rs.getString("itemCondition"),
					rs.getString("preferredLocation"), item, user);

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
			String query = itemInSaleQ.insertItemInSale(itemInSale);
			stmt.executeUpdate(query);

			//TODO valutare se avviare un thread per caricare ogni singola immagine
			Integer mediaID = 0;

			for(String mediaPath : itemInSale.getMedia()){
				query = mediaQuery.insertItemMedia(mediaPath, mediaID, itemInSale.getItemInSaleID());
				stmt.executeUpdate(query);
				mediaID++;
			}



		} catch (SQLException e) {
			// TODO Auto-generated catch block

			e.printStackTrace();
			System.out.println("Attenzione: Errore nella ItemInSaleDao.insertItemInSale()"); //TODO a cosa serve se c'è printStackTrace?

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
			String query = itemInSaleQ.updateItemInSale(itemInSale);
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
