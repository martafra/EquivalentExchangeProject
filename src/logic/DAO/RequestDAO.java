package logic.DAO;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import logic.entity.Request;
import logic.query.RequestQuery;
import logic.support.database.MyConnection;

public class RequestDAO {
	
	MyConnection connection = MyConnection.getInstance();
	RequestQuery requestQuery = new RequestQuery();
	
	public void insertRequest(Request request) {
		Statement stmt = null;
		
		try {
			
			Connection con = connection.getConnection();
			stmt = con.createStatement();
			Integer status;
			if (request.getStatus())
				status = 1;
			else
				status = 0;
			String query = requestQuery.insertRequest(request.getBuyer().getUsername(), request.getReferredItem().getItemInSaleID(), status, request.getNote());
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
	
	public Request selectRequest(String buyer, Integer item) {
		Request request = null;
		Statement stmt = null;
		ResultSet rs;
		
		try {
			
			Connection con = connection.getConnection();
			stmt = con.createStatement();
			String query = requestQuery.selectRequest(buyer, item);
			rs = stmt.executeQuery(query);
			if (!rs.next()) {
				return null;
			}
			UserDAO userDAO = new UserDAO();
			ItemInSaleDAO itemDAO = new ItemInSaleDAO();
			Boolean status;
			if (rs.getInt("requestStatus") == 1)
				status = true;
			else
				status = false;
			request = new Request(userDAO.selectUser(buyer), itemDAO.selectItemInSale(item), status, rs.getString("note"));
			
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
		return request;
	}
	
	public ArrayList<Request> selectAllRequests(String buyer) {
		ArrayList<Request> requests = new ArrayList<>();
		Statement stmt = null;
		ResultSet rs;
		
		try {
			
			Connection con = connection.getConnection();
			stmt = con.createStatement();
			String query = requestQuery.selectAllRequests(buyer);
			rs = stmt.executeQuery(query);
			UserDAO userDAO = new UserDAO();
			ItemInSaleDAO itemDAO = new ItemInSaleDAO();
			while(rs.next()) {
				Boolean status;
				if (rs.getInt("requestStatus") == 1)
					status = true;
				else
					status = false;
				requests.add(new Request(userDAO.selectUser(buyer), itemDAO.selectItemInSale(rs.getInt("referredItemID")), status, rs.getString("note")));
			}		
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
		return requests;
	}
	
	public void deleteRequest(String buyer, Integer item) {
		Statement stmt = null;
		
		try {
			
			Connection con = connection.getConnection();
			stmt = con.createStatement();
			String query = requestQuery.deleteRequest(buyer, item);
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
