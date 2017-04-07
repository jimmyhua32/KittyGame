package infogame;

import java.awt.Font;

import org.lwjgl.input.Mouse;
import org.newdawn.slick.Color;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.TrueTypeFont;
import org.newdawn.slick.state.BasicGameState;
import org.newdawn.slick.state.StateBasedGame;

public class Help extends BasicGameState {

	private Image background;
	private Image back;
	
	public Help(int state) {
		
	}
	
	public void init(GameContainer gc, StateBasedGame sbg) throws SlickException {
		background = new Image("res/splash.png");
		back = new Image("res/back.png");
	}
	
	public void render(GameContainer gc, StateBasedGame sbg, Graphics g) throws SlickException {
		background.draw(0, 0);
		back.draw(10, 650);
		g.setColor(Color.yellow);
		Font f = new Font("Cooper Black", Font.BOLD, 70);
		TrueTypeFont ttf = new TrueTypeFont(f, true);
		
		ttf.drawString(510, 10, "HELP", Color.yellow);
		
		f = new Font("Georgia", Font.PLAIN, 18);
		ttf = new TrueTypeFont(f, true);

		
		ttf.drawString(20, 150, "This is the help screen", Color.yellow);
	}
	
	public void update(GameContainer gc, StateBasedGame sbg, int ms) throws SlickException {
		int posX = gc.getInput().getAbsoluteMouseX();
		int posY = gc.getInput().getAbsoluteMouseY();
		
		if ((posX >= 10 && posX <= 74) && (posY >= 650 && posY <= 714)) {
			if (Mouse.isButtonDown(0)) {
				sbg.enterState(0);
			}
		}

	}

	public int getID() {
		return 1;
	}

}
