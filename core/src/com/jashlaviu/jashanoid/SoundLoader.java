package com.jashlaviu.jashanoid;

import java.util.ArrayList;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.files.FileHandle;

public class SoundLoader {
	
	private ArrayList<Sound> allSoundsList;
	
	public static float soundVolume = 1f;
	public static Sound ball_bounds, ball_brick_normal, platform_bonus, platform_ball;
	

	public SoundLoader() {		
		allSoundsList = new ArrayList<Sound>();
		
		ball_bounds = load("ball_bounds");
		ball_brick_normal = load("ball_brick");
		platform_ball = load("platform_ball");
		platform_bonus = load("platform_bonus");
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
