package com.jashlaviu.jashanoid;

import menu.MainMenuScreen;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;

public class Jashanoid extends Game {
	private SpriteBatch batch;
	private ShapeRenderer shaper;
	private JashanoidScreen gameScreen;
	private TextureLoader textureLoader;
	
	@Override
	public void create () {
		batch = new SpriteBatch();
		shaper = new ShapeRenderer();
		textureLoader = new TextureLoader();		
		gameScreen = new JashanoidScreen(this);		
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
		shaper.dispose();
		batch.dispose();
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
}
