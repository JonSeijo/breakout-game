package com.jashlaviu.jashanoid.actors.bricks;

import com.jashlaviu.jashanoid.TextureLoader;

public class BrickHard extends Brick{

	public BrickHard(float posX, float posY) {
		super(TextureLoader.brick_11, posX, posY);
		vulnerable = false;
	}

}
