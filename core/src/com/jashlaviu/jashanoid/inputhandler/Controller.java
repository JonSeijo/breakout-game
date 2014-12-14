package com.jashlaviu.jashanoid.inputhandler;

import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.jashlaviu.jashanoid.JashanoidScreen;
import com.jashlaviu.jashanoid.actors.Platform;

public class Controller {
	
	private JashanoidScreen gameScreen;
	private Platform platform;
	
	private boolean platformMovingRight;	
	private boolean platformMovingLeft;
	private boolean shootJustPressed;
	private boolean developJustPressed;
	
	public Controller(JashanoidScreen gameScreen) {
		this.gameScreen = gameScreen;
		platform = gameScreen.getPlatform();
	}
	
	public void update(float delta){		
		if(platformMovingRight){
			platform.addAction(Actions.moveBy(platform.getSpeed() * delta, 0));
			gameScreen.getTakeOff().x += platform.getSpeed() * delta;
			
		}	
		
		if(platformMovingLeft){
			platform.addAction(Actions.moveBy(-platform.getSpeed() * delta, 0));
			gameScreen.getTakeOff().x -= platform.getSpeed() * delta;			
		}
		
		if(shootJustPressed){
			shootJustPressed = false;
			platform.setGlue(false);
		}
		
		if(developJustPressed){
			developJustPressed = false;
			gameScreen.levelUp();
		}
		
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
