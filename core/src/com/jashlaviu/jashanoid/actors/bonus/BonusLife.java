package com.jashlaviu.jashanoid.actors.bonus;

import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.jashlaviu.jashanoid.JashanoidScreen;
import com.jashlaviu.jashanoid.TextureLoader;

public class BonusLife extends Bonus {

	public BonusLife(JashanoidScreen gameScreen, float posX, float posY) {
		super(gameScreen, TextureLoader.bonus_02, posX, posY);
		
	}
	
	@Override
	public void apply() {
		super.apply();
		gameScreen.addLife();
	}

}
