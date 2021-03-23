package logic.entity;
import logic.enumeration.MovieGenre;

public class Movie extends Item {
	private Integer duration;
	private MovieGenre genre;
	
	public Integer getDuration() {
		return this.duration;
	}
	
	public MovieGenre getGenre() {
		return this.genre;
	}
	
	public void setDuration(Integer duration) {
		this.duration = duration;
	}
	
	public void setGenre(MovieGenre genre) {
		this.genre = genre;
	}
	

}
