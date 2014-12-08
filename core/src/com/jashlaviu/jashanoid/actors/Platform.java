package com.jashlaviu.jashanoid.actors;

import com.badlogic.gdx.math.Vector2;
import com.jashlaviu.jashanoid.TextureLoader;

public class Platform extends ActorJashanoid {
	
	
	private float speed;
	private boolean glue;
	
	public Platform() {
		super(TextureLoader.platform);		
		this.setPosition(300, 40);
		
		speed = 300;
		glue = true;  //If ball must be "glued" to this platform
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
	
	public float getSpeed(){
		return speed;
	}
	
	public void setGlue(boolean bool){
		glue = bool;
	}	
	
	public boolean hasGlue(){
		return glue;
	}
	
	
	
	
}
