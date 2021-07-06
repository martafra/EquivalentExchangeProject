package logic.controller.application;

import java.util.ArrayList;
import java.util.List;

import logic.DAO.ArticleDAO;
import logic.DAO.ArticleReviewDAO;
import logic.bean.ArticleBean;
import logic.entity.Article;
import logic.entity.ArticleReview;

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
			
			
		}
		
		
		
		return articleBeans;
		
	}
	
}
