package com.jashlaviu.jashanoid.menu;


import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.actions.ParallelAction;
import com.badlogic.gdx.scenes.scene2d.actions.SequenceAction;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.jashlaviu.jashanoid.Jashanoid;
import com.jashlaviu.jashanoid.SoundLoader;
import com.jashlaviu.jashanoid.TextureLoader;

public class MainMenuScreen extends ScreenAdapter{

	private Jashanoid game;
	private Stage stage;
	private MenuButton playButton, quitButton, soundButton, yesButton, noButton;
	private MenuButton[] menuButtons;
	private Cursor cursor;
	
	private boolean isSound = true;
	private boolean showingOptions = false;
	
	
	private boolean animationStarted;
	private float animationTime;
	
	public MainMenuScreen(Jashanoid game) {
		this.game = game;
		
		stage = new Stage(game.getViewport(), game.getBatch());
		
		playButton = new MenuButton(TextureLoader.button_play, -200, 300);
		soundButton = new MenuButton(TextureLoader.button_sound, -200, 200);
		quitButton = new MenuButton(TextureLoader.button_quit, -200, 100);
		yesButton = new MenuButton(TextureLoader.button_yes, 530, 205);
		noButton = new MenuButton(TextureLoader.button_no, 530, 205);
		
		menuButtons = new MenuButton[] {playButton, soundButton, quitButton};
		
		cursor = new Cursor(playButton.getX() - 90, playButton.getY() - 10, 3, 100);
		
		stage.addActor(cursor);	
		stage.addActor(playButton);
		stage.addActor(soundButton);
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
			setShowingOptions(false);
			cursor.moveDown();		
		}
		
		if(Gdx.input.isKeyJustPressed(Keys.UP)){
			SoundLoader.platform_ball.play(SoundLoader.soundVolume);
			setShowingOptions(false);
			cursor.moveUp();
		}
		
		if(Gdx.input.isKeyJustPressed(Keys.ENTER)){
			int index = cursor.getIndex();
			
			if(menuButtons[index] == playButton){
				SoundLoader.menu.play(SoundLoader.soundVolume);
				startOutAnimation();				
			}
			
			if(menuButtons[index] == soundButton){
				handleSoundOptions();				
			}				
			
			if(menuButtons[index] == quitButton){
				Gdx.app.exit();	
			}
		}
		
		if(Gdx.input.isKeyJustPressed(Keys.RIGHT) || Gdx.input.isKeyJustPressed(Keys.LEFT)){
			int index = cursor.getIndex();
			if(menuButtons[index] == soundButton){				
				handleSoundOptions();
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
	
	private void setShowingOptions(boolean bool){
		showingOptions = bool;
		if(!showingOptions){
			if(isSound) yesButton.remove();
			else noButton.remove();
		}
		
	}
	
	private void handleSoundOptions(){
		if(showingOptions){		
			if(isSound){
				yesButton.remove();
				stage.addActor(noButton);
				isSound = false;
				SoundLoader.soundVolume = 0;
			}
			else{				
				noButton.remove();
				stage.addActor(yesButton);
				isSound = true;
				SoundLoader.soundVolume = 1f;
				SoundLoader.menu.play(SoundLoader.soundVolume);
			}
		}
		if(!showingOptions){
			setShowingOptions(true);
			if(isSound) {
				SoundLoader.menu.play(SoundLoader.soundVolume);
				stage.addActor(yesButton);
			}
			else stage.addActor(noButton);
		
		}
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
		game.updateViewport(width, height);
		stage.getViewport().update(game.getViewport().getScreenWidth(), game.getViewport().getScreenHeight());
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
