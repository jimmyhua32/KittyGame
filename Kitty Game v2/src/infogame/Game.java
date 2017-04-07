package infogame;

import java.util.ArrayList;

import org.newdawn.slick.Animation;
import org.newdawn.slick.Color;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.Input;
import org.newdawn.slick.Music;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.Sound;
import org.newdawn.slick.geom.Rectangle;
import org.newdawn.slick.geom.Vector2f;
import org.newdawn.slick.state.BasicGameState;
import org.newdawn.slick.state.StateBasedGame;

public class Game extends BasicGameState {
	private static final float PLAYER_SPEED = .15f;
	
	private ArrayList<Block> friendly;
	private ArrayList<Block> powerUp;
	private ArrayList<Block> hostile;
	private ArrayList<Block> deadly;
	private ArrayList<Animation> exp;
	private ArrayList<Vector2f> expPos;
	
	//Player
	private Block player;
	
	//Image resources
	private Background bg;
	
	private Image background;
	private Image catidle;
	private Image catfire;
	private	Image cupcake1;
	private Image cupcake2;
	private Image cupcake3;
	private Image dog1;
	private Image dog2;
	private Image exp1;
	private Image exp2;
	private Image exp3;
	private Image exp4;
	private Image exp5;
	private Image exp6;
	private Image exp7;
	private Image exp8;
	private Image laser;
	private Image[] expAni;
	
	//Audio resources
	private Music songGame;
	private Sound boom;
	private Sound bye;
	private Sound collect;
	//private Sound death;
	private Sound pew;
	private Sound powerup;
	
	//Variables for the game
	private boolean lost;
	
	//Current player variables
	private Vector2f pos;
	private Vector2f vel;
	private int time;
	private int bgtime;
	private int score;
	private int ammo;
	private boolean hasAmmo;
	
	public Game(int state) {
		
	}
	
	public void init(GameContainer gc, StateBasedGame sbg) throws SlickException {
		gc.setShowFPS(false);
		Graphics g = new Graphics();
		
		Rectangle world = new Rectangle(0, 0, gc.getWidth(), gc.getHeight());
		g.setWorldClip(world);
		
		//Loads image files
		background = new Image("res/background.png");
		catidle = new Image("res/catidle.png");
		catidle.setFilter(Image.FILTER_NEAREST);
		catfire = new Image("res/catfire.png");
		catfire.setFilter(Image.FILTER_NEAREST);
		exp1 = new Image("res/explosion1.png");
		exp2 = new Image("res/explosion2.png");
		exp3 = new Image("res/explosion3.png");
		exp4 = new Image("res/explosion4.png");
		exp5 = new Image("res/explosion5.png");
		exp6 = new Image("res/explosion6.png");
		exp7 = new Image("res/explosion7.png");
		exp8 = new Image("res/explosion8.png");
		cupcake1 = new Image("res/cupcake1.png");
		cupcake2 = new Image("res/cupcake2.png");
		cupcake3 = new Image("res/cupcake3.png");
		dog1 = new Image("res/dog1.png");
		dog1.setFilter(Image.FILTER_NEAREST);
		dog2 = new Image("res/dog2.png");
		dog2.setFilter(Image.FILTER_NEAREST);
		laser = new Image("res/lase.png");
		laser.setFilter(Image.FILTER_NEAREST);
		expAni = new Image[] {exp1, exp2, exp3, exp4, exp5, exp6, exp7, exp8};
		
		for (Image i : expAni)
			i.setFilter(Image.FILTER_NEAREST);
		
		//Loads audio files
		songGame = new Music("res/game.ogg");
		boom = new Sound("res/boom.ogg");
		bye = new Sound("res/bye.ogg");
		collect = new Sound("res/collect.ogg");
		//death = new Sound("res/death.ogg");
		pew = new Sound("res/pew.ogg");
		powerup = new Sound("res/powerup.ogg");
		
		setup(gc);	
	}
	
	//When the game starts
	public void enter(GameContainer gc, StateBasedGame sbg) throws SlickException {
		songGame.loop(1f, .7f);
		setup(gc);
	}
	
	public void leave(GameContainer gc, StateBasedGame sbg) throws SlickException {
		songGame.stop();
	}
	
	//Setups the game every time a new game is started
	private void setup(GameContainer gc) {
		
		lost = false;
		pos = new Vector2f(gc.getWidth() / 5, gc.getHeight() / 2);
		vel = new Vector2f(0, 0);
		
		//Clear the lists
		exp = new ArrayList<Animation>();
		expPos = new ArrayList<Vector2f>();
		friendly = new ArrayList<Block>();
		powerUp = new ArrayList<Block>();
		hostile = new ArrayList<Block>();
		deadly = new ArrayList<Block>();
		
		player = new Block(pos, vel, catidle, gc);
		
		//Start every game with a "clean slate"
		time = 0;
		bgtime = 0;
		score = 0;
		ammo = 5;
		hasAmmo = true;
		
		bg = new Background(background);
	}
	
	public void update(GameContainer gc, StateBasedGame sbg, int ms) {
		
		time+= ms;
		bgtime+= ms;
		hasAmmo = ammo > 0;
		
		//Exits the game when ESC is pressed
		if (gc.getInput().isKeyPressed(Input.KEY_ESCAPE)) {
			songGame.stop();
			bye.play(1.1f, 2f);
			while (bye.playing()){
			}
			System.exit(0);
		}
		
		if(lost) {
			if (gc.getInput().isKeyDown(Input.KEY_ENTER)) 
				setup(gc);
			return;
		}
		
		updatePlayer(gc, ms);
		updateObjects(gc, ms);
		updateLaser(gc, ms);
		if (bgtime > 7000 && bgtime % 10 == 0) {
			updateBackground();
			bgtime = 0;
		}
		
		float spawn_prob = (float) (1.0 + (float)time / 30000);
		int prob = (int) (Math.random() * 1000.0 / ms);
		if (Math.random() * 1000.0 / ms < spawn_prob) {
			spawnObject(gc);
			if (prob % 5 == 0)
				ammo++;
		}
	}
	
	//Changes the background to a random color
	public void updateBackground() {
		int randCol = (int) (Math.random() * 7); 
		//Other colors are too boring or are already included in the cupcakes
		switch (randCol) {
		case 0: 
			bg.changeColor(Color.blue);
			break;
		case 1:
			bg.changeColor(Color.gray);
			break;
		case 2:
			bg.changeColor(Color.green);
			break;
		case 3:
			bg.changeColor(Color.magenta);
			break;
		case 4:
			bg.changeColor(Color.orange);
			break;
		case 5:
			bg.changeColor(Color.red);
			break;
		case 6:
			bg.changeColor(Color.white); //Default color
			break;
		default:
			break;
		}	
	}
	
	//Updates the player position and gets input
	public void updatePlayer(GameContainer gc, int ms) {
		pos.add(vel.scale(ms));
		
		if (gc.getInput().isKeyDown(Input.KEY_RIGHT) && player.pos.x <= gc.getWidth() / 2)
			vel.add(new Vector2f(PLAYER_SPEED,0).scale(ms));
		if (gc.getInput().isKeyDown(Input.KEY_LEFT) && player.pos.x >= 0)
			vel.add(new Vector2f(-PLAYER_SPEED,0).scale(ms));
		if (gc.getInput().isKeyDown(Input.KEY_UP) && player.pos.y >= 0)
			vel.add(new Vector2f(0, -PLAYER_SPEED).scale(ms));
		if (gc.getInput().isKeyDown(Input.KEY_DOWN) && player.pos.y <= gc.getHeight() - 100) 
			vel.add(new Vector2f(0, PLAYER_SPEED).scale(ms));
		vel.scale((float) Math.pow(1.0 / ms, 1.1));
	}
	
	//Updates the laser shot by the player
	public void updateLaser(GameContainer gc, int ms) {
		Vector2f posTemp = new Vector2f(pos);
		posTemp.x+= 80;
		Vector2f velTemp = new Vector2f(PLAYER_SPEED * 9f, 0);
		if (gc.getInput().isKeyPressed(Input.KEY_SPACE) && hasAmmo) {
			deadly.add(new Block(posTemp, velTemp, laser, gc));
			pew.play(1f, .6f);
			ammo--;
			if (score > 0)
				score--;
		}
	}
	
	//Updates the blocks/objects in the game
	public void updateObjects(GameContainer gc, int ms) {
		Rectangle playerHitbox = player.getEdge();
		for (int i = 0; i < friendly.size(); i++) { //Regular cupcakes
			friendly.get(i).update(ms);
			if (friendly.get(i).getEdge().intersects(playerHitbox)) {
				score+= 2;
				friendly.remove(i);
				collect.play(1f, .7f);
				i--;
			} else if (friendly.get(i).pos.x <= -20) {
				friendly.remove(i);
				i--;
			}
		}
		for (int i = 0; i < powerUp.size(); i++) { //Special cupcakes, not necessarily a "power up"
			powerUp.get(i).update(ms);
			if (powerUp.get(i).getEdge().intersects(playerHitbox)) {
				if (powerUp.get(i).getColor().equals(Color.pink)) {//Pink cupcakes
					score+= 30;
					ammo++;
					bg.changeColor(Color.pink);
					bgtime = 4000;
				}
				else if (powerUp.get(i).getColor().equals(Color.yellow)) { //Yellow cupcakes
					score+= 10;
					bg.changeColor(Color.yellow);
					bgtime = 4000;
				}
				else if (powerUp.get(i).getColor().equals(Color.blue)) {//Blue cupcakes
					score+= 50;
					ammo+= 2;
					bg.changeColor(Color.cyan);
					bgtime = 4000;
				}
				powerUp.remove(i);
				collect.play(1f, .7f);
				powerup.play(1f, .7f);
				i--;
			} else if (powerUp.get(i).pos.x <= -20) {
				powerUp.remove(i);
				i--;
			}
		}
		for (int i = 0; i < hostile.size(); i++) { //Dogs
			hostile.get(i).update(ms);
			if (hostile.get(i).getEdge().intersects(playerHitbox)) {
				lost = true;
			} else if (hostile.get(i).pos.x <= -20) {
				hostile.remove(i);
				i--;
			} else {
				for (int k = 0; k < deadly.size(); k++) {
					Rectangle laserHitbox = deadly.get(k).getEdge();
					if (hostile.get(i).getEdge().intersects(laserHitbox)) { //Hit by laser
						exp.add(new Animation(expAni, 40, true));
						expPos.add(hostile.get(i).pos); //Adds pos to possible explosion positions
						hostile.remove(i);
						deadly.remove(k);
						boom.play(1f, .5f);
						k--;
						i--;
						break;
					} else {
						if (deadly.get(k).pos.x >= gc.getWidth()) { //Laser disappears when off screen
							deadly.remove(k);
							k--;
						}
					}
				}
			}
		}
		for (Block d : deadly) {
			d.update(ms);
		}
	}
	
	//Creates a new random cupcake or dog
	private void spawnObject(GameContainer gc) {
		int height = (int) (50 + 100 * Math.random());
		Vector2f pos = new Vector2f();
		Vector2f vel = new Vector2f();
		
		float speed = (float) Math.random() / 5 + 0.5f; //Re-adjust later
		
		//Moving to the left
		pos = new Vector2f(gc.getWidth(), (int) (Math.random() * (gc.getHeight() - height)));
		vel = new Vector2f(-speed, 0); 
		
		//Spawn a random cupcake or dog with size adjusting to container size
		double randObj = Math.random();
      if (randObj <= .1)
      	hostile.add(new Block(pos, vel, dog1, gc));
      else if (randObj <= .3)
         hostile.add(new Block(pos, vel, dog2, gc));
      else if (randObj <= .5) {
      	if (randObj <= 0.32)
         	powerUp.add(new Block(pos, vel, cupcake1, gc, Color.pink));
         else
            friendly.add(new Block(pos, vel, cupcake1, gc));
		}
      else if (randObj <= .8) {
         if (randObj <= 0.54)
         	powerUp.add(new Block(pos, vel, cupcake2, gc, Color.yellow));
         else
            friendly.add(new Block(pos, vel, cupcake2, gc));
		}
      else if (randObj <= .9) {
         if (randObj <= 0.81)
            powerUp.add(new Block(pos, vel, cupcake3, gc, Color.blue));
         else
            friendly.add(new Block(pos, vel, cupcake3, gc));
		}
	}
	
	public void render(GameContainer gc, StateBasedGame sbg, Graphics g) throws SlickException {
		if (lost) {
			//death.play();
			sbg.enterState(3);
		}
		else 
			renderGame(gc, g);
	}
	
	public void renderGame(GameContainer gc, Graphics g) {
		bg.render(0, 0);
		if (gc.getInput().isKeyDown(Input.KEY_SPACE) && hasAmmo) {
			player.changeImg(catfire);
			player.render();
		}
		else {
			player.changeImg(catidle);
			player.render();
		}
		for (Block a : friendly)
			a.render();
		for (Block b : powerUp)
			b.render();
		for (Block c : hostile)
			c.render();
		for (Block d : deadly)
			d.render();
		for (int i = 0; i < exp.size(); i++) {
			exp.get(i).setLooping(false);
			exp.get(i).draw(expPos.get(i).x, expPos.get(i).y, gc.getWidth() / 13, gc.getHeight() / 9);
			if (exp.get(i).isStopped()) {
				exp.remove(i);
				expPos.remove(i);
				i--;
			} 
		}
		g.setColor(Color.white);
		g.drawString("Score: " + score, 0, 0);
		g.drawString("Current Ammo: " + ammo, 0, 20);
	}

	public int getID() {
		return 2;
	}
}