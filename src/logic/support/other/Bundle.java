package logic.support.other;

import java.util.HashMap;
import logic.support.interfaces.Bean;

public class Bundle {
	
	private HashMap<String, Bean> content = new HashMap<String, Bean>();
	
	public void add(String id, Bean bean) {
		content.put(id, bean);
	}
	
	public Bean get(String id) {
		return content.get(id);
	}
	
	
}
