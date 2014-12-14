package com.jashlaviu.jashanoid.actors.bonus;

import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.jashlaviu.jashanoid.JashanoidScreen;
import com.jashlaviu.jashanoid.TextureLoader;

public class BonusGlue extends Bonus {

	public BonusGlue(JashanoidScreen gameScreen, float posX, float posY) {
		super(gameScreen, TextureLoader.bonus_05, posX, posY);
	}
	
	@Override
	public void apply() {
		super.apply();
		gameScreen.setNeedGlue(true);
	}

}
