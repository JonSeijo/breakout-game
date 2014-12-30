package com.jashlaviu.jashanoid;

import java.util.ArrayList;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.jashlaviu.jashanoid.actors.bricks.*;

/**
 * Creates the correct bricks in their position and stores the in the "bricks" array,
 * the level is created by reading .txt files in assets/levels.
 * Different characters on the txt represents different types of bricks.
 * The txt must have a max of 11 blocks width, or it wont fit.
 * @author jonseijo
 *
 */
public class LevelCreator {
	
	// The sizes must NOT change. proportions are thinked with these values.
	private final int BRICK_WIDTH = 55;
	private final int BRICK_HEIGHT = 20;
	
	private final char charBlue, charRed, charGreen, charPurple, charOrange;
	private final char charHard, charRock;
		
	private ArrayList<Brick> bricks;
	private String levelChars;
	
	public LevelCreator(ArrayList<Brick> bricks) {
		this.bricks = bricks; 
		
		charBlue = '1';
		charRed = '2';
		charGreen = '3';
		charPurple = '4';
		charOrange = '5';
		
		charHard = '*';
		charRock = '+';
	}

	public void setLevel(int level) {	
		
		levelChars = getLevelChars(level);
		
		int x = Bounds.GAME_X_LEFT;
		int y = Bounds.GAME_Y_UP - BRICK_HEIGHT;
		
		for(int i = 0; i < levelChars.length(); i++){
			char thisChar = levelChars.charAt(i);
			
			//Add normal bricks with different colors
			if(thisChar == charBlue || thisChar == '0') addNormal(TextureLoader.brick_01, x ,y);	
			if(thisChar == charRed) addNormal(TextureLoader.brick_02, x ,y);
			if(thisChar == charGreen) addNormal(TextureLoader.brick_03, x ,y);
			if(thisChar == charPurple) addNormal(TextureLoader.brick_04, x ,y);
			if(thisChar == charOrange) addNormal(TextureLoader.brick_05, x ,y);
			
			//Add hard and indestructible bricks
			if(thisChar == charHard) addHard(x, y);
			if(thisChar == charRock) addRock(x, y);
			
			// new line in text -> new line in game
			if(thisChar == '\n'){
				x = Bounds.GAME_X_LEFT;
				y -= BRICK_HEIGHT;
			}else x += BRICK_WIDTH;
			
		}					
	}
	
	/**
	 * Reads every char in the .txt of the level passed. Returns a String with every char.
	 */
	private String getLevelChars(int level){			
		FileHandle levelFile = Gdx.files.internal("levels/level_" + level);			
		return levelFile.readString();
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
