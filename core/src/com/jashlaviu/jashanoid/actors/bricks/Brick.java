package com.jashlaviu.jashanoid.actors.bricks;

import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.jashlaviu.jashanoid.actors.ActorJashanoid;

public class Brick extends ActorJashanoid {
	
	boolean vulnerable;
	
	public Brick(TextureRegion loaderTexture, float posX, float posY) {
		super(loaderTexture);
		setPosition(posX, posY);
	}
	
	public boolean isVulnerable(){
		return vulnerable;
	}
	
	public void setVulnerable(boolean bool){
		vulnerable = bool;
	}

}
