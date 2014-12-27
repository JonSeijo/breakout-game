package com.jashlaviu.jashanoid;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Preferences;

public class Score {	
	
	private JashanoidScreen gameScreen;
	private int points, pointsToLife, pointsToLifeGap, hiScore;
	private Preferences prefs;
	
	public static final int BRICK_NORMAL = 100;
	public static final int BRICK_HARD = 300;
	public static final int BONUS = 1000;
	public static final int LEVEL = 5000;
	
	
	public Score(JashanoidScreen gameScreen) {
		this.gameScreen = gameScreen;
		points = -LEVEL; //To start in 0 when start the first level
		pointsToLifeGap = 20000;
		pointsToLife = pointsToLifeGap;		
		
		prefs = Gdx.app.getPreferences("jashlaviu.jashanoid.preferences.score");
	}
	
	public void addPoints(int points){
		this.points += points;
		if(this.points >= pointsToLife){
			gameScreen.addLife();
			pointsToLife += pointsToLifeGap;
		}		
	}	
	
	public void resetHiScore(){
		prefs.putInteger("hiscore", 0);
		prefs.flush();
		hiScore = prefs.getInteger("hiscore");
	}
	
	public void setHiScore(int score){
		prefs.putInteger("hiscore", score);
		prefs.flush();
		hiScore = prefs.getInteger("hiscore");
	}
	
	public int getHiScore(){
		return hiScore;
	}
	
	public int getPoints(){
		return points;
	}
	
	public int getPointsToLife(){
		return pointsToLife;
	}
	
	
	
}
