package com.jashlaviu.jashanoid.menu;


import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Graphics.DisplayMode;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.actions.ParallelAction;
import com.badlogic.gdx.scenes.scene2d.actions.SequenceAction;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.jashlaviu.jashanoid.Bounds;
import com.jashlaviu.jashanoid.Jashanoid;
import com.jashlaviu.jashanoid.SoundLoader;
import com.jashlaviu.jashanoid.TextureLoader;

public class MainMenuScreen extends ScreenAdapter{

	private Jashanoid game;
	private Stage stage;
	private MenuButton playButton, quitButton, optionsButton;
	private MenuButton[] menuButtons;
	private Cursor cursor;
	
	private boolean animationStarted;
	private float animationTime;
	
	public MainMenuScreen(Jashanoid game) {
		this.game = game;
		
		stage = new Stage(new FitViewport(Gdx.graphics.getWidth(), 
				Gdx.graphics.getHeight()), game.getBatch());
		
		playButton = new MenuButton(TextureLoader.button_play, -200, 400);
		optionsButton = new MenuButton(TextureLoader.button_options, -200, 300);
		quitButton = new MenuButton(TextureLoader.button_quit, -200, 200);
		
		menuButtons = new MenuButton[] {playButton, optionsButton, quitButton};
		
		cursor = new Cursor(playButton.getX() - 90, playButton.getY() - 10, 3, 100);
		
		stage.addActor(cursor);	
		stage.addActor(playButton);
		stage.addActor(optionsButton);
		stage.addActor(quitButton);	

		
		for(Actor actors : stage.getActors()){
			SequenceAction seq = new SequenceAction();
			seq.addAction(Actions.fadeOut(0));
			seq.addAction(Actions.moveBy(550, 0, 0.4f));
			seq.addAction(Actions.moveBy(-50, 0, 0.1f));
			
			ParallelAction par = new ParallelAction();
			par.addAction(seq);
			par.addAction(Actions.fadeIn(0.4f));
			
			actors.addAction(par);
		}
			
	}
	
	@Override
	public void render(float delta) {
		Gdx.gl.glClearColor(0, 0, 0, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		
		game.getBatch().begin();
		for(int y = 0; y < stage.getHeight(); y += 116)
			for(int x = 0; x < stage.getWidth(); x += 121)
				game.getBatch().draw(TextureLoader.back_gui, x, y);			
	
		game.getBatch().end();

		
		if(Gdx.input.isKeyJustPressed(Keys.DOWN)){
			SoundLoader.platform_ball.play(SoundLoader.soundVolume);
			cursor.moveDown();		
		}
		
		if(Gdx.input.isKeyJustPressed(Keys.UP)){
			SoundLoader.platform_ball.play(SoundLoader.soundVolume);
			cursor.moveUp();
		}
		
		if(Gdx.input.isKeyJustPressed(Keys.ENTER)){
			int index = cursor.getIndex();
			
			if(menuButtons[index] == playButton){
				SoundLoader.menu.play(SoundLoader.soundVolume);
				startOutAnimation();				
			}
			
			if(menuButtons[index] == optionsButton){
				SoundLoader.menu.play(SoundLoader.soundVolume);
			}				
			
			if(menuButtons[index] == quitButton){
				Gdx.app.exit();	
			}
		}
		
		if(animationStarted){
			animationTime += delta;
			if(animationTime >= 0.6f){
				game.newGame();
				game.setScreen(game.getGameScreen());
			}
			
		}
			
		stage.act();
		stage.draw();	
	}
	
	private void startOutAnimation(){
		for(Actor actors : stage.getActors()){
			SequenceAction seq = new SequenceAction();
			seq.addAction(Actions.fadeOut(0));
			seq.addAction(Actions.moveBy(-50, 0, 0.1f));
			seq.addAction(Actions.moveBy(600, 0, 0.4f));
			
			ParallelAction par = new ParallelAction();
			par.addAction(seq);
			par.addAction(Actions.fadeIn(0.4f));
			
			actors.addAction(par);
			
			animationStarted = true;
		}	
	}
	
	
	@Override
	public void resize(int width, int height) {
		stage.getViewport().update(width, height);
	}
	
	@Override
	public void hide() {
		this.dispose();
	}
	
	@Override
	public void dispose() {
		stage.dispose();
	}
	
}
