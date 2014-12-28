package com.jashlaviu.jashanoid;


import com.badlogic.gdx.Gdx;
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
		
		font = new BitmapFont(Gdx.files.internal("fonts/ShareTechMono-Regular22.fnt"));		
	}
	
	public void render(SpriteBatch batch){
		
		int leftMargin = 60;
		
		batch.begin();
		
		font.draw(batch, "Score", Bounds.SCORE_X_LEFT + 60, 580);
		font.draw(batch, ""+score.getPoints(), Bounds.SCORE_X_LEFT + leftMargin, 550);
		
		font.draw(batch, "Extra life at", Bounds.SCORE_X_LEFT + 10, 500);
		font.draw(batch, ""+score.getPointsToLife(), Bounds.SCORE_X_LEFT + leftMargin, 470);
		
		font.draw(batch, "Level", Bounds.SCORE_X_LEFT + leftMargin, 120);
		font.draw(batch, ""+gameScreen.getLevel(), Bounds.SCORE_X_LEFT + 80, 90);
		
		for(int i = 1; i < gameScreen.getLives(); i++){
			batch.draw(livesRegion, Bounds.GAME_X_RIGHT + 45, 400 - i * 30);
		}
		
		
		batch.end();
	}
	
	public void dispose(){
		font.dispose();
	}

}
