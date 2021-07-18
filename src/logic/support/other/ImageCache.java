package logic.support.other;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import org.apache.tomcat.jakartaee.commons.io.IOUtils;
import org.apache.tomcat.util.http.fileupload.FileUtils;

public class ImageCache {
	
	private static ImageCache instance = null;
	private static final String MISSING_IMAGE_PATH = 
			File.separator + "logic" + File.separator + "view" + File.separator + "assets" +
					File.separator + "images" + File.separator + "missing.png";
	private Path directoryPath;
	
	private ImageCache() {
		try {
			Path path = Files.createTempDirectory("EquivalentExchangeTempData");
			directoryPath = path.toAbsolutePath();
			
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public Path getDirectoryPath() {
		return directoryPath;
	}
	
	public static ImageCache getInstance() {
		if(instance == null)
			instance = new ImageCache();
		return instance;
	}
	
	public String addImage(String name, InputStream data) {
		


		if(data == null) {
			return MISSING_IMAGE_PATH;
		}
		
		String filePath = directoryPath.toString() + File.separator + name;
		File image = new File(filePath);
		
		filePath = "file:///" + filePath;
		filePath = filePath.replace('\\', '/');
		
	
		try {
				if(image.createNewFile()) {
					//file created
				}
				OutputStream output = new FileOutputStream(image);
				IOUtils.copy(data, output);	
				output.close();
				data.close();
				
		} catch (IOException e) {
			e.printStackTrace();
			filePath = MISSING_IMAGE_PATH;
		}
		
		return filePath;
		
	}
	
	public void remove() {
		try {
			File mainDirectory = directoryPath.toFile();
			FileUtils.cleanDirectory(mainDirectory);
			FileUtils.deleteDirectory(mainDirectory);		
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public String getMissingImagePath() {
		return MISSING_IMAGE_PATH;
	}
}
