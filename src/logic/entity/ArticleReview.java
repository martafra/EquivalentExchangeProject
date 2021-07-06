package logic.entity;

public class ArticleReview {

	private Integer value;
	private User author;
	
	
	public ArticleReview(Integer val, User user) {
		value = val;
		author = user;
	}
	
	
	public Integer getValue() {
		return value;
	}
	
	public User getAuthor() {
		return author;
	}
}
