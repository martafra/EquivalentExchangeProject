package logic.dao;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import logic.entity.ItemInSale;
import logic.entity.Order;
import logic.entity.User;

import logic.query.WishlistQuery;
import logic.support.database.MyConnection;

public class WishlistDAO {
	WishlistQuery wishlistQ = new WishlistQuery();
	MyConnection connection = MyConnection.getInstance();
	
	public boolean checkItemInWishlist(User user, ItemInSale itemInSale) {
		Statement stmt = null;
		ResultSet rs = null;
		try {

			Connection con = connection.getConnection();
			stmt = con.createStatement();
			String userID = user.getUsername();
			Integer itemID = itemInSale.getItemInSaleID();
			String query = wishlistQ.checkItemInWishlist(userID, itemID);
			rs = stmt.executeQuery(query);

			if (!rs.next()) {
				return false;
			}
			rs.close();

		} catch (SQLException e) {

			e.printStackTrace();

		} finally {
			try {
				if(stmt!=null)
					stmt.close();
			}catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return true;

	}

	public void insertToWishlist(User user, ItemInSale itemInSale) {
		Statement stmt = null;
		try {

			Connection con = connection.getConnection();
			stmt = con.createStatement();
			String userID = user.getUsername();
			Integer itemID = itemInSale.getItemInSaleID();
			String query = wishlistQ.insertToWishlist(userID, itemID);
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
	
	public void deleteFromWishlist(User user, ItemInSale itemInSale) {
		Statement stmt = null;
		try {

			Connection con = connection.getConnection();
			stmt = con.createStatement();
			String userID = user.getUsername();
			Integer itemID = itemInSale.getItemInSaleID();
			String query = wishlistQ.deleteFromWishlist(userID, itemID);
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

}
