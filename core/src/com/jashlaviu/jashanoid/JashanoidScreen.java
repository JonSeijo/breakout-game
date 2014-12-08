package com.jashlaviu.jashanoid;

import java.util.ArrayList;
import java.util.Iterator;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Intersector;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Stage;
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
	private Controller controller;
	
	private Vector2 takeOffPoint;
	
	private int level;
	
	public JashanoidScreen(Jashanoid game) {		
		shaper = game.getShaper();
		bounds = new Bounds();
		balls = new ArrayList<Ball>();
		bricks = new ArrayList<Brick>();
		
		platform = new Platform();		
		takeOffPoint = new Vector2(platform.getCenterX() + 20, platform.getTop());
		balls.add(new Ball(this, takeOffPoint, platform.getBounceDirection(takeOffPoint)));				
		
		stage = new Stage(new FitViewport(Gdx.graphics.getWidth(), 
				Gdx.graphics.getHeight()), game.getBatch());		
		
		stage.addActor(platform);
		stage.addActor(balls.get(0));
		
		controller = new Controller(this);			
		inputHandler = new InputHandler(controller);	
		Gdx.input.setInputProcessor(inputHandler);		
		
		levelUp(level);
	}
	
	private void levelUp(int level) {
		LevelCreator.setLevel(bricks, level);
		for(Brick brick : bricks)
			stage.addActor(brick);
		
	}

	@Override
	public void render(float delta) {
		Gdx.gl.glClearColor(1, 1, 1, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		
		bounds.draw(shaper);		
		stage.draw();
		
		controller.update(delta);
		stage.act();
		updateLogic();		
		

	}
	
	public void updateLogic(){		
		//Platform collisions with left and right bounds.
		updateCollisionsPlatformBounds();
		
		//Handles ball collisions with bounds.
		//If collide with down bounds, resets game.
		updateCollisionsBallBounds();	
		
		updateCollisionsBallBricks();
		
		updateCollisionsBallPlatform();
	}

	private void updateCollisionsBallBricks() {
		for(Ball ball : balls){
			Rectangle ballBounds = ball.getCollisionBounds();
			
			Iterator<Brick> brickIter = bricks.iterator();
			while(brickIter.hasNext()){	
				Brick brick = brickIter.next();	
				Rectangle brickBounds = brick.getCollisionBounds();
				
				if(ballBounds.overlaps(brickBounds)){
					
					Rectangle intersection = new Rectangle();
					Intersector.intersectRectangles(brickBounds, ballBounds, intersection);
					
					// Collision from top or bottom.
					if(intersection.width > intersection.height){
						ball.setDirection(ball.getDirection().x, -ball.getDirection().y);
					}
					
					// Collision from left or right side.
					else if(intersection.height > intersection.width){
						ball.setDirection(-ball.getDirection().x, ball.getDirection().y);
					}
					
					// Collision from exact digonal. Mirror both directions.
					else if(intersection.height == intersection.width){
						ball.setDirection(-ball.getDirection().x, -ball.getDirection().y);
					}
					
				
					if(brick.isVulnerable()){
						brick.remove();
						brickIter.remove();
					}else{
						brick.setVulnerable(true);
					}
				}				
			}
			
		}
		
	}

	private void updateCollisionsBallPlatform(){
		for(Ball ball : balls){
			Rectangle ballBounds = ball.getCollisionBounds();
			if(ballBounds.overlaps(platform.getCollisionBounds())){					
				ball.setDirection(platform.getBounceDirection(ball.getPosition()));
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
			}

			if(bounds.collideRight(ball)){
				ball.setDirection(-ball.getDirection().x, ball.getDirection().y);
				ball.setPosition(Bounds.GAME_X_RIGHT - ball.getWidth(), ball.getY());
			}
			
			// Mirrors direction in y axis
			if(bounds.collideUp(ball)){				
				ball.setDirection(ball.getDirection().x, -ball.getDirection().y);
				ball.setPosition(ball.getX(), Bounds.GAME_Y_UP - ball.getHeight());
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








