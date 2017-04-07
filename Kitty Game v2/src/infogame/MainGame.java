package infogame;

import java.io.FileNotFoundException;

import org.newdawn.slick.AppGameContainer;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.state.StateBasedGame;

public class MainGame extends StateBasedGame {
	public MainGame(String title) {
		super(title);
	}

	public static void main(String[] args) throws SlickException, FileNotFoundException {	
		try {
			AppGameContainer game = new AppGameContainer(new MainGame("Super Kitty Rainbow Cupcake Explosion"));
			game.setDisplayMode(1280, 720, false);
			game.setTargetFrameRate(60); 
			game.start();
		} catch (SlickException e) {
			e.printStackTrace();
		}
	}
	
	public void initStatesList(GameContainer gc) throws SlickException {
		this.addState(new Menu(0));
		this.addState(new Help(1));
		this.addState(new Game(2));
		this.addState(new GameOver(3));
	}
}