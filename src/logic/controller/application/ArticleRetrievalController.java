package logic.controller.application;

import java.util.List;

import logic.DAO.ArticleDAO;
import logic.DAO.ArticleReviewDAO;
import logic.DAO.UserDAO;
import logic.bean.ArticleBean;
import logic.bean.UserBean;
import logic.entity.Article;
import logic.entity.ArticleReview;
import logic.entity.User;

public class ArticleRetrievalController extends ArticleDataController{

	public ArticleBean getArticleData(Integer articleID) {
		ArticleDAO articleDAO = new ArticleDAO();
		Article article = articleDAO.selectArticle(articleID);
		return this.fromArticleToBean(article);
	}
	
	public Boolean alreadyVoted(UserBean user, ArticleBean article) {
		ArticleReviewDAO reviewDAO = new ArticleReviewDAO();
		List<ArticleReview> reviews = reviewDAO.getReviewVotes(article.getID());
		for(ArticleReview review : reviews) {
			if(review.getAuthor().getUsername().equals(user.getUserID())) {
				return true;
			}
		}
		return false;
	}
	
	public void rateArticle(UserBean userData, ArticleBean article, Integer value) {
		
		ArticleReviewDAO reviewDAO = new ArticleReviewDAO();
		UserDAO userDAO = new UserDAO();
		User user = userDAO.selectUser(userData.getUserID());
		ArticleReview review = new ArticleReview(value, user);
		reviewDAO.addReview(article.getID(), review);
		User articleAuthor = userDAO.selectUser(article.getAuthor().getUserID());
		articleAuthor.increaseCredit(value);
		userDAO.updateUser(articleAuthor);
	}

}
