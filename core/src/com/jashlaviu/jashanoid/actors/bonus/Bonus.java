package com.jashlaviu.jashanoid.actors.bonus;

import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.jashlaviu.jashanoid.JashanoidScreen;
import com.jashlaviu.jashanoid.actors.ActorJashanoid;

/**
 * Parent of the different bonus types. All bonuses moves down.
 * Each bonus child has its own TextureRegion and implements its "apply()"
 * @author jonseijo
 *
 */
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
	}
	
	@Override
	public void act(float delta) {
		super.act(delta);
		moveBy(0, -1 * speed * delta);			
	}

}
