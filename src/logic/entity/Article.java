package logic.entity;

import java.util.ArrayList;
import java.util.List;

import logic.entity.Item;
import logic.enumeration.ArticleType;
import logic.enumeration.LayoutType;

public class Article {
	
	private Integer articleID;
	private String title;
	private String text[] = new String[4];
	private ArrayList<String> tags;
	private Boolean onValidation = false;
	private ArrayList<String> mediaPaths;
	private Item referredItem;
	private User author;
	
	private ArticleType type;
	private LayoutType layout;
	
	public Integer getArticleID() {
		return title.hashCode() + author.hashCode() + text.hashCode();
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
	
	public LayoutType getLayout() {
		return layout;
	}
	
	public void setLayout(LayoutType layout) {
		this.layout = layout;
	}
	
	public String getMedia(Integer index) {
		return mediaPaths.get(index);
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
	
	
	
}
