package com.jashlaviu.jashanoid.actors.bonus;

import com.jashlaviu.jashanoid.JashanoidScreen;
import com.jashlaviu.jashanoid.SoundLoader;
import com.jashlaviu.jashanoid.TextureLoader;

public class BonusGlue extends Bonus {

	public BonusGlue(JashanoidScreen gameScreen, float posX, float posY) {
		super(gameScreen, TextureLoader.bonus_glue, posX, posY);
	}
	
	@Override
	public void apply() {
		super.apply();
		SoundLoader.glue.play(gameScreen.getSoundVolume());
		gameScreen.setNeedGlue(true);
	}

}
