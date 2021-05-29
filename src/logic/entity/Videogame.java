package logic.entity;

import java.util.ArrayList;
import java.util.Date;

import logic.enumeration.VideoGameGenre;
import logic.enumeration.VGConsole;

public class Videogame extends Item {
	private ArrayList<VGConsole> consoles;
	private VideoGameGenre genre;
	
	public Videogame(String name, Date publishingDate, String genre) {
		this.setName(name);
		this.setPublishingDate(publishingDate);
		setGenre(genre);
	}
	public VGConsole getConsole(int index) {
		return this.consoles.get(index);
	}
	public ArrayList<VGConsole> getConsoles() {
		return this.consoles;
	}
	public VideoGameGenre getGenre() {
		return this.genre;
	}
	public char getType() {
		return 'V';
	}
	public void addConsole(VGConsole console) {
		this.consoles.add(console);
	}
	public void removeConsole(int index) {
		this.consoles.remove(index);
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
