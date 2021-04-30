package logic.entity;


import logic.enumeration.MovieGenre;

public class Movie extends Item {
	private Integer duration;
	private MovieGenre genre;
	
	public Movie(String name, String publishingDate,Integer duration, MovieGenre genre) {
		this.setName(name);
		this.setPublishingDate(publishingDate);
		this.duration = duration;
		this.genre = genre;
	}
	
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
