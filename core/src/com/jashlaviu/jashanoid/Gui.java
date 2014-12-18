package com.jashlaviu.jashanoid;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

public class Gui {
	
	private JashanoidScreen gameScreen;
	private TextureRegion livesRegion;
	
	public Gui(JashanoidScreen gameScreen) {
		this.gameScreen = gameScreen;
		livesRegion = TextureLoader.getPlatform();
		
	}
	
	public void render(SpriteBatch batch){
		for(int i = 0; i < gameScreen.getLives(); i++){
			batch.draw(livesRegion, Bounds.GAME_X_RIGHT + 45, 300 - i * 30);
		}
	}

}
