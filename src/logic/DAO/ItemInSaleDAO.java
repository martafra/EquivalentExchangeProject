package logic.DAO;

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
	
	public List<ItemInSale> selectItemsInSaleByUser(String userID){
		ArrayList<ItemInSale> itemList = new ArrayList<>();
		ItemInSale itemInSale = null;
		Statement stmt = null;
		ResultSet rs = null;
		ResultSet rs2 = null;
		
		try {

			Connection con = connection.getConnection();
			stmt = con.createStatement();
			String query = itemInSaleQ.selectItemsByUser(userID);
			rs = stmt.executeQuery(query);

			ItemDAO itemDAO= new ItemDAO();
			UserDAO userDAO= new UserDAO();
			
			while (rs.next()) {
				itemInSale = new ItemInSale(rs.getInt("itemInSaleID"), 
											rs.getInt("price"),
											rs.getString("saleDescription"), 
											rs.getBoolean("availability"), 
											rs.getString("itemCondition"),
											rs.getString("preferredLocation"), 
											itemDAO.selectItem(rs.getInt("referredItemID")), 
											userDAO.selectUser(rs.getString("userID")));
				itemList.add(itemInSale);
			}
			
			ImageCache mediaCache = ImageCache.getInstance();
			
			for(ItemInSale item : itemList) {
				Integer itemID = item.getItemInSaleID();
				query = mediaQuery.selectItemMedia(itemID);
				rs2 = stmt.executeQuery(query);
				while(rs2.next()) {
					Integer mediaIndex = rs2.getInt("imageIndex");
					String fileName = itemID.toString() + "_" + mediaIndex.toString();
					String filePath = mediaCache.addImage(fileName, rs2.getBinaryStream("image"));
					item.addMedia(filePath);
				}
				if(item.getMedia().isEmpty())
				{
					item.addMedia("/logic/view/assets/images/missing.png");
				}
				rs2.close();
			}	
		} catch (SQLException e) {
				// TODO Auto-generated catch block

				e.printStackTrace();
				System.out.println("Attenzione: Errore nella ItemInSaleDao.selectItemInSale()");

			} finally {
				try {
					if (rs != null) {
						rs.close();
					}
					if (rs2 != null) {
						rs2.close();
					}
					if (stmt != null) {
						stmt.close();
					}
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}

		return itemList;
	}
	public ItemInSale selectItemInSale(int itemInSaleID) {
		ItemInSale itemInSale = null;
		Statement stmt = null;
		ResultSet rs = null;
		ResultSet rs2 = null;
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
			
			query = mediaQuery.selectItemMedia(itemInSale.getItemInSaleID());
			rs2 = stmt.executeQuery(query);
			
			ImageCache mediaCache = ImageCache.getInstance();
			
			while(rs2.next()) {
				Integer mediaIndex = rs2.getInt("imageIndex");
				Integer itemID = itemInSale.getItemInSaleID();
				String fileName = itemID.toString() + "_" + mediaIndex.toString();
				String filePath = mediaCache.addImage(fileName, rs2.getBinaryStream("image"));
				itemInSale.addMedia(filePath);
			}
			
			if(itemInSale.getMedia().isEmpty())
			{
				itemInSale.addMedia("/logic/view/assets/images/missing.png");
			}

			if(itemInSale.getMedia().isEmpty()){
				itemInSale.addMedia("/logic/view/assets/images/missing.png");
			}

			
		} catch (SQLException e) {
			// TODO Auto-generated catch block

			e.printStackTrace();
			System.out.println("Attenzione: Errore nella ItemInSaleDao.selectItemInSale()");

		} finally {
			try {
				if (rs != null) {
					rs.close();
				}
				if (rs2 != null) {
					rs2.close();
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
	
	public List<ItemInSale> getItemsInSaleList(){
		ArrayList<ItemInSale> itemInSaleList = new ArrayList<>();
		ItemInSale itemInSale = null;
		Statement stmt = null;
		ResultSet rs2 = null;
		ResultSet rs = null;
		ItemDAO itemDAO= new ItemDAO();
		UserDAO userDAO= new UserDAO();
		try {
			Connection con = connection.getConnection();
			stmt = con.createStatement();
			String query = itemInSaleQ.getAllItemsInSale();
			rs = stmt.executeQuery(query);
			
			while(rs.next()) {
				itemInSale = new ItemInSale(rs.getInt("itemInSaleID"), 
											rs.getInt("price"),
											rs.getString("saleDescription"), 
											rs.getBoolean("availability"), 
											rs.getString("itemCondition"),
											rs.getString("preferredLocation"), 
											itemDAO.selectItem(rs.getInt("referredItemID")), 
											userDAO.selectUser(rs.getString("userID")));
				itemInSaleList.add(itemInSale); 
			}
			
			ImageCache mediaCache = ImageCache.getInstance();
			for(ItemInSale item : itemInSaleList) {
				Integer itemID = item.getItemInSaleID();
				query = mediaQuery.selectItemMedia(itemID);
				rs2 = stmt.executeQuery(query);
				while(rs2.next()) {
					Integer mediaIndex = rs2.getInt("imageIndex");
					String fileName = itemID.toString() + "_" + mediaIndex.toString();
					String filePath = mediaCache.addImage(fileName, rs2.getBinaryStream("image"));
					item.addMedia(filePath);
				}
				if(item.getMedia().isEmpty()){
					item.addMedia("/logic/view/assets/images/missing.png");
				}
				rs2.close();
			}	
		}catch(SQLException e) {
			//TODO gestire eccezione
			e.printStackTrace();
			System.out.println("Attenzione: Errore nella ItemInSaleDao.getAllItemInSale()");
		}
		
		finally {
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
		return itemInSaleList;
			
	}
	
	public List<ItemInSale> getItemsInSaleListFiltered(String loggedUser, Map<String, String> filters){
		ArrayList<ItemInSale> itemInSaleList = new ArrayList<>();
		ItemInSale itemInSale = null;
		Statement stmt = null;
		ResultSet rs2 = null;
		ResultSet rs = null;
		try {
			Connection con = connection.getConnection();
			stmt = con.createStatement();
			String query = itemInSaleQ.getItemsInSaleFiltered(loggedUser, filters);
			rs = stmt.executeQuery(query);
			
			while(rs.next()) {
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
				itemInSaleList.add(itemInSale); 
			}
			ImageCache mediaCache = ImageCache.getInstance();
			for(ItemInSale item : itemInSaleList) {
				Integer itemID = item.getItemInSaleID();
				query = mediaQuery.selectItemMedia(itemID);
				rs2 = stmt.executeQuery(query);
				while(rs2.next()) {
					Integer mediaIndex = rs2.getInt("imageIndex");
					String fileName = itemID.toString() + "_" + mediaIndex.toString();
					String filePath = mediaCache.addImage(fileName, rs2.getBinaryStream("image"));
					item.addMedia(filePath);
				}
				if(item.getMedia().isEmpty()){
					item.addMedia("/logic/view/assets/images/missing.png");
				}
				rs2.close();
			}	
			
		}catch(SQLException e) {
			//TODO gestire eccezione
			e.printStackTrace();
			System.out.println("Attenzione: Errore nella ItemInSaleDao.getItemsInSaleListFiltered()");
		}
		
		finally {
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
		return itemInSaleList;
			
	}
	
	
}
