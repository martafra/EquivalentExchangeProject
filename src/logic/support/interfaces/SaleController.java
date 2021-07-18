package logic.support.interfaces;

import logic.bean.OrderBean;
import logic.support.exception.InsufficientCreditException;

public interface SaleController {

	public void acceptOrder(OrderBean orderData)throws InsufficientCreditException;
	
	public void rejectOrder(OrderBean orderData);
	
}
