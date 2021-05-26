package logic.query;

public class MediaQuery {

    public String insertItemMedia(String mediaPath, Integer mediaID, Integer itemID){
        
        String query = "INSERT INTO media (itemInSaleID, imageIndex, image) VALUES (%d, %d, LOAD_FILE(\"%s\"));";
        return String.format(query, itemID, mediaID, mediaPath);

    }

}
