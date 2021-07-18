package test;

import static org.junit.Assert.assertEquals;

import java.util.List;

import org.junit.Test;

import logic.bean.ArticleBean;
import logic.bean.ItemBean;
import logic.bean.UserBean;
import logic.controller.application.ArticleRetrievalController;
import logic.controller.application.CommunityController;
import logic.controller.application.ItemDetailsController;
import logic.controller.application.WriteReviewController;
import logic.dao.UserDAO;
import logic.entity.User;
import logic.support.exception.MissingArticleParametersException;

/*
 * 
 *  @author Marta Fraioli
 * 
 */
public class TestArticles {

	private static final String AUTHOR = "martafra";
	private static final String REVIEWER = "Wibbley712";
	
	@Test
	public void testArticleInsertFromMod() {		
	
		UserBean author = new ItemDetailsController().getUserData(AUTHOR);
		WriteReviewController controller = new WriteReviewController();
		ArticleBean article = new ArticleBean();
		article.setAuthor(author);
		article.setTitle("jd82he9sdjs902hd9fjh39fyhvi50yuwddmosf8391p");
		ItemBean referredItem = controller.getBooksList().get(0);
		article.setType("Review");
		article.setLayout("grid");	
		article.setReferredItem(referredItem);
		article.setText(0, "Test");
		
		
		Boolean articleInsert = true;
		try {
			controller.saveArticle(article);
		} catch (MissingArticleParametersException e) {
			articleInsert = false;
		}
		
		assertEquals(articleInsert, true);
		
		if(Boolean.FALSE.equals(articleInsert)) 
			return;
		
		Boolean isFromMod = false;
		if(Boolean.TRUE.equals(author.isModerator())) {
			List<ArticleBean> pendingArticles = controller.getPendingArticles();
			for(ArticleBean pArticle : pendingArticles) {
				if(pArticle.getAuthor().getUserID().equals(author.getUserID())
					&& pArticle.getTitle().equals(article.getTitle())) {
					controller.rejectArticle(pArticle);
					isFromMod = true;
				}
					
			}
			assertEquals(isFromMod, false);
		}
		
		
	}
	
	@Test
	public void testVerifyArticleVote() {
		ArticleRetrievalController controller = new ArticleRetrievalController();
		UserDAO userDAO = new UserDAO();
		User author = userDAO.selectUser(AUTHOR);
		UserBean reviewer = new ItemDetailsController().getUserData(REVIEWER);
		UserBean authorBean = new UserBean();
		authorBean.setUserID(author.getUsername());
		List<ArticleBean> articles = new CommunityController().getAllAcceptedArticles(authorBean);
		int currentModifiedCredit = author.getWallet().getCurrentCredit() + 8;
		
		assertEquals(articles.isEmpty(), false);
		if(articles.isEmpty()) {
			return;
		}
		
		for(ArticleBean art : articles) {
			if(Boolean.FALSE.equals(controller.alreadyVoted(reviewer, art))) {
				controller.rateArticle(reviewer, art, 8);
			}
		}
		
		author = userDAO.selectUser(AUTHOR);
		int newCredit = author.getWallet().getCurrentCredit();
		
		assertEquals(newCredit, currentModifiedCredit);
		
	}
	
	@Test
	public void testCommunitySearch() {
		String userInput="provafalsa";
		CommunityController cController = new CommunityController();
		
		List<ArticleBean> inputArticles = cController.getInputArticles(userInput);
		assertEquals(inputArticles.isEmpty(), true);
		
		List<ArticleBean> bookArticles = cController.getBookReviews();
		boolean res = true;
		for(ArticleBean book: bookArticles){
			if( ('B' != book.getReferredItem().getType())) {
				res = false;
			}
		}
		assertEquals(res, true); 
	}
	
}