package com.jashlaviu.jashanoid.actors.bonus;

import com.jashlaviu.jashanoid.JashanoidScreen;
import com.jashlaviu.jashanoid.TextureLoader;

public class BonusSlow extends Bonus{

	public BonusSlow(JashanoidScreen gameScreen, float posX, float posY) {
		super(gameScreen, TextureLoader.bonus_slow, posX, posY);
	}
	
	@Override
	public void apply() {
		super.apply();
		gameScreen.slowBalls();
	}
		

}
