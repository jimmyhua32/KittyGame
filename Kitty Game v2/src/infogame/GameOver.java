package infogame;

import org.lwjgl.input.Mouse;
import org.newdawn.slick.*;
import org.newdawn.slick.state.*;

public class GameOver extends BasicGameState {
	private Background bg;
	
	private Image background;
	private Image exit;
	private Image replay;
	
	private Sound bye;
	private Sound death;
	private Sound revive;
	
	private int score;

	public GameOver (int state) {
		
	}
	
	public void enter(GameContainer gc, StateBasedGame sbg) throws SlickException {
		death.play(.5f, .4f);
	}
	
	public void leave(GameContainer gc, StateBasedGame sbg) throws SlickException {
		revive.play(1f, .5f);
	}
	
	public void init (GameContainer gc, StateBasedGame sbg) throws SlickException {
		background = new Image("res/gameover.png");
		exit = new Image("res/exit2.png");
		replay = new Image("res/replay.png");
		
		bye = new Sound("res/bye.ogg");
		death = new Sound("res/death2.ogg");
		revive = new Sound("res/revive.ogg");
		
		bg = new Background(background);
	}
	
	public void render (GameContainer gc,StateBasedGame sbg, Graphics g) throws SlickException {
		bg.render(0, 0);
		replay.draw(300, 580);
		exit.draw(880, 580);
		g.drawString("Final Score: " + score, 1130, 700);
	}
	
	public void update (GameContainer gc, StateBasedGame sbg, int ms) throws SlickException {
		int posX = gc.getInput().getAbsoluteMouseX();
		int posY = gc.getInput().getAbsoluteMouseY();
		
		if((posX >= 300 && posX <= 364) && (posY >= 580 && posY <= 644)) {
			if(Mouse.isButtonDown(0)){
				sbg.enterState(2);
			}
		}
		
		if((posX >= 880 && posX <= 944) && (posY >= 580 && posY <= 644)) {
			if(Mouse.isButtonDown(0)){
				bye.play();
				while(bye.playing()) {
				}
				System.exit(0);
			}
		}
	}
	
	public int getID() {
		return 3;
	}
}
		
		