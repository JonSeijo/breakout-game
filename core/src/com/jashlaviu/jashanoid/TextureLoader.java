package com.jashlaviu.jashanoid;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.MathUtils;

public class TextureLoader{
	
	public TextureAtlas atlas;
	public static TextureRegion platform, ball, block1, block2, block3;
	public static TextureRegion bonus_01, bonus_02, bonus_03, bonus_04, bonus_05, bonus_06, bonus_07;
	public static TextureRegion brick_01, brick_02, brick_03, brick_11, brick_21, ball_02;
	
	public TextureLoader(){
		atlas = new TextureAtlas(Gdx.files.internal("jashanoidAtlas.atlas"));
		
		platform = load("palete");
		ball = load("ball1");
		ball_02 = load("ball2");
		brick_01 = load("brick", 1);
		brick_02 = load("brick", 2);
		brick_03 = load("brick", 3);
		
		brick_11 = load("brick", 11);
		
		brick_21 = load("brick", 21);
		
		
		bonus_01 = load("bonus", 1);
		bonus_02 = load("bonus", 2);
		bonus_03 = load("bonus", 3);
		bonus_04 = load("bonus", 4);
		bonus_05 = load("bonus", 5);
		bonus_06 = load("bonus", 6);
		bonus_07 = load("bonus", 7);
		

		block1 = load("block1");
		block2 = load("block2");
		block3 = load("block3");	
		
	}
	
	public static TextureRegion getRandomNormal(){
		int rand = MathUtils.random(1, 3);
		if(rand == 1)
			return brick_01;
				
		if(rand == 2)
			return brick_02;
		
		if(rand == 3)
			return brick_03;
		
		
		return null;
	}
	
	public static TextureRegion getBall(){
		return ball_02;
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
