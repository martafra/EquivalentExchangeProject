package logic.support.other;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import org.apache.tomcat.jakartaee.commons.io.IOUtils;

public class ImageCache {
	
	private static ImageCache instance = null;
	private ArrayList<String> paths = new ArrayList<>();
	private Path directoryPath;
	
	private ImageCache() {
		try {
			Path path = Files.createTempDirectory("EquivalentExchangeTempData");
			directoryPath = path.toAbsolutePath();
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public static ImageCache getInstance() {
		if(instance == null)
			instance = new ImageCache();
		return instance;
	}
	
	public String addImage(String name, InputStream data) {
		
		String filePath = directoryPath.toString() + "/" + name;
		File image = new File(filePath);
		
		
		try {
			if(image.createNewFile()) {
				OutputStream output = new FileOutputStream(image);
				IOUtils.copy(data, output);	
			}else {
				filePath = "";
			}
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return filePath;
		
	}
	
	public void remove() {
		try {
			Files.deleteIfExists(directoryPath);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
}
