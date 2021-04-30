package logic.enumeration;

public enum BookGenre {
	ACTION, ADVENTURE, COMEDY, CRIME, FANTASY, HISTORICAL, HORROR, MISTERY, OTHER, POETRY, ROMANCE, SATIRE, SCI_FI, CYBERPUNK, SPECULATIVE, THRILLER, WESTERN;
	
	public static BookGenre getGenre(String genre) {
		return ACTION;
	}
}
