package com.jashlaviu.jashanoid.desktop;

import com.badlogic.gdx.Files.FileType;
import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import com.jashlaviu.jashanoid.Jashanoid;

public class DesktopLauncher {
	public static void main (String[] arg) {
		LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();
		config.width = 800;
		config.height = 600;
		config.resizable = false;
		config.addIcon("icon_window.png", FileType.Internal);
		new LwjglApplication(new Jashanoid(), config);
	}
}
