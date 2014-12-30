package com.jashlaviu.jashanoid.menu;

import com.jashlaviu.jashanoid.TextureLoader;
import com.jashlaviu.jashanoid.actors.ActorJashanoid;

/**
 * Grey TextureRegion that shows the user which button is selecting.
 * It takes the button sizes and quantities, and moves between them.
 * @author jonseijo
 *
 */
public class Cursor extends ActorJashanoid{
	
	private int index, maxButtons, buttonHeight;
	private float initialX, initialY;
	
	public Cursor(float x, float y, int maxButons, int buttonHeight) {
		super(TextureLoader.cursor_2);
		initialX = x;
		initialY = y;
		setPosition(initialX, initialY);  //First button position
		
		this.maxButtons = maxButons;
		this.buttonHeight = buttonHeight;
	}	
	
	/**
	 * If index = 0 --> first button --> cursor has initial position
	 * If index = 1 --> second button --> cursor has initial position MINUS (buttonHeigh * index)
	 * Index represents the number of the button, and is the multiplier of the height.
	 */
	public void moveDown(){
		index++;
		if(index >= maxButtons)
			index = 0;	
		
		setY(initialY - (buttonHeight * index));	
	}
	
	/**
	 * Idem ModeDown
	 */
	public void moveUp(){
		index--;
		if(index < 0)
			index = maxButtons -1;
		
		setY(initialY - (buttonHeight * index));
	}
	
	public int getIndex(){
		return index;
	}
	
	
	
	

}
