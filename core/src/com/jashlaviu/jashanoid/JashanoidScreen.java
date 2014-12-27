package com.jashlaviu.jashanoid;

import java.util.ArrayList;
import java.util.Iterator;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.ScreenAdapter;
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
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.jashlaviu.jashanoid.actors.Ball;
import com.jashlaviu.jashanoid.actors.Platform;
import com.jashlaviu.jashanoid.actors.bonus.Bonus;
import com.jashlaviu.jashanoid.actors.bonus.BonusExpand;
import com.jashlaviu.jashanoid.actors.bonus.BonusGlue;
import com.jashlaviu.jashanoid.actors.bonus.BonusLevel;
import com.jashlaviu.jashanoid.actors.bonus.BonusLife;
import com.jashlaviu.jashanoid.actors.bonus.BonusSlow;
import com.jashlaviu.jashanoid.actors.bonus.BonusThree;
import com.jashlaviu.jashanoid.actors.bricks.Brick;
import com.jashlaviu.jashanoid.inputhandler.Controller;
import com.jashlaviu.jashanoid.inputhandler.InputHandler;

public class JashanoidScreen extends ScreenAdapter{	
	private Jashanoid game;
	private SpriteBatch batch;
	private ShapeRenderer shaper;
	private Bounds bounds;
	private Stage stage;
	private Gui gui;
	
	private Platform platform;
	private ArrayList<Ball> balls;
	private ArrayList<Brick> bricks;
	private ArrayList<Bonus> bonuses;
	
	private InputHandler inputHandler;
	private LevelCreator levelCreator;
	private Controller controller;
	
	private Vector2 takeOffPoint;
	
	private float soundVolume;
	
	private Brick lastBrick;
	private Score score;
	
	private int lives, level;
	
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
		
		stage.addActor(platform);
		
		controller = new Controller(game, this);			
		inputHandler = new InputHandler(game, controller);	
		Gdx.input.setInputProcessor(inputHandler);		
		
		soundVolume = SoundLoader.soundVolume;
		
		lives = 3;
		level = 0;
		levelUp();
	}

	@Override
	public void render(float delta) {
		// Gdx.gl.glClearColor(1/255f, 80/255f, 150/255f, 1);
		Gdx.gl.glClearColor(0, 0, 0, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		
		drawBackground();
		
		bounds.draw(shaper);		
		stage.draw();
		
		controller.update(delta);
		stage.act();
		updateLogic(delta);
		updateGameOver();
		
		batch.begin();
		gui.render(batch);
		batch.end();
	}
	
	public void drawBackground(){		
		batch.begin();		
			
		for(int y = 0; y < Bounds.GAME_Y_UP; y += 116){
			for(int x = 0; x < Bounds.SCORE_X_RIGHT; x += 121){
				batch.draw(TextureLoader.back_gui, x, y);
			}
		}
		
		for(int y = Bounds.GAME_Y_DOWN; y < Bounds.GAME_Y_UP; y += 116){
			for(int x = Bounds.GAME_X_LEFT; x < Bounds.GAME_X_RIGHT; x += 121){
				batch.draw(getLevelTexture(), x, y);
			}
		}
		
		batch.end();
	}
	
	public TextureRegion getLevelTexture(){
		if(level == 1 || level == 6) return TextureLoader.back_blue;		
		if(level == 2 || level == 7) return TextureLoader.back_green;
		if(level == 3 || level == 8) return TextureLoader.back_red;
		if(level == 4 || level == 9) return TextureLoader.back_yellow;
		if(level == 5 || level == 10) return TextureLoader.back_violet;
		
		else
			return TextureLoader.back_blue;
	}
	
	
	public void levelUp() {		
		level++;
		score.addPoints(Score.LEVEL);
		
		SoundLoader.level.play(soundVolume);
		
		for(Brick brick : bricks)
			brick.remove();
		bricks.clear();
		
		for(Bonus bonus : bonuses)
			bonus.remove();
		bonuses.clear();
		
		levelCreator.setLevel(level);
		for(Brick brick : bricks)
			stage.addActor(brick);		
		
		for(Ball ball : balls) ball.remove();	//Remove balls from stage 	
		balls.clear();		//Deletes balls in array
		
		resetGame();		
	}
	
	private void updateGameOver(){
		
		boolean noMoreBricks = true;		
		
		for(Brick brick : bricks)
			if(!brick.isIndestructible())
				noMoreBricks = false;		
			
		
		if(noMoreBricks){
			lastBrick.remove();
			levelUp();	
		}
		
		if(lives <= 0)
			gameOver();
	}
	
	private void updateLogic(float delta){		
		//Platform collisions with left and right bounds.
		updateCollisionsPlatformBounds();
		
		//Handles ball collisions with bounds.
		//If collide with down bounds, resets game.
		updateCollisionsBallBounds();	
		
		updateCollisionsBallBricks(delta);
		
		updateCollisionsBallPlatform();
		
		updateBonus();
	}
	
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

	private void updateCollisionsBallBricks(float delta) {
		for(Ball ball : balls){							
			Iterator<Brick> brickIter = bricks.iterator();
			while(brickIter.hasNext()){	
				boolean brickHit = false;
				Brick brick = brickIter.next();	
				Rectangle brickBounds = brick.getCollisionBounds();
				
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
						
						if(balls.size() < 2){   //Only create bonus when there is one ball.
							randomBonus(brick);
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

	
	private void randomBonus(Brick brick){
		if(bonuses.isEmpty()){
			if(MathUtils.random(100) < 16){		// 16% chance of a new bonus	
				// create a random new bonus is the destroyed brick position
				Bonus nBonus = getRandomBonus(this, brick.getX(), brick.getY());  
				bonuses.add(nBonus);
				stage.addActor(nBonus);
			}
		}
	}
	
	public void disableBonuses(){
		setNeedGlue(false);
		platform.colapse();		
	}
	
	private Bonus getRandomBonus(JashanoidScreen screen, float x, float y){
		int ran = MathUtils.random(1, 100);
				
		if(ran <= 70){
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
			int ran2 = MathUtils.random(1, 2);
			if(ran2 == 1)
				return new BonusLevel(screen, x, y);
			
			if(ran2 == 2)
				return new BonusLife(screen, x, y);
		}
		
		return null;		
	}
	

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

	private void updateCollisionsBallPlatform(){
		for(Ball ball : balls){
			Rectangle ballBounds = ball.getCollisionBounds();
			
			if(ballBounds.overlaps(platform.getCollisionBounds())){
				
	

				if(ballBounds.y >= platform.getCollisionBounds().y + platform.getHeight()/2){  //If hits from top
					platform.setPlayAnimation(true);				
					if(needGlue()){
						SoundLoader.glue.play(soundVolume);
						takeOffPoint.set(new Vector2(ball.getPosition()));					
						ball.setDirection(platform.getBounceDirection(ball.getPosition()));					
						platform.setGlue(true);
						//setNeedGlue(false);
					}else{
						SoundLoader.platform_ball.play(soundVolume);
						ball.setDirection(platform.getBounceDirection(ball.getPosition()));
						ball.moreSpeed();
					}
					
				}else{
					ball.setDirection(-ball.getDirection().x, ball.getDirection().y);
				}
				
				
			}
		}
		
	}
	
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
			
			if(bounds.collideDown(ball)){
				ballIter.remove();  //Removes from the array
				ball.remove();		//Removes actor from the stage

				if(balls.isEmpty()){
					SoundLoader.lostLive.play(soundVolume);
					lives--;
					needReset = true;
				}
			}
		}		
		if(needReset) resetGame();
	}

	private void updateCollisionsPlatformBounds() {
		if(bounds.collideLeft(platform)){
			platform.setPosition(Bounds.GAME_X_LEFT, platform.getY());

		}
			
		else if(bounds.collideRight(platform)){
			platform.setPosition(Bounds.GAME_X_RIGHT - 
					platform.getCollisionBounds().getWidth(), platform.getY());
		}
	}
	
	public void resetGame(){
		disableBonuses();
		platform.reset();
		platform.setGlue(true);
		addBall(getDefaultTakeOff(), platform.getBounceDirection(getDefaultTakeOff()));
		takeOffPoint = getDefaultTakeOff();
	}
	
	private void gameOver(){
		game.setScreen(new GameOverScreen(game));
	}
	
	public void slowBalls(){
		SoundLoader.slow.play(soundVolume);
		for(Ball ball : balls){
			ball.setSpeed(ball.getSpeed() * 0.60f);  //40% slow
		}
	}
	
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
	
	@Override
	public void hide() {
		
	}
	
	public ArrayList<Ball> getBalls(){
		return balls;
	}
	
	public Platform getPlatform(){
		return platform;
	}
	
	public Vector2 getDefaultTakeOff(){
		return new Vector2(platform.getDefaultX() + platform.getWidth()/2 + 20, platform.getTop());
	}
	
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
	
}








