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
	
	/*public void setCurrentCredit()*/
	
	public void removeOrder(int index) {
		this.orderList.remove(index);
	}
	
}
