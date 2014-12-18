package com.jashlaviu.jashanoid.actors.bonus;

import com.jashlaviu.jashanoid.JashanoidScreen;
import com.jashlaviu.jashanoid.TextureLoader;

public class BonusExpand extends Bonus {

	public BonusExpand(JashanoidScreen gameScreen, float posX, float posY) {
		super(gameScreen, TextureLoader.bonus_06, posX, posY);
	}
	
	@Override
	public void apply() {
		super.apply();
		gameScreen.getPlatform().expand();		
	}

}
