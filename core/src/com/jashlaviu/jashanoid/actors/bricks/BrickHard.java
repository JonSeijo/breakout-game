package com.jashlaviu.jashanoid.actors.bricks;

import com.jashlaviu.jashanoid.TextureLoader;

public class BrickHard extends Brick{

	public BrickHard(float posX, float posY) {
		super(TextureLoader.block2, posX, posY);
		vulnerable = false;
	}

}
