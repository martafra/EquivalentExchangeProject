package logic.entity;
import java.util.ArrayList;

public class Wallet {
	
	private Integer currentCredit;
	private ArrayList<Order> orderList;
	
	public Integer getCurrentCredit() {
		return this.currentCredit;
	}
	public Order getOrder(int index) {
		return this.orderList.get(index);
	}
	public ArrayList<Order> getOrderList(){
		return this.orderList;
	}
	
	public boolean setCurrentCredit(Integer n) {
		if (n > 0) {
		this.currentCredit = n;
		return true;
		}
		
		else {
			return false;
		}
	}
	public void addOrder(Order order) {
		this.orderList.add(order);
	}
	public void removeOrder(int index) {
		this.orderList.remove(index);
	}
	
}
