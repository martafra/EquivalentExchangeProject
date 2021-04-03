package logic.entity;

import java.util.ArrayList;

import logic.entity.Item;
import logic.enumeration.ArticleType;
import logic.enumeration.LayoutType;

public class Article {
	private String title;
	private String text;
	private ArrayList<String> tags;
	private Boolean onValidation;
	private ArrayList<String> mediaPaths;
	private Item referredItem;
	private User author;
	
	private ArticleType type;
	private LayoutType layout;
	
	
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getText() {
		return text;
	}
	public void setText(String text) {
		this.text = text;
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
	
	
	
}
