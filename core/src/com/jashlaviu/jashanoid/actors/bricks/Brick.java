package com.jashlaviu.jashanoid.actors.bricks;

import com.badlogic.gdx.graphics.Texture;
import com.jashlaviu.jashanoid.actors.ActorJashanoid;

public class Brick extends ActorJashanoid {
	
	boolean vulnerable;
	
	public Brick(Texture loaderTexture, float posX, float posY) {
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
