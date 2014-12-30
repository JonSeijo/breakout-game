package com.jashlaviu.jashanoid;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Sound;

/**
 * This class loads and stores every sound in the game.
 * The sounds are public and static for easy acces through every class in the game.
 * Also, it provides an easy dispose method 
 * (not so true with sounds, 
 * but it does with its brother "TextureLoader")                
 *  
 * FUN FACT: ALL the sounds in this game, were created using the same audio file as base.
 * Check credits.txt ! 
 */
public class SoundLoader {
	
	public static float soundVolume = 1f;
	public static Sound ball_bounds, ball_brick_normal, ball_brick_hard;
	public static Sound platform_ball;
	public static Sound level, life, slow, three, glue, expand, menu, lostLive;	

	public SoundLoader() {			
		ball_bounds = load("bertrof2");		
		ball_brick_normal = load("bertrof4");
		ball_brick_hard = load("bertrof3");
		
		platform_ball = load("bertrof6");		
		lostLive = load("bertrof_lostlive");
		
		level = load("bertrof_level");
		life = load("bertrof_life");
		slow = load("bertrof_slow");
		three = load("bertrof_three");
		glue = load("bertrof_glue");
		expand = load("bertrof_expand");
		
		menu = load("bertrof_menu");		
		
	}
	
	/**
	 * Disposes every loaded sound.
	 */
	public void dispose(){
		ball_bounds.dispose();	
		ball_brick_normal.dispose();
		ball_brick_hard.dispose();;		
		platform_ball.dispose();	
		lostLive.dispose();		
		level.dispose();
		life.dispose();
		slow.dispose();
		three.dispose();
		glue.dispose();
		expand.dispose();;		
		menu.dispose();	
	}
	
	/**
	 * Loads the sound with the given nave (assumming .wav)
	 */
	private Sound load(String path){		
		Sound sound = Gdx.audio.newSound(Gdx.files.internal("sound/" + path + ".wav"));
		return sound;
	}

}
