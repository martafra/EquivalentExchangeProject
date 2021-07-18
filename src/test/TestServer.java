package test;

import static org.junit.Assert.assertEquals;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.junit.jupiter.api.Test;
import logic.bean.ChatBean;
import logic.bean.LoginBean;
import logic.bean.UserBean;
import logic.controller.application.ChatController;
import logic.controller.application.LoginController;
import logic.entity.ChatMessage;
import logic.support.connection.ConnectionData;
import logic.support.connection.ConnectionServer;
import logic.support.connection.MessageParser;
import logic.support.connection.MessageSender;
import logic.support.connection.SessionHandler;
import logic.support.interfaces.Observer;
import logic.support.other.MailBox;
/*
 * 
 *  @author Marco Valerio
 * 
 */
public class TestServer implements Observer{

	private static final String USERNAME = "Wibbley712";
	private static final String PASSWORD = "1234";
	
	private MailBox mailbox;
	private int testSize = 0;
	private ChatController cController = new ChatController();
	private List<String> messages = new ArrayList<>();
	private static final Integer NUMBER_OF_MESSAGES = 2;
	LoginController controller = new LoginController();

	// Vengono inviati due messaggi di chat in sequenza a se stessi tramite rete,
	// e si verifica se i messaggi vengono ricevuti nello stesso ordine in cui sono
	// inviati

	@Test
	public void testServerSendMessage() {

		MessageSender sender = new MessageSender();
		UserBean userData = getUserByLoginParameters(USERNAME, PASSWORD);
		mailbox = controller.connect(userData);
		mailbox.register(this);
		ChatMessage message = new ChatMessage();
		for (int i = 0; i < NUMBER_OF_MESSAGES; i++) {
			testSize = i;
			messages.add("Questo è il messaggio " + i);

			message.setSender(userData.getUserID());
			message.setDate(new Date());
			message.setText("Questo è il messaggio " + i);
			sender.sendChatMessage(userData.getUserID(), message);
		}
		controller.logout(userData);

	}

	@Override
	public void update() {
		ChatBean message = cController.getLastMessageSent(mailbox);
		if (message == null)
			return;
		assertEquals(message.getMessageText(), messages.get(testSize));

	}

	@Test
	public void testMessageParser() {

		ChatMessage messageToParse = new ChatMessage();
		UserBean user = getUserByLoginParameters(USERNAME, PASSWORD);

		messageToParse.setSender(user.getUserID());
		messageToParse.setDate(new Date());
		messageToParse.setText("messaggio da parsare");
		
		String dateString = new SimpleDateFormat("yyyy-MM-dd_HH-mm-ss").format(messageToParse.getDate());
		
		String messageToCompare = "type:chat;sender:%s;date:%s;text:%s";
		messageToCompare = String.format(messageToCompare, messageToParse.getSender(), dateString, messageToParse.getText());
		String chatMessageString = MessageParser.encodeMessage(messageToParse);
		
		assertEquals(messageToCompare, chatMessageString);
		
		ChatMessage message = MessageParser.parseChatMessage(chatMessageString);
	
		
		assertEquals(message.getText(), messageToParse.getText());
		assertEquals(message.getSender(), messageToParse.getSender());
		assertEquals(message.getDate().toString(), messageToParse.getDate().toString());
	}
	
	@Test
	public void testRegisterToServer() {
		
		
		UserBean user = getUserByLoginParameters(USERNAME, PASSWORD);
		ConnectionServer serverInstance = ConnectionServer.getInstance();
		
		ConnectionData myServerData = serverInstance.getConnectionData();
		SessionHandler sessionHandler = new SessionHandler();
		
		Boolean sessionLoaded = sessionHandler.startSession(user.getUserID(), myServerData.getIP() , myServerData.getPort());
			
		assertEquals(sessionLoaded, true);
		
		List<ConnectionData> connections = sessionHandler.getConnectionData(USERNAME);
		
		Boolean iAmPresent = false;
		for(ConnectionData connection : connections) {
			if(myServerData.toString().equals(connection.toString())) {
				iAmPresent = true;
			}
		}
		assertEquals(iAmPresent, true);
		if(Boolean.TRUE.equals(sessionLoaded))
			sessionHandler.endSession(USERNAME, myServerData.getIP(), myServerData.getPort());
		
	}
	

	private UserBean getUserByLoginParameters(String username, String password) {

		LoginBean loginData = new LoginBean();
		loginData.setUserID(username);
		loginData.setPassword(password);
		return controller.getUserByLoginData(loginData);
	}

}
