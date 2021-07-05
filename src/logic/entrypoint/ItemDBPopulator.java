package logic.entrypoint;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import logic.DAO.ItemDAO;
import logic.entity.Book;
import logic.entity.Item;

public class ItemDBPopulator {

	public static void main(String[] args) throws Exception {
		
		ItemDAO itemDAO = new ItemDAO();
		BufferedReader reader = new BufferedReader(new FileReader("E:\\Desktop\\books.txt"));
		
		String header = reader.readLine();
		header = header.replaceAll(";;;;;;", "");
		System.out.println(header);
		String line = "";
		while(line != null) {
			line = reader.readLine().replaceAll(";;;;;;", "");
			Book item = (Book) parseBook(parseLine(header, line));
			itemDAO.insertItem(item);
			
		}
		

	}
	
	public static Book parseBook(Map<String, String> map) throws ParseException {
		
		
		HashMap<String, String> bookMap = (HashMap<String, String>) map;
		SimpleDateFormat format = new SimpleDateFormat("mm/dd/yyyy");
		
		String title = bookMap.get("title");
		String authors = bookMap.get("authors");
		Integer edition = 1;
		Integer numOfPages = Integer.valueOf(bookMap.get("num_pages"));
		String genre = "GENERIC";
		String publisher = bookMap.get("publisher");
		Date date = format.parse(bookMap.get("publication_date"));
		String language = "MULTILINGUAL";
		
		return new Book(title, date, authors, edition, numOfPages, genre, publisher, language);
	}
	
	public static Map<String, String> parseLine(String keys, String itemString) {
		String[] itemData = itemString.split(",");
		String[] itemHeader = keys.split(",");
		HashMap<String, String> data = new HashMap<>();
		for(int i = 0; i < itemData.length; i++) {
			data.put(itemHeader[i], itemData[i]);
		}
		
		return data;
	}
	
}
