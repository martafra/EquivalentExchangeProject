package logic.DAO;

import java.sql.Connection;
import java.sql.Date;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import logic.query.UserQuery;
import logic.database.MyConnection;
import logic.entity.User;
import logic.enumeration.Gender;

public class UserDAO {

	MyConnection connection = MyConnection.getInstance();
	UserQuery userQ = new UserQuery();

	public User selectMiniUser(String username, String password) {//restituisce l'utente con solo i dati obbligatori
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

			user = new User(rs.getString("username"), rs.getString("firstName"), rs.getString("lastName"),
					rs.getString("email"), rs.getString("passwd"));

		} catch (SQLException e) {
			// TODO Auto-generated catch block

			e.printStackTrace();
			System.out.println("Attenzione: Errore nella UserDao.selectMiniUser()");

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

	public User selectUser(String username, String password) {//restituisce l'utente con tutti i dati presenti, lasciando a null i restanti
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

			user = new User(rs.getString("username"), rs.getString("firstName"), rs.getString("lastName"),
					rs.getString("email"), rs.getString("passwd"));

			String datestr = rs.getString("birthDate");
			String genderstr = rs.getString("gender");

			if (datestr != null) {
				Date date = Date.valueOf(datestr);
				user.setBirthDate(date);
			}
			if (genderstr != null) {
				Gender gender = Gender.getGender(genderstr);
				user.setGender(gender);
			}

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
	
	public User selectUser(String username) {//restituisce l'utente con tutti i dati presenti, lasciando a null i restanti
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

			user = new User(rs.getString("username"), rs.getString("firstName"), rs.getString("lastName"),
					rs.getString("email"), rs.getString("passwd"));

			String datestr = rs.getString("birthDate");
			String genderstr = rs.getString("gender");

			if (datestr != null) {
				Date date = Date.valueOf(datestr);
				user.setBirthDate(date);
			}
			if (genderstr != null) {
				Gender gender = Gender.getGender(genderstr);
				user.setGender(gender);
			}

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

	// ritorna 1 se l'utente è stato inserito, 0 altrimenti
	public int insertUser(String username, String firstName, String lastName, String email, String password) {
		Statement stmt = null;
		int ret = 0;
		try {

			Connection con = connection.getConnection();
			stmt = con.createStatement();
			if (checkUser(stmt, username) == 0) {
				String query = userQ.insertUser(username, firstName, lastName, email, password);
				stmt.executeUpdate(query);
				ret = 1;
			}

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
		return ret;
	}

	// controlla se l'username è già in uso
	public int checkUser(Statement stmt, String username) throws SQLException {
		ResultSet rs;
		int ret;
		String query = userQ.checkUser(username);
		rs = stmt.executeQuery(query);
		if (rs.next()) {
			ret = 1;
		} else {
			ret = 0;
		}
		rs.close();
		return ret;
	}

	public void updateGender(String username, char gender) {
		Statement stmt = null;
		try {

			Connection con = connection.getConnection();
			stmt = con.createStatement();
			String query = userQ.updateGender(username, gender);
			stmt.executeUpdate(query);

		} catch (SQLException e) {
			// TODO Auto-generated catch block

			e.printStackTrace();
			System.out.println("Attenzione: Errore nella UserDao.updateGender()");

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

	public void updateBirthDate(String username, String birthDate) { //birtDate : yyyy-mm-dd
		Statement stmt = null;
		try {

			Connection con = connection.getConnection();
			stmt = con.createStatement();
			String query = userQ.updateBirthDate(username, birthDate);
			stmt.executeUpdate(query);

		} catch (SQLException e) {
			// TODO Auto-generated catch block

			e.printStackTrace();
			System.out.println("Attenzione: Errore nella UserDao.updateBirthDate()");

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

	public void updateCredit(String username, Integer newCredit) {
		Statement stmt = null;
		try {

			Connection con = connection.getConnection();
			stmt = con.createStatement();
			String query = userQ.updateCredit(username, newCredit);
			stmt.executeUpdate(query);

		} catch (SQLException e) {
			// TODO Auto-generated catch block

			e.printStackTrace();
			System.out.println("Attenzione: Errore nella UserDao.updateCredit()");

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
