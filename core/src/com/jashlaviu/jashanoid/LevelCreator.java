package com.jashlaviu.jashanoid;

import java.util.ArrayList;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.utils.GdxRuntimeException;
import com.jashlaviu.jashanoid.actors.bricks.*;

public class LevelCreator {
	
	private final int BRICK_WIDTH = 55;
	private final int BRICK_HEIGHT = 20;
	
	private final char charBlue, charRed, charGreen, charPurple, charOrange;
	private final char charHard;
	private final char charRock;
	
	@SuppressWarnings("unused")
	private final char charSpace;
	
	private ArrayList<Brick> bricks;
	private String levelChars;
	
	public LevelCreator(ArrayList<Brick> bricks) {
		this.bricks = bricks; 
		
		charBlue = '1';
		charRed = '2';
		charGreen = '3';
		charPurple = '4';
		charOrange = '5';
		
		charSpace = '_';
		charHard = '*';
		charRock = '+';
	}

	public void setLevel(int level) {	
		
		getLevelChars(level);
		
		int x = Bounds.GAME_X_LEFT;
		int y = Bounds.GAME_Y_UP - BRICK_HEIGHT;
		
		for(int i = 0; i < levelChars.length(); i++){
			char thisChar = levelChars.charAt(i);
			
			if(thisChar == charBlue || thisChar == '0') addNormal(TextureLoader.brick_01, x ,y);	
			if(thisChar == charRed) addNormal(TextureLoader.brick_02, x ,y);
			if(thisChar == charGreen) addNormal(TextureLoader.brick_03, x ,y);
			if(thisChar == charPurple) addNormal(TextureLoader.brick_04, x ,y);
			if(thisChar == charOrange) addNormal(TextureLoader.brick_05, x ,y);
			
			if(thisChar == charHard) addHard(x, y);
			if(thisChar == charRock) addRock(x, y);
			
			if(thisChar == '\n'){
				x = Bounds.GAME_X_LEFT;
				y -= BRICK_HEIGHT;
			}else x += BRICK_WIDTH;
			
		}					
	}
	
	private void getLevelChars(int level){			
		FileHandle levelFile = Gdx.files.internal("levels/level_" + level);	
		
		levelChars = levelFile.readString();
	}
	
	
	private void addNormal(TextureRegion brickRegion, float x, float y){
		bricks.add(new BrickNormal(brickRegion, x, y));
	}
	
	private void addHard(float x, float y){
		bricks.add(new BrickHard(x, y));
	}
	
	private void addRock(float x, float y){
		bricks.add(new BrickRock(x, y));
	}

}
