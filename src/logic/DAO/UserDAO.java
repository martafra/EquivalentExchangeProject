package logic.DAO;

import java.sql.Connection;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import logic.query.UserQuery;
import logic.database.MyConnection;
import logic.entity.User;


public class UserDAO {

	MyConnection connection = MyConnection.getInstance();
	UserQuery userQ = new UserQuery();


	public User selectUser(String username, String password) {
		User user = null;
		Statement stmt = null;
		ResultSet rs = null;
		try {

			Connection con = connection.getConnection();
			stmt = con.createStatement();
			String query = userQ.selectUser(username, password);
			rs = stmt.executeQuery(query);

			if (!rs.next()) {
				return null;
			}

			user = new User(rs.getString("username"), rs.getString("firstName"), rs.getString("lastName"), rs.getString("gender"),
					rs.getDate("birthDate"), rs.getString("email"), rs.getString("passwd"));


		} catch (SQLException e) {
			// TODO Auto-generated catch block

			e.printStackTrace();
			System.out.println("Attenzione: Errore nella UserDao.selectUser()");

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
		return user;

	}
	
	public User selectUser(String username) {
		User user = null;
		Statement stmt = null;
		ResultSet rs = null;
		try {

			Connection con = connection.getConnection();
			stmt = con.createStatement();
			String query = userQ.selectUser(username);
			rs = stmt.executeQuery(query);

			if (!rs.next()) {
				return null;
			}

			user = new User(rs.getString("username"), rs.getString("firstName"), rs.getString("lastName"), rs.getString("gender"),
					rs.getDate("birthDate"), rs.getString("email"), rs.getString("passwd"));


		} catch (SQLException e) {
			// TODO Auto-generated catch block

			e.printStackTrace();
			System.out.println("Attenzione: Errore nella UserDao.selectUser()");

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
		return user;

	}

	public void insertUser(User user) {
		Statement stmt = null;
		try {

			Connection con = connection.getConnection();
			stmt = con.createStatement();
			String query = userQ.insertUser(user);
			stmt.executeUpdate(query);

		} catch (SQLException e) {
			// TODO Auto-generated catch block

			e.printStackTrace();
			System.out.println("Attenzione: Errore nella UserDao.insertUser()");

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
	
	public void updateUser(User user) {
		Statement stmt = null;
		try {

			Connection con = connection.getConnection();
			stmt = con.createStatement();
			String query = userQ.updateUser(user);
			stmt.executeUpdate(query);

		} catch (SQLException e) {
			// TODO Auto-generated catch block

			e.printStackTrace();
			System.out.println("Attenzione: Errore nella UserDao.updateUser()");

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
	
	public void deleteUser(String username) {
		Statement stmt = null;
		try {

			Connection con = connection.getConnection();
			stmt = con.createStatement();
			String query = userQ.deleteUser(username);
			stmt.executeUpdate(query);

		} catch (SQLException e) {
			// TODO Auto-generated catch block

			e.printStackTrace();
			System.out.println("Attenzione: Errore nella UserDao.deleteUser()");

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
