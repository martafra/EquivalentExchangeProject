package logic.controller.application;

import java.util.Date;
import java.util.List;

import logic.DAO.UserDAO;
import logic.bean.ArticleBean;
import logic.entity.Article;
import logic.entity.User;
import logic.enumeration.NotificationType;
import logic.support.connection.MessageSender;
import logic.support.other.Notification;

public class WriteReviewController extends ItemRetrieveController{
	
	
	public void saveReview(ArticleBean articleData) {
		
		Article article = new Article();
		article.setTitle(articleData.getTitle());
		article.setText(null);
		
		UserDAO userDAO = new UserDAO();
		
		List<User> mods = userDAO.getModerators();
		
		for(User mod : mods) {
			if(articleData.getAuthor().getUserID().equals(mod.getUsername())) {
				
			}
		}
		
		notifyModerators(article, mods);
		
		
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
