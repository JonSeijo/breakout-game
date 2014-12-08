package com.jashlaviu.jashanoid.inputhandler;

import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.jashlaviu.jashanoid.JashanoidScreen;
import com.jashlaviu.jashanoid.actors.Platform;

public class Controller {
	
	private Platform platform;
	
	private boolean platformMovingRight;	
	private boolean platformMovingLeft;
	private boolean shootJustPressed;
	
	public Controller(JashanoidScreen gameScreen) {
		platform = gameScreen.getPlatform();
	}
	
	public void update(float delta){		
		if(platformMovingRight)
			platform.addAction(Actions.moveBy(platform.getSpeed() * delta, 0));
		
		if(platformMovingLeft)
			platform.addAction(Actions.moveBy(-platform.getSpeed() * delta, 0));
		
		if(shootJustPressed){
			shootJustPressed = false;
			platform.setGlue(false);
		}
		
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
