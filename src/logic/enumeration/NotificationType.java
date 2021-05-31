package logic.enumeration;

public enum NotificationType {
    WISHLIST("WISHLIST"),
    APPROVAL("APPROVAL"), 
    REQUEST("REQUEST"), 
    ORDER("ORDER"),
	CHAT("CHAT");
    
    private String type;
    private NotificationType(String type) {
    	this.type = type;
    }
    
    @Override
    public String toString() {
    	return type;
    }
    
}
