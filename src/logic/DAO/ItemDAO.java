package logic.DAO;

import java.sql.Connection;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import logic.query.ItemQuery;
import logic.database.ItemFactory;
import logic.database.MyConnection;
import logic.entity.Item;


public class ItemDAO {
	
	MyConnection connection = MyConnection.getInstance();
	ItemQuery itemQ = new ItemQuery();
	
	public Item selectItem(int itemID) {
		Item item = null;
		Statement stmt = null;
		ResultSet rs = null;
		try {

			Connection con = connection.getConnection();
			stmt = con.createStatement();
			String query = itemQ.selectItem(itemID);
			rs = stmt.executeQuery(query);

			if (!rs.next()) {
				return null;
			}
			
			ItemFactory myFactory = new ItemFactory();
			ArrayList<String> data = storeRs(rs, rs.getMetaData().getColumnCount());
			
			item = myFactory.makeItem(data.get(6).charAt(0), data); //chiamo il metodo makeItem della Factory passandogli itemType ed il resultSet della query inserito in un ArrayList
	
	
		} catch (SQLException e) {
			// TODO Auto-generated catch block

			e.printStackTrace();
			System.out.println("Attenzione: Errore nella ItemDao.selectItem()");

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
		return item;

	} 
	
	private ArrayList<String> storeRs(ResultSet rs, Integer column_count) {//Inserisce tutti i dati del resultSet in un ArrayList
		ArrayList<String> ret = new ArrayList<String>();

		try {

				for (int i = 1; i <= column_count; i++) {
					ret.add(rs.getString(i));
				}


		} catch (SQLException e) {
			e.printStackTrace();
			System.out.println("Attenzione: Errore nella 'storeRs()'");
		}
		return ret;
	}
	
	
	public void insertItem(String itemName, char itemType) {//inserisce l'item senza dover specificare l'ID -> nel database verrà messo come AutoIncrement
		Statement stmt = null;
		try {

			Connection con = connection.getConnection();
			stmt = con.createStatement();
				String query = itemQ.insertItem(itemName, itemType);
				stmt.executeUpdate(query);
				
		} catch (SQLException e) {
			// TODO Auto-generated catch block

			e.printStackTrace();
			System.out.println("Attenzione: Errore nella ItemDao.insertItem()");

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
	
	public void insertItem(Integer itemID,String itemName, char itemType) {
		Statement stmt = null;
		try {

			Connection con = connection.getConnection();
			stmt = con.createStatement();
				String query = itemQ.insertItem(itemID, itemName, itemType);
				stmt.executeUpdate(query);
				
		} catch (SQLException e) {
			// TODO Auto-generated catch block

			e.printStackTrace();
			System.out.println("Attenzione: Errore nella ItemDao.insertItem()");

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
