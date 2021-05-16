package logic.support.other;

import java.util.HashMap;
import logic.support.interfaces.Bean;

public class Bundle {
	
	private HashMap<String, Bean> beanContent = new HashMap<>();
	private HashMap<String, Object> genericContent = new HashMap<>();
	
	
	public void addBean(String id, Bean bean) {
		beanContent.put(id, bean);
	}
	
	public Bean getBean(String id) {
		return beanContent.get(id);
	}
	
	public void addObject(String id, Object obj) {
		genericContent.put(id, obj);
	}

	public Object getObject(String id) {
		return genericContent.get(id);
	}
	
	
	
}
