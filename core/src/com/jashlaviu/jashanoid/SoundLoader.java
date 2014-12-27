package com.jashlaviu.jashanoid;

import java.util.ArrayList;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Sound;

public class SoundLoader {
	
	private ArrayList<Sound> allSoundsList;
	
	public static float soundVolume = 1f;
	public static Sound ball_bounds, ball_brick_normal, ball_brick_hard;
	public static Sound platform_ball;
	public static Sound level, life, slow, three, glue, expand, menu, lostLive;	

	public SoundLoader() {		
		allSoundsList = new ArrayList<Sound>();
		
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
	
	public void dispose(){
		for(Sound sound : allSoundsList)
			sound.dispose();
	}
	
	private Sound load(String path){		
		Sound sound = Gdx.audio.newSound(Gdx.files.internal("sound/" + path + ".wav"));
		allSoundsList.add(sound);
		return sound;
	}

}
