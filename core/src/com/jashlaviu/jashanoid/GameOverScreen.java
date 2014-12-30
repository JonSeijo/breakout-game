package com.jashlaviu.jashanoid;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.jashlaviu.jashanoid.menu.MainMenuScreen;

public class GameOverScreen extends ScreenAdapter{
	
	private Jashanoid game;
	private SpriteBatch batch;
	private Stage stage;	
	private Score score;	
	private BitmapFont fontSmall, fontBig;
	
	public GameOverScreen(Jashanoid game) {
		this.game = game;		
		this.batch = game.getBatch();
		
		fontSmall = new BitmapFont(Gdx.files.internal("fonts/ShareTechMono-Regular26.fnt"));
		fontBig = new BitmapFont(Gdx.files.internal("fonts/ShareTechMono-Regular120.fnt"));
		
		//get Score object
		score = game.getGameScreen().getScore(); 
		
		//Set new hi-score if new score is bigger.
		if(score.getPoints() > score.getHiScore()){
			score.setHiScore(score.getPoints());    
		}
		
		stage = new Stage(game.getViewport(), batch);		
	}
	
	@Override
	public void render(float delta) {
		Gdx.gl.glClearColor(0, 0, 0, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		
		batch.begin();
		
		/*for(int y = 0; y < stage.getHeight(); y += 116)
			for(int x = 0; x < stage.getWidth(); x += 121)
				batch.draw(TextureLoader.back_gui, x, y);		
		*/		
		
		fontBig.draw(batch, "GAME OVER", 90, 500);
		
		fontSmall.draw(batch, "YOUR SCORE", 150, 300);
		fontSmall.draw(batch, "" + score.getPoints(), 170, 260);
		
		fontSmall.draw(batch, "YOUR MAX SCORE", 450, 300);		
		fontSmall.draw(batch, "" + score.getHiScore(), 500, 260);
		
		fontSmall.draw(batch, "Press [R] to restart", 250, 50);
		batch.end();
		
		
		if(Gdx.input.isKeyJustPressed(Keys.R)){
			game.setScreen(new MainMenuScreen(game));
		}
		
		// CTRL + H = resets hi-score.
		if(Gdx.input.isKeyPressed(Keys.CONTROL_LEFT)){
			if(Gdx.input.isKeyJustPressed(Keys.H)){
				score.resetHiScore();				
			}
		}		
	}
	
	@Override
	public void resize(int width, int height) {	
		game.updateViewport(width, height);
		stage.getViewport().update(game.getViewport().getScreenWidth(), game.getViewport().getScreenHeight());
	}	
	
	@Override
	public void hide() {
		dispose();
	}
	
	@Override
	public void dispose() {
		stage.dispose();
		fontBig.dispose();
		fontSmall.dispose();
	}

}
