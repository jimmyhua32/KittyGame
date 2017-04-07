package infogame;

import org.lwjgl.input.Mouse;
import org.newdawn.slick.*;
import org.newdawn.slick.state.*;

public class Menu extends BasicGameState{
	private Background bg;

	private Image background;
	private Image exit;
	private Image help;
	private Image playNow;
	
	private Sound bye;
	private Music bgm;

	public Menu(int state) {
	
	}
	
	public void init(GameContainer gc, StateBasedGame sbg) throws SlickException {
		exit = new Image("res/exit.png");
		help = new Image("res/help.png");
		playNow = new Image("res/play.png");
		background = new Image("res/menu.png");
		
		bye = new Sound("res/bye.ogg");
		bgm = new Music("res/menu.ogg");
		
		bg = new Background(background);
	}
	
	public void enter(GameContainer gc, StateBasedGame sbg) {
		bgm.loop(1f, .7f);
	}
	
	public void leave(GameContainer gc, StateBasedGame sbg) {
		bgm.stop();
	}
	
	public void render(GameContainer gc,StateBasedGame sbg, Graphics g) throws SlickException {
		bg.render(0, 0);
		playNow.draw(300, 580);
		exit.draw(880, 580);
		help.draw(1210, 650);
	}
	
	public void update(GameContainer gc, StateBasedGame sbg, int ms) throws SlickException {
		int posX = gc.getInput().getAbsoluteMouseX();
		int posY = gc.getInput().getAbsoluteMouseY();
		
		if((posX >= 300 && posX <= 364) && (posY >= 580 && posY <= 644)) {
			if(Mouse.isButtonDown(0)) {
				sbg.enterState(2);
			}
		}
		
		if((posX >= 880 && posX <= 944) && (posY >= 580 && posY <= 644)) {
			if(Mouse.isButtonDown(0)) {
				bgm.stop();
				bye.play();
				while(bye.playing()) {
				}
				System.exit(0);
			}
		}
		if((posX >= 1210 && posX <= 1274) && (posY >= 650 && posY <= 714)) {
			if(Mouse.isButtonDown(0)) {
				sbg.enterState(1);
			}
		}
	}
	
	public int getID() {
		return 0;
	}
}
		