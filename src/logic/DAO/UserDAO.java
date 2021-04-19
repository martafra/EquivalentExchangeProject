package logic.DAO;
import logic.entity.User;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;

import java.sql.Statement;

public class UserDAO {

	
	public User retrieveUser(String username){
		
		User user = new User();
		//query the db for a suser that has "username" as username
		Connection conn = null;
		try {
			conn = DriverManager.getConnection("jdbc:mysql://127.0.0.1:3306/EquivalentExchange", "root", "root");
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		Statement stm = null;
		try {
			stm = conn.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		ResultSet risp = null;
		try {
			risp = stm.executeQuery("Select username, firstName, lastName, email, passwd "
												+ "from User "
												+ "where username=\""+username+"\";");
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		try {
			
			if(risp.first()) {
				
				user.setName(risp.getString("firstName"));
				user.setSurname(risp.getString("lastName"));
				user.setUsername(risp.getString("username"));
				user.setEmail(risp.getString("email"));
				user.setPassword(risp.getString("passwd"));
			}else {
				return null;
			}
			
			
		} catch(SQLException e) {
			System.out.println("Errore");
		}
		
		
		return user;
	}
	
}
