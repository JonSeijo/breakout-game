package com.jashlaviu.jashanoid.inputhandler;

import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.InputAdapter;
import com.jashlaviu.jashanoid.Jashanoid;

public class InputHandler extends InputAdapter{

	private Jashanoid game;
	private Controller control;
	
	public InputHandler(Jashanoid game, Controller control) {
		this.game = game;
		this.control = control;
	}
	
	@Override
	public boolean keyDown(int keycode) {
		if(keycode == Keys.RIGHT){
			control.setPlatformMovingRight(true);
			return true;
		}
		if(keycode == Keys.LEFT){
			control.setPlatformMovingLeft(true);
			return true;
		}
		
		if(game.getScreen() == game.getGameScreen()){
			if(keycode == Keys.SPACE){
				control.shootJustPressed(true);
			}		
			
			if(keycode == Keys.N){
				control.developJustPressed(true);
			}
		}
		
		
		return false;		
	}
	
	@Override
	public boolean keyUp(int keycode) {
		if(keycode == Keys.RIGHT){
			control.setPlatformMovingRight(false);
			return true;
		}	
		if(keycode == Keys.LEFT){
			control.setPlatformMovingLeft(false);
			return true;
		}
		return false;
	}
	
}
