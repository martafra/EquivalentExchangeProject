package logic.support.other;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import logic.enumeration.NotificationType;

public class Notification {
    private String sender = "";
    private Date date = new Date();
    private NotificationType type;
    private HashMap<String, String> options = new HashMap<>();
    
    public String getSender() {
        return sender;
    }
    public Date getDate(){
        return date;
    }
    public NotificationType getType(){
        return type;
    }
    public Map<String, String> getParameters(){
        return options;
    }
    public void setSender(String sender){
        this.sender = sender;
    }
    public void setDate(Date date){
        this.date = date;
    }
    public void setType(NotificationType type){
        this.type = type;
    }
    public void addParameter(String key, String value){
        options.put(key, value);
    }
    public void setParameters(Map<String, String> options) {
    	this.options = (HashMap<String, String>) options;
    }
}
