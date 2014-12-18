package com.jashlaviu.jashanoid.actors;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Actor;

public class ActorJashanoid extends Actor{
	
	protected TextureRegion region;
	protected Rectangle collisionBounds;
	protected int defaultWidth, defaultHeight;
	
	public ActorJashanoid(TextureRegion loaderTexture) {
		region = loaderTexture;
		defaultWidth = region.getRegionWidth();
		defaultHeight = region.getRegionHeight();
		setSize(defaultWidth, defaultHeight);
		collisionBounds = new Rectangle(getX(), getY(), getWidth(), getHeight());		
	}
	
	@Override
	public void draw(Batch batch, float parentAlpha) {
		Color col = getColor();		
		batch.setColor(col.r, col.g, col.b, col.a * parentAlpha);
		
		batch.draw(getRegion(), getX(), getY(), 
				getOriginX(), getOriginY(), getWidth(), getHeight(), 
				getScaleX(), getScaleY(), getRotation());
	}
	
	@Override
	public void act(float delta) {
		super.act(delta);
		updateCollisionBounds();
	}	
	
	@Override
	public void setPosition(float x, float y) {
		super.setPosition(x, y);
		updateCollisionBounds();
	}
	
	@Override
	protected void positionChanged() {
		super.positionChanged();
		updateCollisionBounds();
	}
	
	public TextureRegion getRegion(){
		return region;
	}
	
	public void setRegion(TextureRegion newRegion){
		region = newRegion;
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
	
	public int getDefaultWidth(){
		return defaultWidth;
	}
	
}



















