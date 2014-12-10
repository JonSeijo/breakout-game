package com.jashlaviu.jashanoid.actors.bricks;

import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.jashlaviu.jashanoid.actors.ActorJashanoid;

public class Brick extends ActorJashanoid {
	
	boolean vulnerable;
	boolean indestructible;
	
	int maxHits, currentHits;
	
	public Brick(TextureRegion loaderTexture, float posX, float posY) {
		super(loaderTexture);
		setPosition(posX, posY);
		maxHits = 1;
	}
	
	public boolean isVulnerable(){
		return vulnerable;
	}
	
	public void makeVulnerable(){
		if(!isIndestructible()){
			currentHits++;
			if(currentHits >= maxHits)
				vulnerable = true;
		}
	}
	
	public boolean isIndestructible(){
		return indestructible;
	}
	
	

}
