package com.jashlaviu.jashanoid;

import java.util.ArrayList;

import com.jashlaviu.jashanoid.actors.bricks.*;

public class LevelCreator {
	
	public LevelCreator() {
		
	}

	public static void setLevel(ArrayList<Brick> bricks, int level) {
		
		bricks.add(new BrickNormal(100f, 500f));	
		bricks.add(new BrickNormal(155f, 500f));	
		bricks.add(new BrickNormal(305f, 500f));	
		bricks.add(new BrickNormal(360f, 500f));
		bricks.add(new BrickNormal(415f, 500f));
		bricks.add(new BrickNormal(470f, 500f));
		bricks.add(new BrickNormal(525f, 500f));
		
		bricks.add(new BrickHard(400f, 400f));
		bricks.add(new BrickHard(300f, 400f));
		bricks.add(new BrickHard(350f, 450f));
		
	}

}
