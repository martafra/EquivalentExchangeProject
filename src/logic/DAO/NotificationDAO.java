package logic.DAO;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import logic.query.NotificationQuery;
import logic.support.database.MyConnection;
import logic.support.other.Notification;

public class NotificationDAO {

	private MyConnection connection = MyConnection.getInstance();
	private NotificationQuery nQuery = new NotificationQuery();
	private DateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	public void insertNotification(String receiver, Notification notification) {
		
		Statement stmt = null;
		
		
		try {
			Connection con = connection.getConnection();
			stmt = con.createStatement();
			String type = notification.getType().toString().substring(0,1);
			String sender = notification.getSender();
			Date notifDate = notification.getDate();
			
			String query = nQuery.insertNotification(sender, receiver, type, sender, notifDate);
			
			stmt.executeUpdate(query);
			
		}catch (SQLException e) {
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
