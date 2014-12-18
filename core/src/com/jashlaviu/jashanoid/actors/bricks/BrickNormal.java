package com.jashlaviu.jashanoid.actors.bricks;

import com.badlogic.gdx.graphics.g2d.TextureRegion;

public class BrickNormal extends Brick {

	public BrickNormal(TextureRegion region, float posX, float posY) {
		super(region, posX, posY);
		vulnerable = true;
	}

}
