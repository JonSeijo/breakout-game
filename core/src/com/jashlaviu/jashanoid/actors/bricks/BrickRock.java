package com.jashlaviu.jashanoid.actors.bricks;

import com.jashlaviu.jashanoid.TextureLoader;

/**
 * Indestructible brick
 * @author jonseijo
 *
 */
public class BrickRock extends Brick {

	public BrickRock(float posX, float posY) {
		super(TextureLoader.brick_21, posX, posY);
		vulnerable = false;
		indestructible = true;
		
		type = "rock";
		
	}

}
