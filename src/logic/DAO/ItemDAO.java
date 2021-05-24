package logic.DAO;

import java.sql.Connection;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import logic.query.ItemQuery;
import logic.support.other.ItemFactory;
import logic.support.database.MyConnection;
import logic.entity.Item;


public class ItemDAO {
	
	MyConnection connection = MyConnection.getInstance();
	ItemQuery itemQ = new ItemQuery();
	
	public List<Item> getItemsList(){
		ArrayList<Item> itemList = new ArrayList<>();
		ItemFactory factory = new ItemFactory();
		Statement stmt = null;
		ResultSet rs = null;
		try {
			Connection con = connection.getConnection();
			stmt = con.createStatement();
			String query = itemQ.getAllItems();
			rs = stmt.executeQuery(query);
			
			while(rs.next()) {
				ArrayList<String> data = storeRs(rs, rs.getMetaData().getColumnCount());
				itemList.add(factory.makeItem(data.get(6).charAt(0), data)); 
			}
		}catch(SQLException e) {
			//TODO gestire eccezione
		}
		finally {
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
		return itemList;
		
	}
	
	
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
	
	private ArrayList<String> storeRs(ResultSet rs, Integer columnCount) {//Inserisce tutti i dati del resultSet in un ArrayList
		ArrayList<String> ret = new ArrayList<String>();

		try {

				for (int i = 1; i <= columnCount; i++) {
					ret.add(rs.getString(i));
				}


		} catch (SQLException e) {
			e.printStackTrace();
			System.out.println("Attenzione: Errore nella 'storeRs()'");
		}
		return ret;
	}
	
	
	public void insertItem(Item item) {//inserisce l'item senza dover specificare l'ID -> nel database verrà messo come AutoIncrement
		Statement stmt = null;
		try {

			Connection con = connection.getConnection();
			stmt = con.createStatement();
				String query = itemQ.insertItem(item);
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
