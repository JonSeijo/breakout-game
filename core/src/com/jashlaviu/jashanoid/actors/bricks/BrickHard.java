package com.jashlaviu.jashanoid.actors.bricks;

import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.jashlaviu.jashanoid.TextureLoader;

public class BrickHard extends Brick{
	
	private TextureRegion vulnerableRegion;

	public BrickHard(float posX, float posY) {
		super(TextureLoader.brick_11, posX, posY);
		
		vulnerableRegion = TextureLoader.brick_12;
		
		vulnerable = false;
	}
	
	@Override
	public void makeVulnerable() {
		super.makeVulnerable();
		setRegion(vulnerableRegion);
	}

}
