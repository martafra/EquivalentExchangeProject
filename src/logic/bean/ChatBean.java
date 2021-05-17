package logic.bean;

import logic.support.interfaces.Bean;

public class ChatBean implements Bean{
	
	private String sender;
	private String receiver;
	private String messageText;
	
	public String getSender() {
		return sender;
	}
	public String getReceiver() {
		return receiver;
	}
	public String getMessageText() {
		return messageText;
	}
	public void setSender(String sender) {
		this.sender = sender;
	}
	public void setReceiver(String receiver) {
		this.receiver = receiver;
	}
	public void setMessageText(String messageText) {
		this.messageText = messageText;
	}	
}
