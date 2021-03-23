package logic.entity;
import java.util.ArrayList;
import logic.enumeration.VideoGameGenre;
import logic.enumeration.VGConsole;

public class Videogame extends Item {
	private ArrayList<VGConsole> consoles;
	private VideoGameGenre genre;
	
	public VGConsole getConsole(int index) {
		return this.consoles.get(index);
	}
	public ArrayList<VGConsole> getConsoles() {
		return this.consoles;
	}
	public VideoGameGenre getGenre() {
		return this.genre;
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
}
