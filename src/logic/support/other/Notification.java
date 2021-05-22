package logic.support.other;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import logic.enumeration.NotificationType;

public class Notification {
    private String sender;
    private Date date;
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
    private void addParameter(String key, String value){
        options.put(key, value);
    }
}
