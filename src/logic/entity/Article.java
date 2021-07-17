package logic.entity;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import logic.enumeration.ArticleType;
import logic.enumeration.LayoutType;

public class Article {
	
	private Integer articleID;
	private String title;
	private String[] text = new String[4];
	private ArrayList<String> tags = new ArrayList<>();
	private Boolean onValidation = false;
	private ArrayList<String> mediaPaths = new ArrayList<>();
	private Item referredItem;
	private User author;
	private Integer reviewPoints;
	
	private ArticleType type;
	private LayoutType layout;
	private Date publishingDate = null;
	
	public Article(Integer id, String title, Boolean validated, Item item, User author) {
		articleID = id;
		this.title = title;
		onValidation = validated;
		this.referredItem = item;
		this.author = author;
	}
	
	
	public Article() {
		
	}


	
	public Integer getArticleID() {
		if(articleID != null)
		{
			return articleID;
		}
		
		return title.hashCode() + author.hashCode() + Arrays.hashCode(text);
	}
	
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getText(Integer i) {
		return text[i];
	}
	public void setText(String text, Integer i) {
		this.text[i] = text;
	}
	public ArticleType getType() {
		return type;
	}
	public void setType(ArticleType type) {
		this.type = type;
	}
	
	public void setType(String type) {
		
		this.type = null;
		
		if(type != null) {
			for (ArticleType value : ArticleType.values()) {
				if (type.equals(value.toString().substring(0, 1))){
					  this.type = value;
					  return;
				}
			}
		}
	}
	
	public LayoutType getLayout() {
		return layout;
	}
	
	public void setLayout(LayoutType layout) {
		this.layout = layout;
	}
	
	public void setLayout(String layout) {
		this.layout = null;
		if(layout != null) {
			for (LayoutType value : LayoutType.values()) {
				if (layout.equals(value.toString().substring(0, 1))){
					  this.layout = value;
					  return;
				}
			}
		}
	}
	
	public String getMedia(Integer index) {
		return mediaPaths.get(index);
	}
	
	public List<String> getAllMedia(){
		return mediaPaths;
	}
	
	public void setReferredItem(Item item) {
		this.referredItem = item;
	}
	
	public Item getReferredItem() {
		return this.referredItem;
	}
	public void setAuthor(User author) {
		this.author = author;
	}
	public User getAuthor() {
		return this.author;
	}
	
	public void addMedia(String mediaPath) {
		mediaPaths.add(mediaPath);
	}
	
	public void addTag(String tag) {
		tags.add(tag);
	}
	
	public void removeTag(String tag) {
		tags.remove(tag);
	}
	
	public List<String> getTags(){
		return tags;
	}
	
	public void validate() {
		onValidation = true;
	}
	
	public Boolean isValidated() {
		return onValidation;
	}
	
	public void setValidation(Boolean validationStatus) {
		onValidation = validationStatus;
	}


	public void setPublishingDate(Date date) {
		this.publishingDate  = date;
		
	}
	
	public Date getPublishingDate() {
		return this.publishingDate;
	}
	
	public void setreviewPoints(Integer points) {
		this.reviewPoints = points;
	}
	
	public Integer getReviewPoints() {
		return this.reviewPoints;
	}
	
	public void addReviewPoints(Integer points) {
		this.reviewPoints += points;
	}


	public void setArticleID(Integer id) {
		this.articleID = id;
		
	}
	
	
}
