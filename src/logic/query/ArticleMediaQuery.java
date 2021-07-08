package logic.query;

public class ArticleMediaQuery extends Query{
	
	public String insertMedia(Integer articleID, String imagePath, Integer imageIndex) {
		
		imagePath = quote(imagePath);
		imagePath = imagePath.replace('\\', '/');
		String query = "INSERT INTO media_a (articleID, image, imageIndex) "
					 + "VALUES(%d, LOAD_FILE(%s), %d);";
		
		return String.format(query, articleID, imagePath, imageIndex);
	}
	
	public String retrieveAllMedia(Integer articleID) {
		String query = "SELECT * FROM media_a WHERE articleID = %d;";
		return String.format(query, articleID);
	}
	
	
}
