package com.jashlaviu.jashanoid.actors.bricks;

import com.jashlaviu.jashanoid.TextureLoader;

public class BrickNormal extends Brick {

	public BrickNormal(float posX, float posY) {
		super(TextureLoader.block1, posX, posY);
		vulnerable = true;
	}

}
