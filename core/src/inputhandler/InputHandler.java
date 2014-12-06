package inputhandler;

import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.InputAdapter;

public class InputHandler extends InputAdapter{

	private Controller control;
	
	public InputHandler(Controller control) {
		this.control = control;
	}
	
	@Override
	public boolean keyDown(int keycode) {
		if(keycode == Keys.RIGHT){
			control.setPlatformMovingRight(true);
			return true;
		}
		if(keycode == Keys.LEFT){
			control.setPlatformMovingLeft(true);
			return true;
		}
		
		if(keycode == Keys.SPACE){
			control.shootJustPressed(true);
		}
		
		return false;		
	}
	
	@Override
	public boolean keyUp(int keycode) {
		if(keycode == Keys.RIGHT){
			control.setPlatformMovingRight(false);
			return true;
		}	
		if(keycode == Keys.LEFT){
			control.setPlatformMovingLeft(false);
			return true;
		}
		return false;
	}
	
}
