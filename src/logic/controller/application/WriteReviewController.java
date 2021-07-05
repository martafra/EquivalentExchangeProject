package logic.controller.application;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import logic.DAO.ArticleDAO;
import logic.DAO.UserDAO;
import logic.bean.ArticleBean;
import logic.entity.Article;
import logic.entity.User;
import logic.enumeration.ArticleType;
import logic.enumeration.LayoutType;
import logic.enumeration.NotificationType;
import logic.support.connection.MessageSender;
import logic.support.other.Notification;

public class WriteReviewController extends ItemRetrieveController{
	
	
	public void saveArticle(ArticleBean articleData) {
		
		UserDAO userDAO = new UserDAO();
		Article article = new Article();
		article.setTitle(articleData.getTitle());
		
		for(Integer i = 0; i < 4; i++)
		{
			article.setText(articleData.getText()[i], i);
		}
		
		for(String mediaPath : articleData.getMediaPaths()) {
			article.addMedia(mediaPath);
		}
		
		for(String tag : articleData.getTags()) {
			article.addTag(tag);
		}
		
		User author = userDAO.selectUser(articleData.getAuthor().getUserID());
		article.setAuthor(author);
		
		switch(articleData.getType()) {
			case "Review":
				article.setType(ArticleType.REVIEW);
				break;
			case "Guide":
				article.setType(ArticleType.GUIDE);
				break;
			default:
				
		}
		
		switch(articleData.getLayout()) {
			case "grid":
				article.setLayout(LayoutType.GRID);
				break;
			case "vertical":
				article.setLayout(LayoutType.VERTICAL);
				break;
			default:
		}
		
		
		
		List<User> mods = userDAO.getModerators();
		
		if(Boolean.TRUE.equals(article.getAuthor().isModerator())) {
			article.validate();
			ArticleDAO articleDAO = new ArticleDAO();
			articleDAO.insertArticle(article);
			
		}else {
			notifyModerators(article, mods);
		}
		
	}
	
	public void acceptReview() {
		
	}
	
	public List<ArticleBean> getPendingArticles(){
		List<ArticleBean> articles = new ArrayList<>();
		
		
		
		return articles;
	}
	
	private void notifyModerators(Article articleData, List<User> mods) {
		
		for(User mod : mods) {
			Notification artNote = new Notification();
			artNote.setDate(new Date());
			artNote.setSender(articleData.getAuthor().getUsername());
			artNote.setType(NotificationType.APPROVAL);
			artNote.addParameter("articleID", articleData.getArticleID().toString());
			
			MessageSender sender = new MessageSender();
			sender.sendNotification(mod.getUsername(), artNote);
		}
		
	}

	

}
