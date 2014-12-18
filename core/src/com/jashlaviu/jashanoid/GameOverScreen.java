package com.jashlaviu.jashanoid;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

public class GameOverScreen extends ScreenAdapter{
	
	private TextureRegion regionGameOver;
	private SpriteBatch batch;
	
	public GameOverScreen(Jashanoid game) {
		regionGameOver = TextureLoader.game_over;
		this.batch = game.getBatch();
	}
	
	@Override
	public void render(float delta) {
		Gdx.gl.glClearColor(1/255f, 80/255f, 150/255f, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		
		batch.begin();
		batch.draw(regionGameOver, 100, 300);
		batch.end();
		
	}

}
