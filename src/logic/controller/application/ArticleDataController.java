package logic.controller.application;

import logic.bean.ArticleBean;
import logic.bean.ItemBean;
import logic.bean.UserBean;
import logic.entity.Article;
import logic.enumeration.ArticleType;
import logic.enumeration.LayoutType;

public class ArticleDataController extends ItemRetrieveController{
	protected ArticleBean fromArticleToBean(Article data) {
		ArticleBean article = new ArticleBean();
		article.setID(data.getArticleID());
		article.setTitle(data.getTitle());
		
		String type = "";
		if(data.getType().equals(ArticleType.REVIEW))
			type = "Review";
				
		if(data.getType().equals(ArticleType.GUIDE))
			type = "Guide";
				
		article.setType(type);
		
		String layout = "";
		
		if(data.getLayout().equals(LayoutType.GRID))
			layout = "grid";
				
		if(data.getLayout().equals(LayoutType.VERTICAL))
			layout = "vertical";
		
		article.setLayout(layout);
		
		for(Integer i = 0; i < 4; i++) {
			article.setText(i, data.getText(i));
		}
		
		for(String mediaPath : data.getAllMedia()) {
			article.addMedia(mediaPath);
		}
		
		for(String tag : data.getTags()) {
			article.addTag(tag);
		}
		
		UserBean author = new UserBean();
		author.setUserID(data.getAuthor().getUsername());
		author.setName(data.getAuthor().getName());
		author.setLastName(data.getAuthor().getSurname());
		author.setModerator(data.getAuthor().isModerator());
		author.setEmail(data.getAuthor().getEmail());
		
		article.setAuthor(author);
		
		ItemBean referredItem = new ItemBean();
		referredItem.setItemID(data.getReferredItem().getItemID());
		referredItem.setItemName(data.getReferredItem().getName());
		referredItem.setType(data.getReferredItem().getType());
		
		article.setReferredItem(referredItem);
		return article;
		
	}
}
