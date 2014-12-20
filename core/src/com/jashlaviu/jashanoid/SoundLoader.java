package com.jashlaviu.jashanoid;

import java.util.ArrayList;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.files.FileHandle;

public class SoundLoader {
	
	private ArrayList<Sound> allSoundsList;
		
	public static Sound ball_bounds;

	public SoundLoader() {		
		allSoundsList = new ArrayList<Sound>();
		
		
		
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
