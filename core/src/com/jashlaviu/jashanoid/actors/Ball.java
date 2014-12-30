package com.jashlaviu.jashanoid.actors;

import com.badlogic.gdx.math.Vector2;
import com.jashlaviu.jashanoid.JashanoidScreen;
import com.jashlaviu.jashanoid.TextureLoader;

/**
 * Made to destroy bricks. It always moves in a direction.
 * @author jonseijo
 */
public class Ball extends ActorJashanoid { 
	
	private JashanoidScreen screen;

	private float speed;
	private final float MAXSPEED;	
	private boolean isMaxSpeed;
	
	private Vector2 direction;
	
	public Ball(JashanoidScreen screen, float posX, float posY, float dirX, float dirY) {
		super(TextureLoader.getBall());
		setPosition(posX, posY);
		updateCollisionBounds();
		
		this.screen = screen;		
		direction = new Vector2(dirX, dirY);
		
		speed = 400;
		MAXSPEED = 650 ;
	}
	
	public Ball(JashanoidScreen screen, Vector2 pos, Vector2 dir){
		this(screen, pos.x, pos.y, dir.x, dir.y);
	}
	
	@Override
	public void act(float delta) {
		super.act(delta);
		
		// If the platform has "glue", don't move.
		if(screen.getPlatform().hasGlue()){
			setPosition(screen.getTakeOff().x, screen.getPlatform().getTop());	
		}
		else  //Move in its direction all the time. Direction is changed externally when needed.
			moveInDirection(delta);		
		
		updateCollisionBounds();	
	}
	
	/**
	 * Uses delta time to move, to keep a constant movement between every FPS change.
	 * @param delta
	 */
	public void moveInDirection(float delta){
		moveBy(direction.x * speed * delta, direction.y * speed * delta);
	}
	
	public void setDirection(float x, float y){
		direction.set(x, y);
		direction.nor();
	}
	
	public void setDirection(Vector2 direction){
		this.setDirection(direction.x, direction.y);
	}
	
	/**
	 * Add a small amount to speed unless unless the maxspeed is reached.
	 */
	public void moreSpeed(){
		if(speed < MAXSPEED){
			speed += 2;	
			isMaxSpeed = false;
		}else
			isMaxSpeed = true;
	
	}
	
	public Vector2 getDirection(){
		return direction;
	}
	
	public void setSpeed(float speed){
		this.speed = speed;
	}
	
	public float getSpeed(){
		return speed;
	}
	
	public boolean isMaxSpeed(){
		return isMaxSpeed;
	}

}
