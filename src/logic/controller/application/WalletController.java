package logic.controller.application;

import java.util.ArrayList;
import java.util.List;

import logic.DAO.OrderDAO;
import logic.DAO.UserDAO;
import logic.bean.ItemInSaleBean;
import logic.bean.OrderBean;
import logic.bean.UserBean;
import logic.entity.Order;
import logic.entity.User;

public class WalletController {
	
	public Integer getCredit(UserBean userBean) {
		
		String username = userBean.getUserID();
		UserDAO userDAO = new UserDAO();
		User user = userDAO.selectUser(username);
		
		return user.getWallet().getCurrentCredit();
	}
	
	public List<OrderBean> getOrderList(UserBean userBean){
		String username = userBean.getUserID();
		OrderDAO orderDAO = new OrderDAO();
		//UserDAO userDAO = new UserDAO();
		ArrayList<Order> orders = orderDAO.selectAllOrders(username);
		List<OrderBean> orderBeans = new ArrayList<OrderBean>();
		
		OrderBean orderBean = new OrderBean();
		UserBean buyerBean = new UserBean();
		ItemInSaleBean itemBean = new ItemInSaleBean();
		for (Order order: orders) {
			orderBean.setStartDate(order.getStartDate());
			orderBean.setOrderID(order.getOrderID());
			buyerBean.setUserID(order.getBuyer().toString());
			itemBean.setItemID(order.getInvolvedItem().getItemInSaleID());
			itemBean.setItemName(order.getInvolvedItem().getReferredItem().getName());
			itemBean.setMediaPath(order.getInvolvedItem().getMedia().get(0));
			orderBean.setBuyer(buyerBean);
			orderBean.setInvolvedItem(itemBean);
		}
		
		return orderBeans;
	}
}
