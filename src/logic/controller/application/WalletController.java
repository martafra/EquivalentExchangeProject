package logic.controller.application;

import java.util.ArrayList;
import java.util.List;

import logic.bean.ItemInSaleBean;
import logic.bean.OrderBean;
import logic.bean.UserBean;
import logic.dao.OrderDAO;
import logic.dao.UserDAO;
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
		List<Order> orders = orderDAO.selectAllOrders(username);
		List<OrderBean> orderBeans = new ArrayList<>();
		
		for (Order order: orders) {
			OrderBean orderBean = new OrderBean();
			UserBean buyerBean = new UserBean();
			UserBean sellerBean = new UserBean();
			ItemInSaleBean itemBean = new ItemInSaleBean();
			orderBean.setStartDate(order.getStartDate());
			orderBean.setOrderDate(order.getOrderDate());
			orderBean.setOrderID(order.getOrderID());
			buyerBean.setUserID(order.getBuyer().getUsername());
			sellerBean.setUserID(order.getInvolvedItem().getSeller().getUsername());
			itemBean.setSeller(sellerBean);
			itemBean.setPrice(order.getInvolvedItem().getPrice());
			itemBean.setItemID(order.getInvolvedItem().getItemInSaleID());
			itemBean.setItemName(order.getInvolvedItem().getReferredItem().getName());
			itemBean.setMediaPath(order.getInvolvedItem().getMedia().get(0));
			orderBean.setBuyer(buyerBean);
			orderBean.setInvolvedItem(itemBean);
			orderBean.setBuyerStatus(order.getBuyerStatus());
			orderBean.setSellerStatus(order.getSellerStatus());
			orderBeans.add(orderBean);
		}
		
		return orderBeans;
	}
}
