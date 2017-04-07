package infogame;

import org.newdawn.slick.Color;
import org.newdawn.slick.Image;

public class Background {
	private Color c;
	private Image img;
	
	//Gets height and width of the container
	public Background(Image img) {
		this.img = img;
		this.c = Color.white;
	}
	
	public Background(Image img, Color c) {
		this(img);
		this.c = c;
	}
	
	//Draws the background at a location with a color filter applied
	public void render(int x, int y) {
		img.draw(x, y, c);
	}
	
	public void changeColor(Color newColor) {
		c = newColor;
	}
}