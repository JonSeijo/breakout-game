package com.jashlaviu.jashanoid.actors;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Actor;

public class ActorJashanoid extends Actor{
	
	protected TextureRegion region;
	protected Rectangle collisionBounds;
	
	public ActorJashanoid(Texture loaderTexture) {
		region = new TextureRegion(loaderTexture);
		setSize(region.getRegionWidth(), region.getRegionHeight());
		collisionBounds = new Rectangle(getX(), getY(), getWidth(), getHeight());		
	}
	
	@Override
	public void draw(Batch batch, float parentAlpha) {
		Color col = getColor();		
		batch.setColor(col.r, col.g, col.b, col.a * parentAlpha);
		
		batch.draw(region, getX(), getY(), 
				getOriginX(), getOriginY(), getWidth(), getHeight(), 
				getScaleX(), getScaleY(), getRotation());
	}
	
	@Override
	public void act(float delta) {
		super.act(delta);
		updateCollisionBounds();
	}	
	
	@Override
	protected void positionChanged() {
		super.positionChanged();
		updateCollisionBounds();
	}
	
	public void updateCollisionBounds(){
		collisionBounds.setPosition(getX(), getY());
	}
	
	public Rectangle getCollisionBounds(){
		return collisionBounds;
	}
	
	public Vector2 getPosition(){
		return new Vector2(getX(), getY());
	}
}



















