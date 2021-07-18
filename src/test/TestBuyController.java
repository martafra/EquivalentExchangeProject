package test;

import org.junit.Test;

import logic.dao.ItemDAO;
import logic.dao.ItemInSaleDAO;
import logic.dao.OrderDAO;
import logic.dao.UserDAO;
import logic.bean.OrderBean;
import logic.bean.UserBean;
import logic.controller.application.BuyController;
import logic.entity.ItemInSale;
import logic.entity.Movie;
import logic.entity.Order;
import logic.entity.User;
import logic.enumeration.Condition;
import logic.enumeration.Gender;
import logic.support.exception.AlreadyRegisteredUserException;
import logic.support.exception.InsufficientCreditException;
import logic.support.other.Notification;

import static org.junit.Assert.*;
import java.util.Date;

/*
 * @author Valentina Martini
 */
public class TestBuyController {
	
	/*	il metodo restoreCredit si occupa di ridare il credito al compratore in caso la compravendita non vada a buon fine. 
	*	Il prezzo dell'oggetto e' 50, il credito attuale del buyer è 150, quindi mi aspetto che il suo nuovo credito sia di 200.
	*/
	@Test
	public void testRestoreCredit() {
		BuyController controller = new BuyController();
		User buyer = new User("giorgino","giorgio","filipponi","giorgio.filipponi@gmail.com","1234", 150);
		User seller = new User("federicoTheBest","federico","francesini","federico.francesini@gmail.com","4321",200);
		Movie item = new Movie("Harry Potter", new Date(), 130, "FANTASY", "ITALIAN");
		ItemInSale itemInSale = new ItemInSale(50, "Questa e' una descrizione", Condition.NEW, item,seller );
		Order order = new Order(buyer, itemInSale);
		controller.restoreCredit(order);
		int credit =  buyer.getWallet().getCurrentCredit();
		assertEquals(200, credit);
	}
	
	/*	il metodo orderAccepted controlla se il credito dell'utente e' sufficiente.
	*	Se e' sufficiente allora provvede a: settare lo status del buyer a true, generare e settare il codice, se il buyer e' il secondo ad accettare setta la 
	*	data di inizio ordine.
	*	nel test il buyer e' il primo ad accettare quindi controllo lo status del buyer, il valore del codice e che la data di inizio non sia stata settata.
	*/
	@Test
	public void testOrderAccepted() {
		//definisco gli oggetti su cui verra' effettuato il test
		User buyer = new User("giorgino","giorgio","filipponi","giorgio.filipponi@gmail.com","1234", 150);
		buyer.setBirthDate(new Date());
		buyer.setGender(Gender.MALE);
		User seller = new User("federicoTheBest","federico","francesini", "federico.francesini@gmail.com","4321",200);
		seller.setBirthDate(new Date());
		seller.setGender(Gender.MALE);
		Movie item = new Movie("Harry Potter", new Date(), 130, "FANTASY", "ITALIAN");
		ItemInSale itemInSale = new ItemInSale(50, "Questa e' una descrizione", Condition.NEW, item,seller );
		Order order = new Order(buyer, itemInSale);
		
		//creo uno stato consistente, per il test, nel database
		UserDAO userDAO = new UserDAO();
		userDAO.deleteUser(buyer.getUsername());
		userDAO.deleteUser(seller.getUsername());
		try {
			userDAO.insertUser(buyer);
			userDAO.insertUser(seller);
		} catch (AlreadyRegisteredUserException e) {
			e.printStackTrace();
		}
		
		ItemDAO itemDAO = new ItemDAO();
		itemDAO.insertItem(item);
		
		ItemInSaleDAO itemInSaleDAO = new ItemInSaleDAO();
		itemInSaleDAO.insertItemInSale(itemInSale);
		
		OrderDAO orderDAO = new OrderDAO();
		orderDAO.insertOrder(order);
		
		
		BuyController controller = new BuyController();
		Boolean result;
		try {
			result = controller.orderAccepted(order.getOrderID());
		} catch (InsufficientCreditException e) {
			result = false;
			e.printStackTrace();
		}
		order = orderDAO.selectOrder(order.getOrderID());
		Boolean ret;
		if(result  && order.getBuyerStatus() == true && order.getCode() != null && order.getStartDate() == null) {
			ret = true;
		}
		else {
			ret = false;
		}
		orderDAO.deleteOrder(order.getOrderID());
		itemInSaleDAO.deleteItemInSale(itemInSale.getItemInSaleID());
		itemDAO.deleteItem(item.getItemID());
		userDAO.deleteUser(seller.getUsername());
		userDAO.deleteUser(buyer.getUsername());
		
		assertEquals(true, ret);
	}
	
	/*	rejectOrder si occupa di cancellare l'ordine nel caso in cui l'utente rifiuta l'ordine e di rendere l'oggetto coinvolto nuovamente disponibile.
	*	testo quindi che l'ordine non sia piu' presente del Database e che la disponibilita' dell'oggetto sia stata settata correttamente.
	*/
	@Test
	public void testRejectOrder() {
		//definisco gli oggetti su cui verra' effettuato il test
		User buyer = new User("giorgino","giorgio","filipponi","giorgio.filipponi@gmail.com","1234", 150);
		buyer.setBirthDate(new Date());
		buyer.setGender(Gender.MALE);
		User seller = new User("federicoTheBest","federico","francesini", "federico.francesini@gmail.com","4321",200);
		seller.setBirthDate(new Date());
		seller.setGender(Gender.MALE);
		Movie item = new Movie("Harry Potter", new Date(), 130, "FANTASY", "ITALIAN");
		ItemInSale itemInSale = new ItemInSale(50, "Questa e' una descrizione", Condition.NEW, item,seller );
		itemInSale.setAvailability(false);
		Order order = new Order(buyer, itemInSale);
		
		//creo uno stato consistente, per il test, nel database
		UserDAO userDAO = new UserDAO();
		userDAO.deleteUser(buyer.getUsername());
		userDAO.deleteUser(seller.getUsername());
		try {
			userDAO.insertUser(buyer);
			userDAO.insertUser(seller);
		} catch (AlreadyRegisteredUserException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
				
		ItemDAO itemDAO = new ItemDAO();
		itemDAO.insertItem(item);
				
		ItemInSaleDAO itemInSaleDAO = new ItemInSaleDAO();
		itemInSaleDAO.insertItemInSale(itemInSale);
				
		OrderDAO orderDAO = new OrderDAO();
		orderDAO.insertOrder(order);
				
		order = orderDAO.selectOrder(order.getOrderID());
		
		OrderBean orderBean = new OrderBean();
		orderBean.setOrderID(order.getOrderID());
		
		BuyController controller = new BuyController();
		controller.rejectOrder(orderBean);
		itemInSale = itemInSaleDAO.selectItemInSale(itemInSale.getItemInSaleID());
		Boolean ret;
		if (itemInSale.getAvailability() ==true && orderDAO.selectOrder(order.getOrderID()) == null) {
			ret = true;
		}
		else {
			ret = false;
		}
		
		orderDAO.deleteOrder(order.getOrderID());
		itemInSaleDAO.deleteItemInSale(itemInSale.getItemInSaleID());
		itemDAO.deleteItem(item.getItemID());
		userDAO.deleteUser(seller.getUsername());
		userDAO.deleteUser(buyer.getUsername());
		
		assertEquals(true, ret);
	}

}
