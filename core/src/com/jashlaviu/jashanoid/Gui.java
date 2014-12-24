package com.jashlaviu.jashanoid;


import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

public class Gui {
	
	private Score score;
	private JashanoidScreen gameScreen;
	private TextureRegion livesRegion;
	private BitmapFont font;
	
	public Gui(JashanoidScreen gameScreen) {
		this.gameScreen = gameScreen;
		this.score = gameScreen.getScore();
		livesRegion = TextureLoader.getPlatform();
		
		font = new BitmapFont();		
	}
	
	public void render(SpriteBatch batch){
		font.draw(batch, "Score: ", Bounds.SCORE_X_LEFT + 60, 560);
		font.draw(batch, ""+score.getPoints(), Bounds.SCORE_X_LEFT + 60, 540);
		
		font.draw(batch, "Extra life at: ", Bounds.SCORE_X_LEFT + 60, 500);
		font.draw(batch, ""+score.getPointsToLife(), Bounds.SCORE_X_LEFT + 60, 480);
		
		font.draw(batch, "Level:   " + gameScreen.getLevel(), Bounds.SCORE_X_LEFT + 60, 120);
		
		for(int i = 1; i < gameScreen.getLives(); i++){
			batch.draw(livesRegion, Bounds.GAME_X_RIGHT + 45, 400 - i * 30);
		}
	}
	
	public void dispose(){
		font.dispose();
	}

}
