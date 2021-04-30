package logic.enumeration;

public enum MovieGenre {
	ACTION, ADVENTURE, COMEDY, CRIME, FANTASY, HISTORICAL, HORROR, MISTERY, OTHER, ROMANCE, SATIRE, SCI_FI, CYBERPUNK, THRILLER, WESTERN;
	
	public static MovieGenre getGenre(String genre) {
		return COMEDY;
	}
}
