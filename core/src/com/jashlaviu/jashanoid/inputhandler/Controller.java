package com.jashlaviu.jashanoid.inputhandler;

import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.jashlaviu.jashanoid.Bounds;
import com.jashlaviu.jashanoid.Jashanoid;
import com.jashlaviu.jashanoid.JashanoidScreen;
import com.jashlaviu.jashanoid.PauseScreen;
import com.jashlaviu.jashanoid.actors.Platform;

/**
 * Translates directed pressed input in game logic.
 * @author jonseijo
 *
 */
public class Controller {
		
	private Jashanoid game;
	private JashanoidScreen gameScreen;
	private Platform platform;
	
	private boolean platformMovingRight;	
	private boolean platformMovingLeft;
	private boolean shootJustPressed;
	private boolean developJustPressed;
	
	public Controller(Jashanoid game, JashanoidScreen gameScreen) {
		this.game = game;
		this.gameScreen = gameScreen;
		platform = gameScreen.getPlatform();
	}
	
	public void update(float delta){		
		if(platformMovingRight){
			//If the key 'order' is to move right, don't move right if collides.
			if(platform.getRight() < Bounds.GAME_X_RIGHT){
				platform.addAction(Actions.moveBy(platform.getSpeed() * delta, 0));
				gameScreen.getTakeOff().x += platform.getSpeed() * delta;
			}else{
				// Move the platform to the right
				platform.setPosition(Bounds.GAME_X_RIGHT - platform.getWidth(), platform.getY());
			}
			
		}	
		
		if(platformMovingLeft){
			//If the key 'order' is to move left, don't move left if collides.
			if(platform.getX() > Bounds.GAME_X_LEFT){
				platform.addAction(Actions.moveBy(-platform.getSpeed() * delta, 0));
				gameScreen.getTakeOff().x -= platform.getSpeed() * delta;
			}else{
				// Move the platform to the left
				platform.setPosition(Bounds.GAME_X_LEFT, platform.getY());
			}
		}	

		//Unglue
		if(shootJustPressed){
			shootJustPressed = false;
			platform.setGlue(false);
		}
		
		//Used for debugging
		if(developJustPressed){
			developJustPressed = false;
			
			
		}
		
		
	}
	
	public void pause(){
		game.setScreen(new PauseScreen(game, gameScreen));
	}
	
	public void unpause(){
		game.setScreen(game.getGameScreen());
	}
	
	public void developJustPressed(boolean bool){
		developJustPressed = bool;
	}
	
	public void setPlatformMovingRight(boolean bool){
		platformMovingRight = bool;
	}
	
	public void setPlatformMovingLeft(boolean bool){
		platformMovingLeft = bool;
	}

	public void shootJustPressed(boolean bool) {
		shootJustPressed = bool;
		
	}
	

}
