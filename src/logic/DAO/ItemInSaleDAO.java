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

public class ItemInSaleDAO {

	MyConnection connection = MyConnection.getInstance();
	ItemInSaleQuery itemInSaleQ = new ItemInSaleQuery();
	
	public ItemInSale selectItemInsale(int itemInSaleID) {
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
			
			itemInSale = new ItemInSale(rs.getInt("price"), rs.getBoolean("availability"), rs.getString("preferredLocation"),
					item, user);

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
	
	public void insertItemInSale(int price, boolean availability, String preferredLocation, int referredItemID, String sellerID) {
		Statement stmt = null;
		
		try {

			Connection con = connection.getConnection();
			stmt = con.createStatement();
			String query = itemInSaleQ.insertItemInSale(price, preferredLocation, referredItemID, sellerID);
			stmt.executeUpdate(query);
		

		} catch (SQLException e) {
			// TODO Auto-generated catch block

			e.printStackTrace();
			System.out.println("Attenzione: Errore nella ItemInSaleDao.insertItemInSale()");

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
	
	public void updateDescription(int itemInSaleID, String saleDescription) {
		Statement stmt = null;
		try {

			Connection con = connection.getConnection();
			stmt = con.createStatement();
			String query = itemInSaleQ.updateSaleDescription(itemInSaleID, saleDescription);
			stmt.executeUpdate(query);

		} catch (SQLException e) {
			// TODO Auto-generated catch block

			e.printStackTrace();
			System.out.println("Attenzione: Errore nella ItemInSaleDao.updateDescription()");

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
	
	public void updatePrice(int itemInSaleID,int newPrice) {
		Statement stmt = null;
		try {

			Connection con = connection.getConnection();
			stmt = con.createStatement();
			String query = itemInSaleQ.updatePrice(itemInSaleID, newPrice);
			stmt.executeUpdate(query);

		} catch (SQLException e) {
			// TODO Auto-generated catch block

			e.printStackTrace();
			System.out.println("Attenzione: Errore nella ItemInSaleDao.updatePrice()");

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
	
	public void updateAvailability(int itemInSaleID,boolean availability) {
		Statement stmt = null;
		try {

			Connection con = connection.getConnection();
			stmt = con.createStatement();
			String query = itemInSaleQ.updateAvailability(itemInSaleID, availability);
			stmt.executeUpdate(query);

		} catch (SQLException e) {
			// TODO Auto-generated catch block

			e.printStackTrace();
			System.out.println("Attenzione: Errore nella ItemInSaleDao.updateAvailability()");

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
	
	public void updateItemCondition(int itemInSaleID,char itemCondition) {
		Statement stmt = null;
		try {

			Connection con = connection.getConnection();
			stmt = con.createStatement();
			String query = itemInSaleQ.updateItemCondition(itemInSaleID, itemCondition);
			stmt.executeUpdate(query);

		} catch (SQLException e) {
			// TODO Auto-generated catch block

			e.printStackTrace();
			System.out.println("Attenzione: Errore nella ItemInSaleDao.updateItemCondition()");

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
	
	public void updatePreferredLocation(int itemInSaleID,String peferredLocation) {
		Statement stmt = null;
		try {

			Connection con = connection.getConnection();
			stmt = con.createStatement();
			String query = itemInSaleQ.updatePreferredLocation(itemInSaleID, peferredLocation);
			stmt.executeUpdate(query);

		} catch (SQLException e) {
			// TODO Auto-generated catch block

			e.printStackTrace();
			System.out.println("Attenzione: Errore nella ItemInSaleDao.updatePreferredLocation()");

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
	
	public void updateReferredOrderID(int itemInSaleID,int referredOrderID) {
		Statement stmt = null;
		try {

			Connection con = connection.getConnection();
			stmt = con.createStatement();
			String query = itemInSaleQ.updateReferredOrderID(itemInSaleID, referredOrderID);
			stmt.executeUpdate(query);

		} catch (SQLException e) {
			// TODO Auto-generated catch block

			e.printStackTrace();
			System.out.println("Attenzione: Errore nella ItemInSaleDao.updateReferredOrderID()");

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
	
	public void removeItemInSale(int itemInSaleID) {
		Statement stmt = null;
		try {

			Connection con = connection.getConnection();
			stmt = con.createStatement();
			String query = itemInSaleQ.removeItemInSale(itemInSaleID);
			stmt.executeUpdate(query);

		} catch (SQLException e) {
			// TODO Auto-generated catch block

			e.printStackTrace();
			System.out.println("Attenzione: Errore nella ItemInSaleDao.removeItemInSale()");

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
