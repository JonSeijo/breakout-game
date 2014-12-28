package com.jashlaviu.jashanoid;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class PauseScreen extends ScreenAdapter{
	
	private Jashanoid game;
	private SpriteBatch batch;
	private BitmapFont fontBig;
	
	/**
	 * A new screen without background, lets you see the previous screen (the level)
	 * In this new screen, there aren't any updates or game logic, so it freezes,
	 * creating a pause until the user decides to return to the previus screen
	 * @param game
	 */
	public PauseScreen(Jashanoid game) {
		this.game = game;
		this.batch = game.getBatch();
		fontBig = new BitmapFont(Gdx.files.internal("fonts/ShareTechMono-Regular120.fnt"));
	}
		
	@Override
	public void render(float delta) {		
		batch.begin();
		fontBig.draw(batch, "PAUSED", 130, 350);
		batch.end();
		
		
		if(Gdx.input.isKeyJustPressed(Keys.ENTER) || 
				Gdx.input.isKeyJustPressed(Keys.ESCAPE)){
			game.setScreen(game.getGameScreen());
		}
		
	}
	
	
	public void show() {
		
	}
	
	public void hide(){
		dispose();
	}
	
	@Override
	public void dispose() {
		fontBig.dispose();
	}

}
