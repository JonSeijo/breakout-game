package com.jashlaviu.jashanoid.actors;

import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.jashlaviu.jashanoid.TextureLoader;

public class Platform extends ActorJashanoid {
	
	private TextureRegion regionNormal, regionDown;
		
	private final float DEFAULT_X = 300;
	private final float DEFAULT_Y = 40;
	
	private float speed;
	private boolean glue;
	
	private boolean playAnimation;
	private float animationTime, animationCurrentTime;
	
	
	public Platform() {
		super(TextureLoader.getPlatform());		
		this.setPosition(DEFAULT_X, DEFAULT_Y);
		
		regionNormal = this.region;
		regionDown = TextureLoader.getDownPlatform();
		
		animationTime = 0.1f;
		
		speed = 400;
		glue = true;  //If ball must be "glued" to this platform
	}
	
	@Override
	public void act(float delta) {
		super.act(delta);
		
		if(playAnimation){		
			animationCurrentTime += delta;
			if(animationCurrentTime >= animationTime){
				setPlayAnimation(false);
				useRegionNormal();			
			}
		}
		
		
	}

	/**
	 * Returns a new vector with the bounce direction in the given point.
	 * (point = global coordinate)
	 * Assumming ball size = 16px;
	 */
	public Vector2 getBounceDirection(Vector2 point) {
		
		//Point is a global position.
		//Collision point is relative to the platform
		float collisionPoint = point.x + 8 - getX();  
		
		float minAngle = 160;
		float maxAngle = 20;
		
		float angleSpan = maxAngle - minAngle;
		
		float scaledValue = collisionPoint / getWidth();		
		float angle = minAngle + (scaledValue * angleSpan);
		
		Vector2 bounceDirection = new Vector2(1, 1);
		bounceDirection.setAngle(angle);
		bounceDirection.nor();
		
		return bounceDirection;
	}
	
	public void reset(){
		this.setPosition(DEFAULT_X, DEFAULT_Y);	
		setBounds(getX(), getY(), getDefaultWidth(), getHeight());
		collisionBounds.setWidth(defaultWidth);
		speed = 400;
		setGlue(true);
	}
	
	public void expand(){
		setBounds(getX()-getWidth()/4, getY(), getWidth() * 1.5f, getHeight());
		collisionBounds.setWidth(getWidth());
	}
	
	public void colapse(){
		setBounds(getX(), getY(), getDefaultWidth(), getHeight());
		collisionBounds.setWidth(getDefaultWidth());
	}
	
	public void toggleRegion(){
		if(getRegion() == regionNormal)
			setRegion(regionDown);
		
		else if(getRegion() == regionDown)
			setRegion(regionNormal);		
	}
	
	public void useRegionNormal(){
		setRegion(regionNormal);
	}
	
	public void useRegionDown(){
		setRegion(regionDown);
	}
	
	public float getSpeed(){
		return speed;
	}
	
	public void setGlue(boolean bool){
		glue = bool;
	}	
	
	public boolean hasGlue(){
		return glue;
	}
	
	public float getDefaultX(){
		return DEFAULT_X;
	}
	
	public float getDefaultY(){
		return DEFAULT_Y;
	}

	public void setPlayAnimation(boolean bool) {
		playAnimation = bool;
		animationCurrentTime = 0;
		if(playAnimation == true)
			useRegionDown();
		
	}
	
}
