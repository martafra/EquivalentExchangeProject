package logic.controller.application;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import logic.DAO.ArticleDAO;
import logic.DAO.UserDAO;
import logic.bean.ArticleBean;
import logic.bean.ItemBean;
import logic.bean.UserBean;
import logic.entity.Article;
import logic.entity.User;
import logic.enumeration.ArticleType;
import logic.enumeration.LayoutType;
import logic.enumeration.NotificationType;
import logic.support.connection.MessageSender;
import logic.support.other.Notification;

public class WriteReviewController extends ArticleDataController{
	
	
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
		}else {
			notifyModerators(article, mods);
		}
		
		ArticleDAO articleDAO = new ArticleDAO();
		articleDAO.insertArticle(article);
		
	}
	
	public void acceptArticle(ArticleBean articleData) {
		
		ArticleDAO articleDAO = new ArticleDAO();
		UserDAO userDao = new UserDAO();
		User author = userDao.selectUser(articleData.getAuthor().getUserID());
		List<Article> articles = articleDAO.getAllArticles(false, author);
		
		for(Article article : articles) {
			if(article.getArticleID().equals(articleData.getID())) {
				article.setValidation(true);
				articleDAO.updateArticle(article);
				return;
			}
		}
		
	}
	
	public void rejectArticle(ArticleBean articleData) {
		
		ArticleDAO articleDAO = new ArticleDAO();
		UserDAO userDao = new UserDAO();
		User author = userDao.selectUser(articleData.getAuthor().getUserID());
		List<Article> articles = articleDAO.getAllArticles(false, author);
		
		for(Article article : articles) {
			
			if(article.getArticleID().equals(articleData.getID())) {
				articleDAO.deleteArticle(article);
				return;
			}
		}
	}
	
	
	
	
	public List<ArticleBean> getPendingArticles(){
		ArticleDAO articleDAO = new ArticleDAO();
		List<ArticleBean> articleBeans = new ArrayList<>();
		
		List<Article> articles = articleDAO.getAllArticles(false, null);
		
		for(Article art : articles) {
			ArticleBean artBean = fromArticleToBean(art);
			articleBeans.add(artBean);
		}
		
		return articleBeans;
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
