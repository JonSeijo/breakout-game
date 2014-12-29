package com.jashlaviu.jashanoid;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.jashlaviu.jashanoid.menu.MainMenuScreen;

public class WinScreen extends ScreenAdapter{
	
	private Jashanoid game;
	private SpriteBatch batch;
	private BitmapFont fontBig, fontSmall;
	private Stage stage;
	private Score score;
	
	public WinScreen(Jashanoid game) {
		this.game = game;
		this.batch = game.getBatch();
		
		score = game.getGameScreen().getScore();
		
		fontBig = new BitmapFont(Gdx.files.internal("fonts/ShareTechMono-Regular120.fnt"));		
		fontSmall = new BitmapFont(Gdx.files.internal("fonts/ShareTechMono-Regular26.fnt"));		
		
		stage = new Stage(game.getViewport(), batch);	
		
	}	
	
	@Override
	public void render(float delta) {		
		Gdx.gl.glClearColor(0, 0, 0, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		
		batch.begin();
		
		for(int y = 0; y < stage.getHeight(); y += 116)
			for(int x = 0; x < stage.getWidth(); x += 121)
				batch.draw(TextureLoader.back_gui, x, y);	
		
		fontBig.draw(batch, "VICTORY", 160, 550);	
		
		
		fontSmall.draw(batch, "YOUR SCORE", 150, 420);
		fontSmall.draw(batch, "" + score.getPoints(), 170, 380);		
		fontSmall.draw(batch, "YOUR MAX SCORE", 450, 420);		
		fontSmall.draw(batch, "" + score.getHiScore(), 500, 380);		
		
		batch.draw(TextureLoader.trophy, 300, 200);
		
		fontSmall.draw(batch, "Thanks for playing!", 100, 100);
		fontSmall.draw(batch, "New levels in future updates!", 50, 50);		
		
		fontSmall.draw(batch, "Main Menu", 610, 100);
		fontSmall.draw(batch, "Press [ESC]", 600, 50);
		
		
		batch.end();		
		
		if(Gdx.input.isKeyJustPressed(Keys.ESCAPE)){
			game.setScreen(new MainMenuScreen(game));
		}
	}
	
	public void hide(){
		dispose();
	}
	
	@Override
	public void dispose() {
		fontBig.dispose();
		fontSmall.dispose();
		stage.dispose();
	}

	@Override
	public void resize(int width, int height) {	
		game.updateViewport(width, height);
		stage.getViewport().update(game.getViewport().getScreenWidth(), game.getViewport().getScreenHeight());
	}

}
