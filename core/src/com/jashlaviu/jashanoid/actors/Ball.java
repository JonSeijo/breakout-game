package com.jashlaviu.jashanoid.actors;

import com.badlogic.gdx.math.Vector2;
import com.jashlaviu.jashanoid.JashanoidScreen;
import com.jashlaviu.jashanoid.TextureLoader;

public class Ball extends ActorJashanoid { 
	
	private JashanoidScreen screen;

	private float speed;
	private Vector2 direction;
	
	public Ball(JashanoidScreen screen, float posX, float posY, float dirX, float dirY) {
		super(TextureLoader.ball);
		setPosition(posX, posY);
		updateCollisionBounds();
		
		this.screen = screen;
		
		direction = new Vector2(dirX, dirY);
		speed = 400;
	}
	
	public Ball(JashanoidScreen screen, Vector2 pos, Vector2 dir){
		this(screen, pos.x, pos.y, dir.x, dir.y);
	}
	
	@Override
	public void act(float delta) {
		super.act(delta);
		
		speed += 0.05f;
		
		if(screen.getPlatform().hasGlue()){
			setPosition(screen.getTakeOff().x, screen.getTakeOff().y);	
		}
		else{
			moveBy(direction.x * speed * delta, direction.y * speed * delta);
		}
		
		updateCollisionBounds();	
	}
	
	public void setDirection(float x, float y){
		direction.set(x, y);
		direction.nor();
	}
	
	public void setDirection(Vector2 direction){
		this.setDirection(direction.x, direction.y);
	}
	
	public Vector2 getDirection(){
		return direction;
	}

}
