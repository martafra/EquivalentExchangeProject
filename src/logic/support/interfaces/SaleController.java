package logic.support.interfaces;

import logic.bean.OrderBean;

public interface SaleController {

	public void acceptOrder(OrderBean orderData);
	
	public void rejectOrder(OrderBean orderData);
	
}
