package logic.enumeration;

public enum Condition {
	NEW("NEW"), 
	ALMOST_NEW("ALMOST NEW"), 
	VERY_GOOD("VERY GOOD"), 
	GOOD("GOOD"), 
	ACCEPTABLE("ACCEPTABLE"), 
	WORN_OUT("WORN OUT");
	
	private String type;
    private Condition(String type) {
    	this.type = type;
    }
    
    @Override
    public String toString() {
    	return type;
    }
    
    public static Condition valueOfLabel(String label) {
        for (Condition e : values()) {
            if (e.type.equals(label)) {
                return e;
            }
        }
        return null;
    }
}
