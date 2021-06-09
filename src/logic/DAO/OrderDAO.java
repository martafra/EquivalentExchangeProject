package logic.DAO;

import java.sql.Connection;


import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import logic.support.database.MyConnection;
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
			DateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			Date orderDate = null;
			Date startDate = null;
			try {
				startDate = format.parse(rs.getString("startDate"));
				orderDate = format.parse(rs.getString("orderDate"));
			} catch (NullPointerException | ParseException e) {
				// TODO Auto-generated catch block
				
				e.printStackTrace();
			}
			Boolean sellerStatus = false;
			if(rs.getInt("sellerStatus") == 1)
				sellerStatus = true;
			Boolean buyerStatus = false;
			if(rs.getInt("buyerStatus") == 1)
				buyerStatus = true;
			
			UserDAO userDAO= new UserDAO();
			User user = userDAO.selectUser(rs.getString("buyerID"));
			
			order = new Order(rs.getInt("orderID"), rs.getString("code"), itemInSale, orderDate, startDate,
				 user, buyerStatus, sellerStatus);


		} catch (SQLException e) {
			// TODO Auto-generated catch block

			e.printStackTrace();

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
	
	public ArrayList<Order> selectAllOrders(String username) {
		ArrayList<Order> orders = new ArrayList<>();
		Statement stmt = null;
		ResultSet rs = null;
		try {

			Connection con = connection.getConnection();
			stmt = con.createStatement();
			String query = orderQ.selectOrdersByUser(username);
			rs = stmt.executeQuery(query);
			
			while(rs.next()) {
				//int orderID, String code, ItemInSale involvedItem, Date orderDate, Date startDate, Boolean orderStatus, User buyer
				DateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
				Date orderDate = null;
				Date startDate = null;
				try {
					orderDate = format.parse(rs.getString("orderDate"));
					startDate = format.parse(rs.getString("startDate"));
				} catch (ParseException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				
				UserDAO userDAO = new UserDAO();
				ItemInSaleDAO itemDAO = new ItemInSaleDAO();
				Boolean sellerStatus = false;
				if(rs.getInt("sellerStatus") == 1)
					sellerStatus = true;
				Boolean buyerStatus = false;
				if(rs.getInt("buyerStatus") == 1)
					buyerStatus = true;
				
				orders.add(new Order(rs.getInt("orderID"), rs.getString("code"), itemDAO.selectItemInSale(rs.getInt("referredItem")), orderDate,
						startDate, userDAO.selectUser(username), buyerStatus, sellerStatus));
			}

		} catch (SQLException e) {
			// TODO Auto-generated catch block

			e.printStackTrace();

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
		return orders;

	}

	public void insertOrder(Order order) {
		Statement stmt = null;
		try {

			Connection con = connection.getConnection();
			stmt = con.createStatement();
			
			Integer orderID = order.getOrderID();
			Date orderDate = order.getOrderDate();
			String seller = order.getInvolvedItem().getSeller().getUsername();
			String buyer = order.getBuyer().getUsername();
			Integer buyerStatus = 0;
			if(Boolean.TRUE.equals(order.isAcceptedByBuyer()))
				buyerStatus = 1;
			
			Integer sellerStatus = 0;
			if(Boolean.TRUE.equals(order.isAcceptedBySeller()))
				sellerStatus = 1;
			String code = order.getCode();
			Date startDate = order.getStartDate();
			Integer itemID = order.getInvolvedItem().getItemInSaleID();	
			String query = orderQ.insertOrder(orderID, orderDate, code, startDate, seller, buyer, itemID, buyerStatus, sellerStatus);
			stmt.executeUpdate(query);

		} catch (SQLException e) {
			// TODO Auto-generated catch block

			e.printStackTrace();

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
			
			Integer orderID = order.getOrderID();
			Date orderDate = order.getOrderDate();
			Date startDate = order.getStartDate();
			
			Integer buyerStatus = 0;
			if(Boolean.TRUE.equals(order.isAcceptedByBuyer()))
				buyerStatus = 1;
			
			Integer sellerStatus = 0;
			if(Boolean.TRUE.equals(order.isAcceptedBySeller()))
				sellerStatus = 1;
			
			
			String code = order.getCode();
			
			String query = orderQ.updateOrder(orderID, orderDate, startDate, code, buyerStatus, sellerStatus);
			stmt.executeUpdate(query);

		} catch (SQLException e) {
			// TODO Auto-generated catch block

			e.printStackTrace();

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
	
	public void deleteOrder(int orderID) {
		Statement stmt = null;
		try {

			Connection con = connection.getConnection();
			stmt = con.createStatement();
			String query = orderQ.deleteOrder(orderID);
			stmt.executeUpdate(query);

		} catch (SQLException e) {
			// TODO Auto-generated catch block

			e.printStackTrace();

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
