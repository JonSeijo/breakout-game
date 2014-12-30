package com.jashlaviu.jashanoid;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.Stage;

public class PauseScreen extends ScreenAdapter{
	
	private Jashanoid game;
	private JashanoidScreen gameScreen;
	private SpriteBatch batch;
	private BitmapFont fontBig;
	private Stage stage;
	
	private Gui gui;
	
	/**
	 * A new screen with the level backgrund and the gui.
	 * In this new screen, there aren't any updates or game logic, so it freezes,
	 * creating a pause until the user decides to return to the previus screen
	 * @param game
	 */
	public PauseScreen(Jashanoid game, JashanoidScreen gameScreen) {
		this.game = game;
		this.gameScreen = gameScreen;
		this.gui = gameScreen.getGui();
		
		this.batch = game.getBatch();
		fontBig = new BitmapFont(Gdx.files.internal("fonts/ShareTechMono-Regular120.fnt"));
		
		stage = new Stage(game.getViewport(), batch);	
	}
		
	@Override
	public void render(float delta) {
		Gdx.gl.glClearColor(0, 0, 0, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		
		gameScreen.renderBackground();
		gui.render(batch);		
		
		batch.begin();
		// Draws a big "paused" message
		fontBig.draw(batch, "PAUSED", 130, 350);
		batch.end();		
		
		//Quit pause and return to gameScreen
		if(Gdx.input.isKeyJustPressed(Keys.ENTER) || 
				Gdx.input.isKeyJustPressed(Keys.ESCAPE)){
			game.setScreen(game.getGameScreen());
		}
		
		
		//CHEAT:  if game is paused, L + E + V key combination goes to next level.
		if(Gdx.input.isKeyPressed(Keys.L) && 
				Gdx.input.isKeyPressed(Keys.E) && 
				Gdx.input.isKeyPressed(Keys.V)){
			game.setScreen(game.getGameScreen());
			gameScreen.levelUp();	
		}
		
		//CHEAT:  if game is paused, L + I + F key combination give extra life.
		if(Gdx.input.isKeyPressed(Keys.L) && 
				Gdx.input.isKeyPressed(Keys.I) && 
				Gdx.input.isKeyPressed(Keys.F)){
			game.setScreen(game.getGameScreen());
			gameScreen.addLife();	
		}
		
	}
	
	public void hide(){
		dispose();
	}
	
	@Override
	public void dispose() {
		fontBig.dispose();
		stage.dispose();
	}
	
	@Override
	public void resize(int width, int height) {	
		game.updateViewport(width, height);
		stage.getViewport().update(game.getViewport().getScreenWidth(), game.getViewport().getScreenHeight());
	}

}
