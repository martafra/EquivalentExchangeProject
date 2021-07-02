package logic.bean;

import java.util.ArrayList;
import java.util.List;

import logic.support.interfaces.Bean;

public class ArticleBean implements Bean{

	private String layout;
	private String[] text = new String[4];
	private String title;
	private ArrayList<String> tags = new ArrayList<>();
	private ArrayList<String> media = new ArrayList<>();
	private String category;
	private String type;
	private ItemBean referredItem = new ItemBean();
	private UserBean author;
	
	
	
	public void addMedia(String selectedImagePath) {
		this.media.add(selectedImagePath);
	}
	
	public void removeMedia(String selectedImagePath) {
		this.media.remove(selectedImagePath);
	}
	
	public void addTag(String tag) {
		this.tags.add(tag);
	}
	
	public void removeTag(String tag) {
		this.tags.remove(tag);
	}
	
	public List<String> getTags() {
		return tags;
	}
	
	public String getLayout() {
		return layout;
	}

	public void setLayout(String layout) {
		this.layout = layout;
	}

	public String[] getText() {
		return text;
	}

	public void setText(Integer position, String text) {
		this.text[position] = text;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getCategory() {
		return category;
	}

	public void setCategory(String category) {
		this.category = category;
	}

	public ItemBean getReferredItem() {
		return referredItem;
	}

	public void setReferredItem(ItemBean referredItem) {
		this.referredItem = referredItem;
	}

	public UserBean getAuthor() {
		return author;
	}

	public void setAuthor(UserBean author) {
		this.author = author;
	}

	public List<String> getMediaPaths(){
		return this.media;
	}
	
	public String getType() {
		return type;
	}
	
	public void setType(String type) {
		this.type = type;
	}

}
