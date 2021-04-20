package logic.database;


import java.sql.*;
//Ricordarsi di fare 'import' manuale della libreria "mysql-connector-java-8.0.23" 
//P.S. si trova nella cartella 'C:\Program Files (x86)\MySQL\Connector J 8.0'

public class MyDB {
	Connection con;
	String nomeDB = "db";
	String portaDB = "3307";
	String usernameDB = "root";
	String passwordDB = "passsql";
	String driver = "com.mysql.cj.jdbc.Driver";

	// Metodo per connettersi al database mySQL
	public int connect() {
		int ret = 0;

		try {
			Class.forName(driver);
			con = DriverManager.getConnection("jdbc:mysql://localhost:" + portaDB + "/" + nomeDB, usernameDB,
					passwordDB);

		} catch (Exception e) {
			System.out.println("Attenzione: Errore nel metodo 'connect' della classe MyDB ");
			e.printStackTrace();
			System.out.println(e);
			ret = -1;
		}

		return ret;
	}

	// Metodo per chiudere la connessione con il database mySQL
	public int disconnect() {
		int ret = 0;

		try {
			con.close();

		} catch (SQLException e) {
			System.out.println("Attenzione: Errore nel metodo 'disconnect' della classe MyDB ");
			e.printStackTrace();
			System.out.println(e);
			ret = -1;
		}

		return ret;
	}

	// Metodo per eseguire una query sul Database al quale si è connessi
	public ResultSet makeQuery(String query) {
		Statement stmt;
		ResultSet rs = null;

		try {
			stmt = con.createStatement(); 
			rs = stmt.executeQuery(query);

		} catch (SQLException e) {
			System.out.println("Attenzione: Non e' stato possibile eseguire la seguente query:\n'" + query + "'");
			e.printStackTrace();
			System.out.println(e);
		}
		return rs;
	}

	// Metodo per eseguire un update sul Database al quale si è connessi
	public int doUpdate(String query) {
		Statement stmt;
		int ret = 0;

		try {
			stmt = con.createStatement();
			ret = stmt.executeUpdate(query);

		} catch (SQLException e) {
			System.out.println("Attenzione: Non e' stato possibile eseguire il seguente update:\n'" + query + "'");
			e.printStackTrace();
			System.out.println(e);
			ret = -1;
		}
		return ret;
	}

	// Metodo che restituisce il numero di colonne nel ResultSet
	public int numeroColonne(ResultSet rs) throws SQLException {

		ResultSetMetaData rsmd = rs.getMetaData();
		return rsmd.getColumnCount();

	}

	
	//METODI DI DEBUG
	
	// Metodo che mostra i nomi delle colonne nel ResultSet
	public void nomeColonne(ResultSet rs) throws SQLException {
		ResultSetMetaData rsmd = rs.getMetaData();

		for (int i = 1; i <= rsmd.getColumnCount(); i++) {

			System.out.print(rsmd.getColumnName(i) + "\t");
		}

		System.out.print("\n");
	}

	// Metodo per mostrare tutti i valori del ResultSet
	public void showAll(ResultSet rs) {
		int column_count;

		try {
			column_count = numeroColonne(rs);
			System.out.println("Ecco i risultati:\n");
			// nomeColonne(rs);
			while (rs.next()) {
				for (int i = 1; i <= column_count; i++) {

					System.out.print(rs.getString(i) + "\t\t");
				}
				System.out.println("");
			}
			System.out.println("\nQuery conclusa");

		} catch (SQLException e) {
			e.printStackTrace();
			System.out.println(e);
		}
	}

}
