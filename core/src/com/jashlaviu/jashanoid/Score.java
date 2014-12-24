package com.jashlaviu.jashanoid;

public class Score {	
	
	private JashanoidScreen gameScreen;
	private int points, pointsToLife, pointsToLifeGap;
	
	public static final int BRICK_NORMAL = 100;
	public static final int BRICK_HARD = 300;
	public static final int BONUS = 1000;
	public static final int LEVEL = 5000;
	
	public Score(JashanoidScreen gameScreen) {
		this.gameScreen = gameScreen;
		points = -LEVEL; //To start in 0 when start the first level
		pointsToLifeGap = 20000;
		pointsToLife = pointsToLifeGap;		
	}
	
	public void addPoints(int points){
		this.points += points;
		if(points >= pointsToLife){
			gameScreen.addLife();
			pointsToLife += pointsToLifeGap;
		}
		
		System.out.println(this.points);
	}
	
	public int getPoints(){
		return points;
	}
	
	
	
}
