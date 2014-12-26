package com.jashlaviu.jashanoid;


import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.jashlaviu.jashanoid.menu.MainMenuScreen;

public class Jashanoid extends Game {
	private SpriteBatch batch;
	private ShapeRenderer shaper;
	private JashanoidScreen gameScreen;
	private TextureLoader textureLoader;
	private SoundLoader soundLoader;
	private Viewport viewport;
	
	@Override
	public void create () {
		batch = new SpriteBatch();
		shaper = new ShapeRenderer();
		viewport = new FitViewport(Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
		textureLoader = new TextureLoader();
		soundLoader = new SoundLoader();
		this.setScreen(new MainMenuScreen(this));
	}

	@Override
	public void render () {
		super.render();
	}
	
	@Override
	public void dispose(){
		super.dispose();
		textureLoader.dispose();
		soundLoader.dispose();
		shaper.dispose();
		batch.dispose();
	}
	
	public void newGame(){
		gameScreen = new JashanoidScreen(this);
	}
	
	public JashanoidScreen getGameScreen(){
		return gameScreen;
	}
	
	public SpriteBatch getBatch(){
		return batch;
	}
	
	public ShapeRenderer getShaper(){
		return shaper;
	}
	
	public void updateViewport(int width, int height){
		viewport.setScreenSize(width, height);
	}
	
	public Viewport getViewport(){
		return viewport;
	}
}
