package com.jashlaviu.jashanoid;

import java.util.ArrayList;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;
import com.jashlaviu.jashanoid.actors.bricks.*;

public class LevelCreator {
	
	private final int BRICK_WIDTH = 55;
	private final int BRICK_HEIGHT = 20;
	
	private final char charNormal;
	private final char charHard;
	
	@SuppressWarnings("unused")
	private final char charSpace;
	
	private ArrayList<Brick> bricks;
	private String levelChars;
	
	public LevelCreator(ArrayList<Brick> bricks) {
		this.bricks = bricks; 
		charNormal = '0';
		charSpace = '_';
		charHard = '*';
	}

	public void setLevel(int level) {	
		
		getLevelChars(level);
		
		int x = Bounds.GAME_X_LEFT;
		int y = Bounds.GAME_Y_UP - BRICK_HEIGHT;
		
		for(int i = 0; i < levelChars.length(); i++){
			char thisChar = levelChars.charAt(i);
			
			if(thisChar == charNormal) addNormal(x ,y);	
			if(thisChar == charHard) addHard(x, y);
			
			if(thisChar == '\n'){
				x = Bounds.GAME_X_LEFT;
				y -= BRICK_HEIGHT;
			}else x += BRICK_WIDTH;
			
		}					
	}
	
	private void getLevelChars(int level){
		String levelString = Integer.toString(level);
		System.out.println("level_" + levelString);
		
		FileHandle levelFile = Gdx.files.internal("levels/level_" + levelString);
		if(levelFile == null)
			levelFile = Gdx.files.internal("levels/level_0");
		
		levelChars = levelFile.readString();
	}
	
	
	private void addNormal(float x, float y){
		bricks.add(new BrickNormal(x, y));
	}
	
	private void addHard(float x, float y){
		bricks.add(new BrickHard(x, y));
	}

}
