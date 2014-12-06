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
		glue = true;
	}

	/**
	 * Returns a new vector with the bounce direction in the given point.
	 * (point = global coordinate)
	 * Assumming ball size = 16px;
	 */
	public Vector2 getBounceDirection(Vector2 point) {
	
		float collisionPoint = point.x + 8 - getX();
		
		float minPos = 0;
		float maxPos = getWidth();
		
		float minAngle = 160;
		float maxAngle = 20;
		
		float posSpan = maxPos - minPos;
		float angleSpan = maxAngle - minAngle;
		
		float scaledValue = (collisionPoint - minPos) / posSpan;		
		float translatedValue = minAngle + (scaledValue * angleSpan);
		
		float angle = translatedValue;
	
		Vector2 bounceDirection = new Vector2(0, -0.5f);
		bounceDirection.setAngle(angle);
		bounceDirection.nor();
		
		System.out.println(bounceDirection.angle());
		
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
