package logic.controller.application;

import java.util.ArrayList;
import java.util.List;

import logic.bean.ArticleBean;
import logic.bean.UserBean;
import logic.dao.ArticleDAO;
import logic.dao.ArticleReviewDAO;
import logic.entity.Article;
import logic.entity.ArticleReview;
import logic.enumeration.ArticleType;

public class CommunityController extends ArticleDataController{

	public List<ArticleBean> getAllAcceptedArticles(){
		List<ArticleBean> articleBeans = new ArrayList<>();
		ArticleDAO articleDAO = new ArticleDAO();
		ArticleReviewDAO reviewDAO = new ArticleReviewDAO();
		
		
		List<Article> articles = articleDAO.getAllArticles(true, null);
		
		for(Article article : articles) {
			ArticleBean bean = this.fromArticleToBean(article);
			articleBeans.add(bean);
			List<ArticleReview> reviews = reviewDAO.getReviewVotes(article.getArticleID());
			
			
			Integer reviewSum = 0;
			
			for(ArticleReview review : reviews) {
				reviewSum += review.getValue();
			}
			
			if(reviews.size() > 0) {
				bean.setVote(reviewSum / reviews.size());
			}
			else {
				bean.setVote(0);
			}
			bean.setNumberOfReviews(reviews.size());
		}
		return articleBeans;
	}
	public List<ArticleBean> getAllAcceptedArticles(UserBean userData) {
		List<ArticleBean> articles = getAllAcceptedArticles();
		
		List<ArticleBean> filteredArticles = new ArrayList<>();
		for(ArticleBean article : articles) {
			if(article.getAuthor().getUserID().equals(userData.getUserID())) {
				filteredArticles.add(article);
			}
		}
			
		return filteredArticles;
	}
	private List<ArticleBean> getFilteredArticles(Character itemType, ArticleType type){
		List<ArticleBean> all = this.getAllAcceptedArticles();
		List<ArticleBean> filtered = new ArrayList<>();
		for (ArticleBean article: all){
			if (itemType == article.getReferredItem().getType() && type.toString().equalsIgnoreCase(article.getType())){
				filtered.add(article);
			}
		}
		return filtered;
	}
	
	
	public List<ArticleBean> getBookReviews(){
		return getFilteredArticles('B', ArticleType.REVIEW);
	}
	public List<ArticleBean> getMovieReviews(){
		return getFilteredArticles('M', ArticleType.REVIEW);
	}
	public List<ArticleBean> getVideogameReviews(){
		return getFilteredArticles('V', ArticleType.REVIEW);
	}
	public List<ArticleBean> getVideogameGuides(){
		return getFilteredArticles('V', ArticleType.GUIDE);
	}
	public List<ArticleBean> getInputArticles(String titleInput){
		List<ArticleBean> all = this.getAllAcceptedArticles();
		List<ArticleBean> filtered = new ArrayList<>();
		for (ArticleBean article: all){
			for(String tag : article.getTags()) {
				if(tag.contains(titleInput)) {
					filtered.add(article);
					break;
				}
			}
			
			if (article.getTitle().contains(titleInput) && !filtered.contains(article)){
				filtered.add(article);
			}
		}
		return filtered;
	}
	
	
}
