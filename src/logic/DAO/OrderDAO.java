package logic.DAO;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import logic.database.MyConnection;
import logic.entity.ItemInSale;
import logic.entity.Order;
import logic.entity.User;
import logic.query.OrderQuery;

public class OrderDAO {
	MyConnection connection = MyConnection.getInstance();
	OrderQuery orderQ = new OrderQuery();
	
	public Order selectOrder(int orderID) {
		Order order = null;
		Statement stmt = null;
		ResultSet rs = null;
		try {

			Connection con = connection.getConnection();
			stmt = con.createStatement();
			String query = orderQ.selectOrder(orderID);
			rs = stmt.executeQuery(query);

			if (!rs.next()) {
				return null;
			}
			
			ItemInSaleDAO itemInSaleDAO= new ItemInSaleDAO();
			ItemInSale itemInSale = itemInSaleDAO.selectItemInSale(rs.getInt("referredItemID"));
			
			UserDAO userDAO= new UserDAO();
			User user = userDAO.selectUser(rs.getString("buyerID"));
			
			order = new Order(rs.getInt("orderID"), rs.getString("code"), itemInSale, rs.getDate("orderDate"),
					rs.getBoolean("status"), user);


		} catch (SQLException e) {
			// TODO Auto-generated catch block

			e.printStackTrace();
			System.out.println("Attenzione: Errore nella OrderDao.selectOrder()");

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
		return order;

	}

	public void insertOrder(Order order) {
		Statement stmt = null;
		try {

			Connection con = connection.getConnection();
			stmt = con.createStatement();
			String query = orderQ.insertOrder(order);
			stmt.executeUpdate(query);

		} catch (SQLException e) {
			// TODO Auto-generated catch block

			e.printStackTrace();
			System.out.println("Attenzione: Errore nella OrderDao.insertOrder()");

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
	
	public void updateOder(Order order) {
		Statement stmt = null;
		try {

			Connection con = connection.getConnection();
			stmt = con.createStatement();
			String query = orderQ.updateOrder(order);
			stmt.executeUpdate(query);

		} catch (SQLException e) {
			// TODO Auto-generated catch block

			e.printStackTrace();
			System.out.println("Attenzione: Errore nella OrderDao.updateOrder()");

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
	
	public void deleteOrder(int itemInSaleID) {
		Statement stmt = null;
		try {

			Connection con = connection.getConnection();
			stmt = con.createStatement();
			String query = orderQ.deleteOrder(itemInSaleID);
			stmt.executeUpdate(query);

		} catch (SQLException e) {
			// TODO Auto-generated catch block

			e.printStackTrace();
			System.out.println("Attenzione: Errore nella OrderDao.deleteOrder()");

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
