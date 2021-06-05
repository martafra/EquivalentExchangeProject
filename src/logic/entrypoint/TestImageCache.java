package logic.entrypoint;

import logic.DAO.ItemInSaleDAO;
import logic.entity.ItemInSale;
import logic.support.other.ImageCache;

public class TestImageCache {
	public static void main(String args[]) {
		ItemInSale item = new ItemInSaleDAO().selectItemInSale(166829714);
		for(String path : item.getMedia()) {
			System.out.println(path);
		}
	
		//ImageCache.getInstance().remove();
	}
	
}
