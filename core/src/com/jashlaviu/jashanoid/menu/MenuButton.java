package com.jashlaviu.jashanoid.menu;

import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.jashlaviu.jashanoid.actors.ActorJashanoid;

public class MenuButton extends ActorJashanoid{
		
	public MenuButton(TextureRegion region, float x, float y) {
		super(region);
		this.setPosition(x, y);		
	}
	
	public void press(){
		
	}
	
}
