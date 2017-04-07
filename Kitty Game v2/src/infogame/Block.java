package infogame;

import org.newdawn.slick.Color;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Image;
import org.newdawn.slick.geom.Rectangle;
import org.newdawn.slick.geom.Vector2f;

public class Block {
	private Image img;
	protected Vector2f pos;
	private Vector2f vel;
	private GameContainer gc;
	private Color c;
	
	public Block(Vector2f pos, Vector2f vel, Image img, GameContainer gc) {
		this.pos = pos;
		this.vel = vel;
		this.img = img;
		this.gc = gc;
		this.c = Color.white;
	}
	
	//Constructor includes color
	public Block(Vector2f pos, Vector2f vel, Image img, GameContainer gc, Color c) {
		this.pos = pos;
		this.vel = vel;
		this.img = img;
		this.gc = gc;
		this.c = c;
	}

	public void update(int ms) {
		pos.add(vel.copy().scale(ms));
	}
	
	//Changes the sprite
	public void changeImg(Image img) {
		this.img = img;
	}
	
	//Returns the color
	public Color getColor() {
		return c;
	}
	
	public void render() {
		img.draw(pos.x, pos.y, gc.getWidth() / 13, gc.getHeight() / 9, c);
	}
	
	//Returns the sprite hitbox
	public Rectangle getEdge() {
		return new Rectangle(pos.x, pos.y, Math.round(img.getHeight() * 1.8), 
										   Math.round(img.getWidth() * 1.8));
	}

}