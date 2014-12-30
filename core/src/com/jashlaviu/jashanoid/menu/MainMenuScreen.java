package com.jashlaviu.jashanoid.menu;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.actions.ParallelAction;
import com.badlogic.gdx.scenes.scene2d.actions.SequenceAction;
import com.jashlaviu.jashanoid.Jashanoid;
import com.jashlaviu.jashanoid.SoundLoader;
import com.jashlaviu.jashanoid.TextureLoader;
import com.jashlaviu.jashanoid.actors.ActorJashanoid;

/**
 * Shows the main menu screen for the game.
 * This is my first time trying to implement a Main Menu. IT WORKS!, 
 * but the code is a mess, sorry.
 * @author jonseijo
 *
 */
public class MainMenuScreen extends ScreenAdapter{

	private Jashanoid game;
	private Stage stage;
	private ActorJashanoid jashanoidName, jashlaviuDev;
	private MenuButton playButton, quitButton, soundButton, yesButton, noButton;
	private MenuButton[] menuButtons;
	private Cursor cursor;
	private BitmapFont fontSmall;
	
	private boolean isSound = true;
	private boolean showingOptions = false;
	private boolean drawSelector;
	
	private boolean animationStarted;
	private float animationTime;
	
	public MainMenuScreen(Jashanoid game) {
		this.game = game;
		
		fontSmall = new BitmapFont(Gdx.files.internal("fonts/ShareTechMono-Regular22.fnt"));	
		
		stage = new Stage(game.getViewport(), game.getBatch());
		
		int heightGap = 80;
		
		//Button creation. They are WAY OFF to the left, because they will be animating 
		//towards the center when the game starts
		playButton = new MenuButton(TextureLoader.button_play, -200, 100 + heightGap*2);
		soundButton = new MenuButton(TextureLoader.button_sound, -200, 100 + heightGap);
		quitButton = new MenuButton(TextureLoader.button_quit, -200, 100);
		yesButton = new MenuButton(TextureLoader.button_yes, 530, soundButton.getY()+5);
		noButton = new MenuButton(TextureLoader.button_no, 530, soundButton.getY()+5);
		
		jashanoidName = new ActorJashanoid(TextureLoader.jashanoid);
		jashanoidName.setPosition(-350, 400);
		
		jashlaviuDev = new ActorJashanoid(TextureLoader.jashlaviu);
		jashlaviuDev.setPosition(-490, 10);
		
		menuButtons = new MenuButton[] {playButton, soundButton, quitButton};
		
		//Create cursor in postion of button n°1 
		cursor = new Cursor(playButton.getX() - 90, playButton.getY() - 10, 3, heightGap);
		
		stage.addActor(cursor);	
		stage.addActor(playButton);
		stage.addActor(soundButton);
		stage.addActor(quitButton);	
		stage.addActor(jashanoidName);
		stage.addActor(jashlaviuDev);

		//Make the move animation to the center. 
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
		
		//Draw backgroud.
		for(int y = 0; y < stage.getHeight(); y += 116)
			for(int x = 0; x < stage.getWidth(); x += 121)
				game.getBatch().draw(TextureLoader.back_gui, x, y);			
		
		//Draw selector to the JashLaviu button if needed.
		if(drawSelector){
			game.getBatch().draw(TextureLoader.jashlaviu_selector, jashlaviuDev.getX()-2, jashlaviuDev.getY()-2);
		}
		
		// Version
		fontSmall.draw(game.getBatch(), "1.0", 750, 35);	
		
		game.getBatch().end();

		//Move down the cursor
		if(Gdx.input.isKeyJustPressed(Keys.DOWN)){
			SoundLoader.platform_ball.play(SoundLoader.soundVolume);
			setShowingOptions(false);
			cursor.moveDown();		
		}
		
		//Move up the cursor
		if(Gdx.input.isKeyJustPressed(Keys.UP)){
			SoundLoader.platform_ball.play(SoundLoader.soundVolume);
			setShowingOptions(false);
			cursor.moveUp();
		}
		
		//Press the button the cursor is currently on
		if(Gdx.input.isKeyJustPressed(Keys.ENTER) || Gdx.input.isKeyJustPressed(Keys.SPACE)){
			int index = cursor.getIndex();
			
			if(menuButtons[index] == playButton){
				SoundLoader.menu.play(SoundLoader.soundVolume);
				startOutAnimation();	//Play an outAnimation before stating			
			}
			
			if(menuButtons[index] == soundButton){
				handleSoundOptions();	//Toggles the YES-NO options			
			}				
			
			if(menuButtons[index] == quitButton){
				Gdx.app.exit();	//Exit the app.
			}
		}
		
		//The YES-NO option in sound can alse be toggled by arrows, if cursor is on "sound" 
		if(Gdx.input.isKeyJustPressed(Keys.RIGHT) || Gdx.input.isKeyJustPressed(Keys.LEFT)){
			int index = cursor.getIndex();
			if(menuButtons[index] == soundButton){				
				handleSoundOptions();
			}
		}
		
		//If click on JashLaviu logo, go to the twitter page ;) 
		if(jashlaviuDev.getCollisionBounds().contains(Gdx.input.getX(), 
				game.getViewport().getScreenHeight() - Gdx.input.getY())){
			
			drawSelector = true;			
			if(Gdx.input.justTouched())		
				Gdx.net.openURI("https://twitter.com/jashlaviu");			
			
		}else drawSelector = false;
		
		//Start a new game when the fade-out animation ends.
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
	
	/**
	 * Toggles between yes and no, adding and removing buttons.
	 */
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
	
	/**
	 * Start the out animation before starting a new game.
	 */
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
