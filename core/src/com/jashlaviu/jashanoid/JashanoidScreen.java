package com.jashlaviu.jashanoid;

import java.util.ArrayList;
import java.util.Iterator;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.actions.ParallelAction;
import com.badlogic.gdx.scenes.scene2d.actions.SequenceAction;
import com.jashlaviu.jashanoid.actors.Ball;
import com.jashlaviu.jashanoid.actors.Platform;
import com.jashlaviu.jashanoid.actors.bonus.*;
import com.jashlaviu.jashanoid.actors.bricks.Brick;
import com.jashlaviu.jashanoid.inputhandler.Controller;
import com.jashlaviu.jashanoid.inputhandler.InputHandler;

/**
 * Main part of the game. Central point.
 * Updates every object, handles collisions, creates bonuses,
 * has all the necesary logic so the game can waor.
 * 
 * NOTE: It's a mess, it needs a rework.
 * 
 * @author jonseijo
 *
 */
public class JashanoidScreen extends ScreenAdapter{	
	private Jashanoid game;
	private SpriteBatch batch;
	private ShapeRenderer shaper;
	private Bounds bounds;
	private Stage stage;
	private Gui gui;
	
	private Platform platform;
	
	// The ball is stored in an array for handling the three-balls bonus
	private ArrayList<Ball> balls; 
	private ArrayList<Brick> bricks;	
	
	 // When first was implemented, there could be multiple bonuses at the same time.
	 // It was reworked later.	 
	private ArrayList<Bonus> bonuses;
	
	private InputHandler inputHandler;
	private Controller controller;	
	private LevelCreator levelCreator;
	
	/**
	 * Point the ball is when is 'glued' to the platform
	 */
	private Vector2 takeOffPoint;	
	
	private Brick lastBrick;
	private Score score;
	
	private float soundVolume;
	private int lives, level;
	private final int MAXLEVEL;
	
	private boolean needsGlue;
	
	public JashanoidScreen(Jashanoid game) {
		this.game = game;
		shaper = game.getShaper(); 
		batch = game.getBatch();
		bounds = new Bounds();
		
		balls = new ArrayList<Ball>();
		bricks = new ArrayList<Brick>();
		bonuses = new ArrayList<Bonus>();
		
		levelCreator = new LevelCreator(bricks);
		score = new Score(this);
		gui = new Gui(this);
		
		platform = new Platform();		
		takeOffPoint = getDefaultTakeOff();
		
		stage = new Stage(game.getViewport(), game.getBatch());		
		stage.addActor(platform); // The bricks are added in the level creation step.
		
		controller = new Controller(game, this);			
		inputHandler = new InputHandler(game, controller);	
		Gdx.input.setInputProcessor(inputHandler);		
		
		soundVolume = SoundLoader.soundVolume;
		
		lives = 4;
		MAXLEVEL = 10;
		levelUp();
	}

	/**
	 * Updates and draws actors in the stage,
	 * and checks every collision in the game
	 */
	public void render(float delta) {
		Gdx.gl.glClearColor(0, 0, 0, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		
		renderBackground(); 
		
		bounds.draw(shaper);		
		stage.draw(); //Draws every actor on the stage (platform, balls, bricks)
		
		controller.update(delta);  //Check for input movement and then updates the actors
		stage.act();
		
		updateLogic(); 	  //Handles collision and bonus creation.
		updateGameOver();		

		gui.render(batch);  //Draw the score, lives	and level.	
		
		// PAUSE, details inside.
		if(Gdx.input.isKeyJustPressed(Keys.ENTER) || Gdx.input.isKeyJustPressed(Keys.ESCAPE))
			game.setScreen(new PauseScreen(game, this)); 
		
	}
	
	/**
	 * Draws the correct backgroud for each 
	 * level specified in getLevelTexture().
	 * Numbers are exact.
	 */
	public void renderBackground(){		
		batch.begin();				
		
		//Draw the brown texture in all the screen
		for(int y = 0; y < Bounds.GAME_Y_UP; y += 116){
			for(int x = 0; x < Bounds.SCORE_X_RIGHT; x += 121){
				batch.draw(TextureLoader.back_gui, x, y);
			}
		}
		
		//Draw the level texture inside the bounds
		for(int y = Bounds.GAME_Y_DOWN; y < Bounds.GAME_Y_UP; y += 116){
			for(int x = Bounds.GAME_X_LEFT; x < Bounds.GAME_X_RIGHT; x += 121){
				batch.draw(getLevelTexture(), x, y);
			}
		}
		
		batch.end();
	}
	
	/**
	 * Returns the textureregion wanted for each level 
	 */
	public TextureRegion getLevelTexture(){
		if(level == 1 || level == 6) return TextureLoader.back_blue;		
		if(level == 2 || level == 7) return TextureLoader.back_green;
		if(level == 3 || level == 8) return TextureLoader.back_red;
		if(level == 4 || level == 9) return TextureLoader.back_yellow;
		if(level == 5 || level == 10) return TextureLoader.back_violet;
		
		else
			return TextureLoader.back_blue;
	}
	
	/**
	 * Creates new level. If MAXLEVEL was beaten, 
	 * changes to the win screen
	 */
	public void levelUp() {
		if(level < MAXLEVEL){
			level++;
			score.addPoints(Score.LEVEL);		
			SoundLoader.level.play(soundVolume);		
			
			//Remove the bricks that could still be in the level (indestructibles, passing level for a bonus)
			for(Brick brick : bricks) 
				brick.remove();     //Remove actor from stage
			bricks.clear();			//Remove brick from array			
			
			for(Bonus bonus : bonuses) 
				bonus.remove();
			bonuses.clear();
			
			levelCreator.setLevel(level);   //The corresponding bricks are stored in the "bricks" array here.
			for(Brick brick : bricks)
				stage.addActor(brick);		
			
			for(Ball ball : balls) ball.remove();	//Remove balls from stage 	
			balls.clear();							//Deletes balls in array
			
			resetGame();		
		
		}else  // If level is the maxlevel, player wins.
			game.setScreen(new WinScreen(game));
		
	}
	
	
	/**
	 * Checks if level was beaten, and if all lives are lost,
	 */
	private void updateGameOver(){		
		boolean noMoreBricks = true;		
		
		for(Brick brick : bricks)
			if(!brick.isIndestructible())
				noMoreBricks = false;		
			
		
		if(noMoreBricks){
			lastBrick.remove();  //Remove because it is making the fading out animation.
			levelUp();	
		}
		
		if(lives <= 0)
			gameOver();
	}
	
	/**
	 * Checks EVERY collision in the game, and handles bonus creation.	 * 
	 */
	private void updateLogic(){	
		// Every function is commented with details inside each one. 
		updateCollisionsPlatformBounds();
		updateCollisionsBallBounds();			
		updateCollisionsBallBricks();
		updateCollisionsBallPlatform();	
		updateBonus();
	}
	
	/**
	 * 	Checks the bonus collection and apllies
	 */
	private void updateBonus(){		
		if(!bonuses.isEmpty()){		//if there is a bonus falling
			Bonus bonus = bonuses.get(0);
			
			if(bonus.getY() <= 0){	// If bonus boes beyond the screen
				bonuses.get(0).remove();
				bonuses.clear();
			}
			
			// If bonus collides with the platform (player picked the bonus)
			if(bonus.getCollisionBounds().overlaps(platform.getCollisionBounds())){		
				score.addPoints(Score.BONUS);
				disableBonuses();   // disable the active bonuses. Only one active at a time.
				bonus.apply();		
				bonus.remove();		
				bonuses.clear();	
			}
		}		
		

	}

	/**
	 * Checks if ball collides with bricks, and handles the direction of the bounce.
	 */
	private void updateCollisionsBallBricks() {
		for(Ball ball : balls){		//There could be three balls					
			Iterator<Brick> brickIter = bricks.iterator();  //Iterate this way to remove the brick from the array if needed.
			while(brickIter.hasNext()){	
				boolean brickHit = false;
				
				Brick brick = brickIter.next();	
				Rectangle brickBounds = brick.getCollisionBounds();  //Collision bounds are not necesary from the size of the texture.
				
				// if brick collides with  middle Ball top
				if(brickBounds.contains(ball.getCenterX(), ball.getTop())){
					ball.setPosition(ball.getX(), brickBounds.getY() - brickBounds.getHeight());
					ball.setDirection(ball.getDirection().x, -ball.getDirection().y);
					brickHit = true;
				}
				
				// if brick collides with  middle Ball bottom
				else if(brickBounds.contains(ball.getCenterX(), ball.getY())){
					ball.setPosition(ball.getX(), brickBounds.getY() + brickBounds.getHeight());
					ball.setDirection(ball.getDirection().x, -ball.getDirection().y);
					brickHit = true;
				}				
				
				// if brick collides with  middle Ball right
				if(brickBounds.contains(ball.getRight(), ball.getCenterY())){
					ball.setPosition(brickBounds.getX() - ball.getWidth(), ball.getY());
					ball.setDirection(-ball.getDirection().x, ball.getDirection().y);
					brickHit = true;
				}	
				
				// if brick collides with  middle Ball left
				else if(brickBounds.contains(ball.getX(), ball.getCenterY())){
					ball.setPosition(brickBounds.getX() + brickBounds.getWidth(), ball.getY());
					ball.setDirection(-ball.getDirection().x, ball.getDirection().y);
					brickHit = true;
				}				
				
				if(brickHit){			
					ball.moreSpeed();
					if(brick.isVulnerable()){				
						SoundLoader.ball_brick_normal.play(soundVolume);
						
						if(brick.getType().equals("normal")) score.addPoints(Score.BRICK_NORMAL);
						if(brick.getType().equals("hard")) score.addPoints(Score.BRICK_HARD);					
						
						if(balls.size() == 1 && bonuses.isEmpty()){   //Only create bonus when there is one ball and no other bonuses
							randomBonus(brick); //(aka: not the three balls bonus active) 
						}
						brickIter.remove(); //Removes from the array (for logic updates).
						removeBrickStage(brick);	//Removes from stage, with prior animation.		
						lastBrick = brick;
						
					}else {						
						SoundLoader.ball_brick_hard.play(soundVolume);
						brick.makeVulnerable(); //If is indestructible, it is handled inside
					}
					
				}				
			}			
		}		
	}

	/**
	 * Chance of creating a random bonus
	 */
	private void randomBonus(Brick brick){		
		if(MathUtils.random(100) < 15){		// 15% chance of a new bonus	
			// create a random new bonus is the destroyed brick position
			Bonus nBonus = getRandomBonus(this, brick.getX(), brick.getY());  
			bonuses.add(nBonus);
			stage.addActor(nBonus);
		}	
	}
	
	/**
	 * Disable existing bonues.  
	 */
	public void disableBonuses(){
		setNeedGlue(false);
		platform.colapse();		
	}
	
	/**
	 * Returns a random bonus. Percents are from trial and error.
	 */
	private Bonus getRandomBonus(JashanoidScreen screen, float x, float y){
		int ran = MathUtils.random(1, 100);
				
		if(ran <= 65){
			int ran2 = MathUtils.random(1, 4);
			if(ran2 == 1)
				return new BonusSlow(screen, x, y);
			
			if(ran2 == 2)
				return new BonusThree(screen, x, y);
			
			if(ran2 == 3)
				return new BonusGlue(screen, x, y);
			
			if(ran2 == 4)
				return new BonusExpand(screen, x, y);
			
		}else{
			int ran2 = MathUtils.random(1, 3);
			if(ran2 == 1 || ran2 == 2)
				return new BonusLife(screen, x, y);
			
			if(ran2 == 3)
				return new BonusLevel(screen, x, y);
		}
		
		return null;
			
	}
	
	/**
	 * Makes the brick rotate, fade out and removes it from stage
	 */
	private void removeBrickStage(Brick brick){
		SequenceAction seq = new SequenceAction();
		ParallelAction parallel = new ParallelAction();
		
		seq.addAction(Actions.fadeOut(0.5f));
		seq.addAction(Actions.removeActor());
		
		parallel.addAction(seq);
		parallel.addAction(Actions.moveBy(0, -50, 0.6f));
		parallel.addAction(Actions.rotateTo(MathUtils.random(-40, 40), 0.5f));
		
		brick.addAction(parallel);
	}

	/**
	 * 	Checks if ball collides with platform, and handles the direction of the bounce.
	 */
	private void updateCollisionsBallPlatform(){
		for(Ball ball : balls){   //There could be 3 balls at the same time
			Rectangle ballBounds = ball.getCollisionBounds();
			
			if(ballBounds.overlaps(platform.getCollisionBounds())){	

				if(ballBounds.y >= platform.getCollisionBounds().y + platform.getHeight()/2){  //If hits from top
					platform.setPlayAnimation(true);				
					if(needGlue()){    //If ball needs to stick in the palete
						SoundLoader.glue.play(soundVolume);
						takeOffPoint.set(new Vector2(ball.getPosition()));					
						ball.setDirection(platform.getBounceDirection(ball.getPosition()));					
						platform.setGlue(true);						
					}else{		//Normal hit
						SoundLoader.platform_ball.play(soundVolume);
						ball.setDirection(platform.getBounceDirection(ball.getPosition()));
						ball.moreSpeed();
					}
					
				}else{  //Collides with a side
					ball.setDirection(-ball.getDirection().x, ball.getDirection().y);
				}			
			}
		}		
	}
	
	/**
	 * Handles ball collisions with bounds.
	 * If collide with down bounds, resets game (live lost).
	 */
	private void updateCollisionsBallBounds() {
		boolean needReset = false;	
		
		Iterator<Ball> ballIter = balls.iterator();		
		while(ballIter.hasNext()){	
			Ball ball = ballIter.next();	
			
			// Mirrors direction in x axis
			if(bounds.collideLeft(ball)){
				ball.setDirection(-ball.getDirection().x, ball.getDirection().y);
				ball.setPosition(Bounds.GAME_X_LEFT, ball.getY());
				ball.moreSpeed();
				SoundLoader.ball_bounds.play(soundVolume);
			}

			if(bounds.collideRight(ball)){
				ball.setDirection(-ball.getDirection().x, ball.getDirection().y);
				ball.setPosition(Bounds.GAME_X_RIGHT - ball.getWidth(), ball.getY());
				ball.moreSpeed();
				SoundLoader.ball_bounds.play(soundVolume);
			}
			
			// Mirrors direction in y axis
			if(bounds.collideUp(ball)){				
				ball.setDirection(ball.getDirection().x, -ball.getDirection().y);
				ball.setPosition(ball.getX(), Bounds.GAME_Y_UP - ball.getHeight());
				ball.moreSpeed();
				SoundLoader.ball_bounds.play(soundVolume);
			}
			
			if(bounds.collideDown(ball)){  		//The ball is lost
				ballIter.remove();  //Removes from the array
				ball.remove();		//Removes actor from the stage

				if(balls.isEmpty()){
					SoundLoader.lostLive.play(soundVolume);
					lives--;
					needReset = true;
				}
			}
		}		
		//It is outside because I can't operate to arrays while looping
		if(needReset) 
			resetGame();
	}
	
	/**
	 * Checks if platform collisions with left and right bounds. 
	 * Keeps the platform inside bounds
	 */
	private void updateCollisionsPlatformBounds() {
		if(bounds.collideLeft(platform)){
			platform.setPosition(Bounds.GAME_X_LEFT, platform.getY());
		}			
		else if(bounds.collideRight(platform)){
			platform.setPosition(Bounds.GAME_X_RIGHT - 
					platform.getCollisionBounds().getWidth(), platform.getY());
		}
	}
	
	/**
	 * Resets platform, ball and disables bonuses.
	 */
	public void resetGame(){
		disableBonuses();
		platform.reset();
		platform.setGlue(true);
		addBall(getDefaultTakeOff(), platform.getBounceDirection(getDefaultTakeOff()));
		takeOffPoint = getDefaultTakeOff();
	}
	
	/**
	 * Change to the game over screen
	 */
	private void gameOver(){
		game.setScreen(new GameOverScreen(game));
	}
	
	/**
	 * Slow each ball by 40%
	 */
	public void slowBalls(){
		SoundLoader.slow.play(soundVolume);
		for(Ball ball : balls){
			ball.setSpeed(ball.getSpeed() * 0.60f);  //40% slow
		}
	}
	
	/**
	 * Add a new ball with the given position and direction
	 */
	public void addBall(Vector2 position, Vector2 direction){
		Ball ball = new Ball(this, position, direction);
		balls.add(ball);
		stage.addActor(ball);		
	}
	
	public void setNeedGlue(boolean bool){
		needsGlue = bool;
	}
	
	public boolean needGlue(){
		return needsGlue;
	}
	
	public void addLife(){
		SoundLoader.life.play(soundVolume/2f);
		lives++;
	}
	
	public Ball getBall(){
		return balls.get(0);
	}
	
	@Override
	public void show() {
		stage.setViewport(game.getViewport());
	}
	
	@Override
	public void resize(int width, int height) {
		game.updateViewport(width, height);
		stage.getViewport().update(game.getViewport().getScreenWidth(), game.getViewport().getScreenHeight());
	}	
	
	@Override
	public void dispose() {
		stage.dispose();
		gui.dispose();
	}

	public ArrayList<Ball> getBalls(){
		return balls;
	}
	
	public Platform getPlatform(){
		return platform;
	}
	
	/**
	 * Returns the default point of the ball in the platform
	 */
	public Vector2 getDefaultTakeOff(){
		return new Vector2(platform.getDefaultX() + platform.getWidth()/2 + 15, platform.getTop());
	}
	
	/**
	 * Returns current take off point
	 */
	public Vector2 getTakeOff(){
		return takeOffPoint;
	}
	
	public int getLives(){
		return lives;
	}

	public ArrayList<Bonus> getBonuses() {
		return bonuses;
	}
	
	public float getSoundVolume(){
		return soundVolume;
	}
	
	public int getLevel(){
		return level;
	}
	
	public Score getScore(){
		return score;
	}
	
	public Gui getGui(){
		return gui;
	}
	
}








