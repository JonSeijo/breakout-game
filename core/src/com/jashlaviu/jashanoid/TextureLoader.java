package com.jashlaviu.jashanoid;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;

public class TextureLoader {
	
	public static Texture platform, ball, block1, block2, block3;
	
	public TextureLoader(){
		platform = load("palete.png");
		ball = load("ball1.png");
		block1 = load("block1.png");
		block2 = load("block2.png");
		block3 = load("block3.png");	
	}
	
	public void dispose(){
		platform.dispose();
		ball.dispose();
		block1.dispose();
		block2.dispose();
		block3.dispose();
	}
	
	public Texture load(String path){
		return new Texture(Gdx.files.internal(path));
	}

}
