package logic.entity;

import java.util.Date;

import logic.enumeration.MovieGenre;

public class Movie extends Item {
	private Integer duration;
	private MovieGenre genre;
	
	public Movie(String name, Date publishingDate,Integer duration, String genre) {
		this.setName(name);
		this.setPublishingDate(publishingDate);
		this.duration = duration;
		setGenre(genre);
	}
	
	public Integer getDuration() {
		return this.duration;
	}
	
	public MovieGenre getGenre() {
		return this.genre;
	}
	
	public char getType() {
		return 'M';
	}
	
	public String getInfo() {
		return duration + ";" + genre;
	}
	
	public void setDuration(Integer duration) {
		this.duration = duration;
	}
	
	public void setGenre(MovieGenre genre) {
		this.genre = genre;
	}
	public void setGenre(String genre) {
		if(genre != null) {
			for (MovieGenre value : MovieGenre.values()) {
				if (genre.equals(value.toString())){
					  this.genre = value;
					  return;
				}
			}
		}
		this.genre = null;
	}

}
