package com.jashlaviu.jashanoid.actors.bonus;

import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.jashlaviu.jashanoid.JashanoidScreen;
import com.jashlaviu.jashanoid.TextureLoader;
import com.jashlaviu.jashanoid.actors.ActorJashanoid;

public class Bonus extends ActorJashanoid {
	
	protected JashanoidScreen gameScreen;
	protected float speed;
	
	public Bonus(JashanoidScreen gameScreen, TextureRegion region, float posX, float posY) {
		super(region);
		setPosition(posX, posY);		
		this.gameScreen = gameScreen;
		
		speed = 100;
		
	}
	
	public void apply(){
		gameScreen.disableBonuses();
		System.out.println("activado");
	}
	
	@Override
	public void act(float delta) {
		super.act(delta);
		moveBy(0, -1 * speed * delta);
		
		if(getY() <= 0){
			gameScreen.isBonus(false);
			this.remove();
		}
			
	}

}
