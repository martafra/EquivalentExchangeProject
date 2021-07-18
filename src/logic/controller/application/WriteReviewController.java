package logic.controller.application;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import logic.bean.ArticleBean;
import logic.dao.ArticleDAO;
import logic.dao.ItemDAO;
import logic.dao.UserDAO;
import logic.entity.Article;
import logic.entity.Item;
import logic.entity.User;
import logic.enumeration.ArticleType;
import logic.enumeration.LayoutType;
import logic.enumeration.NotificationType;
import logic.support.connection.MessageSender;
import logic.support.exception.MissingArticleParametersException;
import logic.support.other.Notification;

public class WriteReviewController extends ArticleDataController{
	
	
	public void saveArticle(ArticleBean articleData) throws MissingArticleParametersException {
		
		UserDAO userDAO = new UserDAO();
		Article article = new Article();
		ItemDAO itemDAO = new ItemDAO();
		
		if(articleData.getTitle() == null || "".equals(articleData.getTitle())) {
			throw new MissingArticleParametersException();
		}
		
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
		
		try {
		
		User author = userDAO.selectUser(articleData.getAuthor().getUserID());
		article.setAuthor(author);
		Item item = itemDAO.selectItem(articleData.getReferredItem().getItemID());
		article.setReferredItem(item);
		
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
		
		}catch(NullPointerException e) {
			throw new MissingArticleParametersException();
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

	public void removeArticle(ArticleBean articleBean){
		ArticleDAO articleDAO = new ArticleDAO();
		
		Article article = new Article();
		article.setArticleID(articleBean.getID());
		
		articleDAO.deleteArticle(article);
	}	

}
