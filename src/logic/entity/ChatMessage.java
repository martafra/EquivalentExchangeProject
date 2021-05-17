package logic.entity;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class ChatMessage {
	private String sender;
	private Date date;
	private String text;
	
	public ChatMessage(String sender, String date, String text) {
		this.sender = sender;
		this.text = text;
		
		DateFormat format = new SimpleDateFormat("yyyy-mm-dd_hh-mm-ss");
		
		try {
			this.date = format.parse(date);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		
	}
	
	public ChatMessage(String sender, Date date, String text) {
		this.sender = sender;
		this.text = text;
		this.date = date;
	}
	
	public String getSender() {
		return this.sender;
	}
	public Date getDate() {
		return this.date;
	}
	public String getText() {
		return this.text;
	}
	
	public void setSender(String sender) {
		this.sender = sender;
	}
	public void setDate(Date date) {
		this.date = date;
	}
	public void setText(String text) {
		this.text = text;
	}
	
	
}
