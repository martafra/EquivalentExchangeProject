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
import org.apache.tomcat.util.http.fileupload.FileUtils;

public class ImageCache {
	
	private static ImageCache instance = null;
	private static final String MISSING_IMAGE_PATH = "/logic/view/assets/images/missing.png";
	private ArrayList<String> fileNames = new ArrayList<>();
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
		
		if(fileNames.contains(name)){
			String filePath = directoryPath.toString() + "/" + name;
			filePath = "file:///" + filePath;
			return filePath.replace('\\', '/');
		}


		if(data == null) {
			return MISSING_IMAGE_PATH;
		}
		
		String filePath = directoryPath.toString() + "/" + name;
		File image = new File(filePath);
		
		filePath = "file:///" + filePath;
		filePath = filePath.replace('\\', '/');
		
	
		try {
				image.createNewFile();
				OutputStream output = new FileOutputStream(image);
				IOUtils.copy(data, output);	
				output.close();
				data.close();
				fileNames.add(name);
				
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			filePath = MISSING_IMAGE_PATH;
		} catch (IOException e) {
			// TODO Auto-generated catch block
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
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public String getMissingImagePath() {
		return MISSING_IMAGE_PATH;
	}
}
