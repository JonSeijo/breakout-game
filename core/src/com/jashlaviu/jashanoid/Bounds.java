package com.jashlaviu.jashanoid;

import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.jashlaviu.jashanoid.actors.ActorJashanoid;

/**
 * The values of Bounds are used to control collitions, draws, and level creation.
 * Modification of the values won't break the game, but it could look weird. 
 * 
 * @author jonseijo
 *
 */
public class Bounds {	
	public final static int GAME_X_LEFT = 10;
	public final static int GAME_X_RIGHT = GAME_X_LEFT + 605;
	public final static int GAME_Y_UP = 590;
	public final static int GAME_Y_DOWN = 10;
	
	public final static int SCORE_X_LEFT = GAME_X_RIGHT + 1;
	public final static int SCORE_X_RIGHT = 795;
	public final static int SCORE_Y_UP = 590;
	public final static int SCORE_Y_DOWN = 10;
	
	/**
	 * Draw bounds for debugging 
	 */
	public void draw(ShapeRenderer shaper){
	/*	
	 	shaper.begin(ShapeType.Line);
		
		shaper.line(GAME_X_LEFT, GAME_Y_DOWN, GAME_X_LEFT, GAME_Y_UP, Color.BLACK, Color.BLACK);
		shaper.line(GAME_X_LEFT, GAME_Y_DOWN, GAME_X_RIGHT, GAME_Y_DOWN, Color.BLACK, Color.BLACK);
		shaper.line(GAME_X_LEFT, GAME_Y_UP, GAME_X_RIGHT, GAME_Y_UP, Color.BLACK, Color.BLACK);
		shaper.line(GAME_X_RIGHT, GAME_Y_DOWN, GAME_X_RIGHT, GAME_Y_UP, Color.BLACK, Color.BLACK);
		
		System.out.println(GAME_Y_UP- GAME_Y_DOWN);
		
		shaper.line(SCORE_X_LEFT, SCORE_Y_DOWN, SCORE_X_LEFT, SCORE_Y_UP, Color.BLUE, Color.BLUE);
		shaper.line(SCORE_X_LEFT, SCORE_Y_DOWN, SCORE_X_RIGHT, SCORE_Y_DOWN, Color.BLUE, Color.BLUE);
		shaper.line(SCORE_X_LEFT, SCORE_Y_UP, SCORE_X_RIGHT, SCORE_Y_UP, Color.BLUE, Color.BLUE);
		shaper.line(SCORE_X_RIGHT, SCORE_Y_DOWN, SCORE_X_RIGHT, SCORE_Y_UP, Color.BLUE, Color.BLUE);		
	
		shaper.end();*/
	}
	
	/**  
	 * Returns true if the actor touched or passed the left bounds 
	 */
	public boolean collideLeft(ActorJashanoid actor){
		return(actor.getCollisionBounds().x <= GAME_X_LEFT);
	}	
	
	/**
	 * Returns true if the actor touched or passed the right bounds 
	 */
	public boolean collideRight(ActorJashanoid actor){
		return((actor.getCollisionBounds().x + actor.getCollisionBounds().width) >= GAME_X_RIGHT);
	}
	
	/**
	 * Returns true if the actor touched or passed the up bounds 
	 */
	public boolean collideUp(ActorJashanoid actor){
		return(actor.getCollisionBounds().y + actor.getCollisionBounds().height >= GAME_Y_UP);
	}
	
	/**
	 * Returns true if the actor touched or passed the down bounds 
	 */
	public boolean collideDown(ActorJashanoid actor){
		return(actor.getCollisionBounds().y <= GAME_Y_DOWN);
	}
	

}
