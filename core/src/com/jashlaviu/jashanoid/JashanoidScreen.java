package com.jashlaviu.jashanoid;

import java.util.ArrayList;
import java.util.Iterator;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.graphics.GL20;
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
import com.jashlaviu.jashanoid.actors.bricks.Brick;
import com.jashlaviu.jashanoid.inputhandler.Controller;
import com.jashlaviu.jashanoid.inputhandler.InputHandler;

public class JashanoidScreen extends ScreenAdapter{	
	private ShapeRenderer shaper;
	private Bounds bounds;
	private Stage stage;
	
	private Platform platform;
	private ArrayList<Ball> balls;
	private ArrayList<Brick> bricks;
	private InputHandler inputHandler;
	private LevelCreator levelCreator;
	private Controller controller;
	
	private Vector2 takeOffPoint;
	
	private int level;
	
	public JashanoidScreen(Jashanoid game) {		
		shaper = game.getShaper();
		bounds = new Bounds();
		balls = new ArrayList<Ball>();
		bricks = new ArrayList<Brick>();
		levelCreator = new LevelCreator(bricks);
		
		platform = new Platform();		
		takeOffPoint = new Vector2(platform.getCenterX() + 20, platform.getTop());			
		
		stage = new Stage(new FitViewport(Gdx.graphics.getWidth(), 
				Gdx.graphics.getHeight()), game.getBatch());		
		
		stage.addActor(platform);
		
		controller = new Controller(this);			
		inputHandler = new InputHandler(controller);	
		Gdx.input.setInputProcessor(inputHandler);		
		
		level = 0;
		levelUp();
	}

	@Override
	public void render(float delta) {
		Gdx.gl.glClearColor(1, 1, 1, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		
		bounds.draw(shaper);		
		stage.draw();
		
		controller.update(delta);
		stage.act();
		updateLogic(delta);
		updateGameOver();
	}
	
	
	private void levelUp() {
		level++;
		levelCreator.setLevel(level);
		for(Brick brick : bricks)
			stage.addActor(brick);		
		
		platform.reset();
		
		for(Ball ball : balls) ball.remove();	//Remove balls from stage 	
		balls.clear();		//Deletes balls in array
		
		balls.add(new Ball(this, takeOffPoint, platform.getBounceDirection(getTakeOff())));	
		stage.addActor(balls.get(0));
		
	}
	
	private void updateGameOver(){
		boolean noMoreBricks = true;
		
		for(Brick brick : bricks)
			if(!brick.isIndestructible())
				noMoreBricks = false;		
		
		if(noMoreBricks)
			levelUp();			
	}
	
	private void updateLogic(float delta){		
		//Platform collisions with left and right bounds.
		updateCollisionsPlatformBounds();
		
		//Handles ball collisions with bounds.
		//If collide with down bounds, resets game.
		updateCollisionsBallBounds();	
		
		updateCollisionsBallBricks(delta);
		
		updateCollisionsBallPlatform();
	}

	private void updateCollisionsBallBricks(float delta) {
		for(Ball ball : balls){			
			boolean alreadyCollided = false;			
			Rectangle ballBounds = ball.getCollisionBounds();
			
			Iterator<Brick> brickIter = bricks.iterator();
			while(brickIter.hasNext()){	
				boolean brickHit = false;
				Brick brick = brickIter.next();	
				Rectangle brickBounds = brick.getCollisionBounds();
				
				// if brick collides with  middle Ball top
				if(brickBounds.contains(ball.getCenterX(), ball.getTop())){
					ball.setDirection(ball.getDirection().x, -ball.getDirection().y);
					brickHit = true;
				}
				
				// if brick collides with  middle Ball bottom
				else if(brickBounds.contains(ball.getCenterX(), ball.getY())){
					ball.setDirection(ball.getDirection().x, -ball.getDirection().y);
					brickHit = true;
				}
				
				// if brick collides with  middle Ball left
				else if(brickBounds.contains(ball.getX(), ball.getCenterY())){
					ball.setDirection(-ball.getDirection().x, ball.getDirection().y);
					brickHit = true;
				}
				
				// if brick collides with  middle Ball right
				else if(brickBounds.contains(ball.getRight(), ball.getCenterY())){
					ball.setDirection(-ball.getDirection().x, ball.getDirection().y);
					brickHit = true;
				}				
				
				
				if(brickHit){
					ball.moveInDirection(delta * 0.2f);
					ball.moreSpeed();
					
					if(brick.isVulnerable()){						
			
						brickIter.remove(); //Removes from the array (for logic updates).
						removeBrickStage(brick);	//Removes from stage, with prior animation.
						
					}else brick.makeVulnerable(); //If is indestructible, it is handled inside
					
				}				
			}			
		}		
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
				ball.setDirection(platform.getBounceDirection(ball.getPosition()));
				ball.moreSpeed();
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
			}

			if(bounds.collideRight(ball)){
				ball.setDirection(-ball.getDirection().x, ball.getDirection().y);
				ball.setPosition(Bounds.GAME_X_RIGHT - ball.getWidth(), ball.getY());
				ball.moreSpeed();
			}
			
			// Mirrors direction in y axis
			if(bounds.collideUp(ball)){				
				ball.setDirection(ball.getDirection().x, -ball.getDirection().y);
				ball.setPosition(ball.getX(), Bounds.GAME_Y_UP - ball.getHeight());
				ball.moreSpeed();
			}
			
			if(bounds.collideDown(ball)){
				ballIter.remove();  //Removes from the array
				ball.remove();		//Removes actor from the stage
				needReset = true;
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
		platform.setGlue(true);
		addBall(getTakeOff(), platform.getBounceDirection(getTakeOff()));		
	}
	
	public void addBall(Vector2 position, Vector2 direction){
		Ball ball = new Ball(this, position, direction);
		balls.add(ball);
		stage.addActor(ball);		
	}
	
	@Override
	public void resize(int width, int height) {
		stage.getViewport().update(width, height);
	}	
	
	@Override
	public void dispose() {
		stage.dispose();
	}
	
	public Platform getPlatform(){
		return platform;
	}
	
	public Vector2 getTakeOff(){
		takeOffPoint.x = platform.getCenterX() + 20;
		return takeOffPoint;
	}
	
}








