package logic.query;

public class MediaQuery extends Query{

    public String insertItemMedia(String mediaPath, Integer mediaID, Integer itemID){
        
        mediaPath = quote(mediaPath);
        mediaPath = mediaPath.replace('\\', '/');
        String query = "INSERT INTO media (itemInSaleID, imageIndex, image) VALUES (%d, %d, LOAD_FILE(%s));";
        return String.format(query, itemID, mediaID, mediaPath);

    }
    
    public String selectItemMedia(Integer itemID) {
    	
    	String query = "SELECT imageIndex, image FROM media WHERE itemInSaleID = %d;";
    	return String.format(query, itemID);
    	
    }

}
