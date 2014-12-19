package menu;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.jashlaviu.jashanoid.TextureLoader;
import com.jashlaviu.jashanoid.actors.ActorJashanoid;

public class Cursor extends ActorJashanoid{
	
	private int index, maxButtons, buttonHeight;
	private float initialX, initialY;
	
	public Cursor(float x, float y, int maxButons, int buttonHeight) {
		super(TextureLoader.cursor);
		initialX = x;
		initialY = y;
		setPosition(initialX, initialY);
		
		this.maxButtons = maxButons;
		this.buttonHeight = buttonHeight;
	}	
	
	public void moveDown(){
		index++;
		if(index >= maxButtons)
			index = 0;	
		
		setY(initialY - (buttonHeight * index));	
		System.out.println(index);
	}
	
	public void moveUp(){
		index--;
		if(index < 0)
			index = maxButtons -1;
		
		setY(initialY - (buttonHeight * index));
		System.out.println(index);
	}
	
	public int getIndex(){
		return index;
	}
	
	
	
	

}
