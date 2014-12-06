package com.jashlaviu.jashanoid;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer.ShapeType;
import com.jashlaviu.jashanoid.actors.ActorJashanoid;

public class Bounds {	
	public static int GAME_X_LEFT = 10;
	public static int GAME_X_RIGHT = 615;
	public static int GAME_Y_UP = 590;
	public static int GAME_Y_DOWN = 10;
	
	public static int SCORE_X_LEFT = GAME_X_RIGHT + 1;
	public static int SCORE_X_RIGHT = 795;
	public static int SCORE_Y_UP = 590;
	public static int SCORE_Y_DOWN = 10;
	
	public Bounds(){
		
	}
	
	public void draw(ShapeRenderer shaper){
		shaper.begin(ShapeType.Line);
		
		shaper.line(GAME_X_LEFT, GAME_Y_DOWN, GAME_X_LEFT, GAME_Y_UP, Color.BLACK, Color.BLACK);
		shaper.line(GAME_X_LEFT, GAME_Y_DOWN, GAME_X_RIGHT, GAME_Y_DOWN, Color.BLACK, Color.BLACK);
		shaper.line(GAME_X_LEFT, GAME_Y_UP, GAME_X_RIGHT, GAME_Y_UP, Color.BLACK, Color.BLACK);
		shaper.line(GAME_X_RIGHT, GAME_Y_DOWN, GAME_X_RIGHT, GAME_Y_UP, Color.BLACK, Color.BLACK);
		
		shaper.line(SCORE_X_LEFT, SCORE_Y_DOWN, SCORE_X_LEFT, SCORE_Y_UP, Color.BLUE, Color.BLUE);
		shaper.line(SCORE_X_LEFT, SCORE_Y_DOWN, SCORE_X_RIGHT, SCORE_Y_DOWN, Color.BLUE, Color.BLUE);
		shaper.line(SCORE_X_LEFT, SCORE_Y_UP, SCORE_X_RIGHT, SCORE_Y_UP, Color.BLUE, Color.BLUE);
		shaper.line(SCORE_X_RIGHT, SCORE_Y_DOWN, SCORE_X_RIGHT, SCORE_Y_UP, Color.BLUE, Color.BLUE);		
		
		shaper.end();
	}
	
	public boolean collideLeft(ActorJashanoid actor){
		return(actor.getCollisionBounds().x <= GAME_X_LEFT);
	}	
	public boolean collideRight(ActorJashanoid actor){
		return((actor.getCollisionBounds().x + actor.getCollisionBounds().width) >= GAME_X_RIGHT);
	}
	public boolean collideUp(ActorJashanoid actor){
		return(actor.getCollisionBounds().y + actor.getCollisionBounds().height >= GAME_Y_UP);
	}
	public boolean collideDown(ActorJashanoid actor){
		return(actor.getCollisionBounds().y <= GAME_Y_DOWN);
	}
	

}
