package com.jashlaviu.jashanoid;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

public class TextureLoader{
	
	public TextureAtlas atlas;
	public static TextureRegion platform, ball, back_blue, back_gui, back_green, back_red, back_violet, back_yellow;
	public static TextureRegion bonus_expand, bonus_glue, bonus_level, bonus_life, bonus_slow, bonus_three;
	public static TextureRegion brick_01, brick_02, brick_03, brick_04, brick_05, brick_11, brick_12, brick_21, ball_02;
	public static TextureRegion hearth, platform_02, platform_03, platform_04, platform_05;
	public static TextureRegion jashanoid, game_over, cursor, cursor_2, trophy, jashlaviu, jashlaviu_selector;
	public static TextureRegion button_play, button_quit, button_options, button_sound, button_yes, button_no;
	
	/**
	 * This class loads and stores every texture in the game, using a Texture Atlas
	 * The regions are public and static for easy acces through every class in the game.
	 * Using regions and Texture Atlas means there is only one .png loaded (the atlas)
	 * The dispose is called in main class Jashanoid
	 * 
	 */
	public TextureLoader(){
		atlas = new TextureAtlas(Gdx.files.internal("jashanoidAtlas.atlas"));
		
		back_gui = load("back", 4);
		back_blue = load("back_blue");
		back_green = load("back_green");
		back_red = load("back_red");
		back_violet = load("back_violet");
		back_yellow = load("back_yellow");
		
		ball = load("ball1");
		ball_02 = load("ball2");
		
		brick_01 = load("brick", 1);
		brick_02 = load("brick", 2);
		brick_03 = load("brick", 3);
		brick_04 = load("brick", 4);
		brick_05 = load("brick", 5);
		
		brick_11 = load("brick", 11);
		brick_12 = load("brick", 12);
		
		brick_21 = load("brick", 21);		
		
		bonus_expand = load("bonus_expand", 1);
		bonus_glue = load("bonus_glue", 1);
		bonus_level = load("bonus_level", 1);
		bonus_life = load("bonus_life", 1);
		bonus_slow = load("bonus_slow", 1);
		bonus_three = load("bonus_three", 1);
		
		platform_04 = load("platform", 6);
		platform_05 = load("platform", 7);
		
		game_over = load("gameOver");	
		jashanoid = load("jashanoid");
			
		cursor = load("menu_cursor");
		cursor_2 = load("cursor", 2);
		
		button_play = load("button_play2");
		button_quit = load("button_quit2");
		button_sound = load("button_sound2");
		button_yes = load("button_yes");
		button_no = load("button_no");
		
		trophy = load("trophy");
		jashlaviu = load("jashlaviu_mini");
		jashlaviu_selector = load("jashlaviu_selector");
		
	}
	
	public static TextureRegion getBall(){
		return ball_02;
	}
	
	public static TextureRegion getPlatform(){
		return platform_04;
	}
	
	public static TextureRegion getDownPlatform(){
		return platform_05;
	}
	
	public void dispose(){
		atlas.dispose();
	}
	
	private TextureRegion load(String name){
		return atlas.findRegion(name);
	}
	
	private TextureRegion load(String name, int index){
		return atlas.findRegion(name, index);
	}

}
