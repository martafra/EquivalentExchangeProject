package logic.bean;

import java.util.Date;

import logic.support.interfaces.Bean;

public class ChatBean implements Bean{
	
	private String sender;
	private String receiver;
	private String messageText;
	private Date messageDate;
	
	public String getSender() {
		return sender;
	}
	public String getReceiver() {
		return receiver;
	}
	public String getMessageText() {
		return messageText;
	}
	public Date getDate() {
		return this.messageDate;
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
	public void setDate(Date date) {
		this.messageDate = date;
	}
}
