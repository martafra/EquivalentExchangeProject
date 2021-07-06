package logic.query;

public class TagQuery extends Query{

	public String insertTagForArticle(String tag, Integer articleID) {
		tag = quote(tag);
		String query = "INSERT INTO tag (articleID, tagValue) VALUES (%d, %s);";
		return String.format(query, articleID, tag);
	}
	
	public String retrieveTags(Integer articleID) {
		String query = "SELECT * FROM tag WHERE articleID = %d;";
		return String.format(query, articleID);
	}
	
}
