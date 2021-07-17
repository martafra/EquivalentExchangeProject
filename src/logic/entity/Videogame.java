package logic.entity;

import java.util.Date;
import logic.enumeration.VideoGameGenre;
import logic.enumeration.VGConsole;

public class Videogame extends Item {
	private VGConsole console;
	private VideoGameGenre genre;
	
	public Videogame(String name, Date publishingDate, String genre, String console, String language) {
		this.setName(name);
		this.setPublishingDate(publishingDate);
		setGenre(genre);
		setConsole(console);
		setLanguage(language);
		
		this.setItemID(name.hashCode() + console.hashCode() + publishingDate.hashCode() % 2147483647);
	}
	public VGConsole getConsole() {
		return this.console;
	}
	public VideoGameGenre getGenre() {
		return this.genre;
	}
	public Character getType() {
		return 'V';
	}
	
	public void setConsole(VGConsole console) {
		this.console = console;
	}
	
	public void setConsole(String console) {
		if(console != null) {
			for (VGConsole value : VGConsole.values()) {
				if (console.equals(value.toString())){
					  this.console = value;
					  return;
				}
			}
		}
		this.console = null;
	}
	
	public void setGenre(VideoGameGenre genre) {
		this.genre = genre;
	}
	public void setGenre(String genre) {
		if(genre != null) {
			for (VideoGameGenre value : VideoGameGenre.values()) {
				if (genre.equals(value.toString())){
					  this.genre = value;
					  return;
				}
			}
		}
		this.genre = null;
	}
}
