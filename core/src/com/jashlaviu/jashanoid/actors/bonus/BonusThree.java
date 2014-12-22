package com.jashlaviu.jashanoid.actors.bonus;

import com.badlogic.gdx.math.Vector2;
import com.jashlaviu.jashanoid.JashanoidScreen;
import com.jashlaviu.jashanoid.SoundLoader;
import com.jashlaviu.jashanoid.TextureLoader;
import com.jashlaviu.jashanoid.actors.Ball;

public class BonusThree extends Bonus{

	public BonusThree(JashanoidScreen gameScreen, float posX, float posY) {
		super(gameScreen, TextureLoader.bonus_07, posX, posY);
	}
	
	@Override
	public void apply() {
		super.apply();
		
		SoundLoader.three.play(gameScreen.getSoundVolume());

		Ball oldBall = gameScreen.getBall();
		
		Vector2 dir1 = new Vector2(oldBall.getDirection());
		dir1.rotate(10);
		
		Vector2 dir2 = new Vector2(oldBall.getDirection());
		dir2.rotate(-10);
		
		gameScreen.addBall(new Vector2(oldBall.getPosition()), dir1);
		gameScreen.addBall(new Vector2(oldBall.getPosition()), dir2);
		
		float speed = oldBall.getSpeed();
		
		for(Ball ball : gameScreen.getBalls()){
			ball.setSpeed(speed);
		}
	}

}
