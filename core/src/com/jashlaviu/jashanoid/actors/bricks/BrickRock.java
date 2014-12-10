package com.jashlaviu.jashanoid.actors.bricks;

import com.jashlaviu.jashanoid.TextureLoader;

public class BrickRock extends Brick {

	public BrickRock(float posX, float posY) {
		super(TextureLoader.block3, posX, posY);
		vulnerable = false;
		indestructible = true;
		
	}

}
