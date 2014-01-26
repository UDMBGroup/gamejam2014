package edu.virginia.cs.sgd;

import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;

public class Main {
	public static void main(String[] args) {
		LwjglApplicationConfiguration cfg = new LwjglApplicationConfiguration();
		cfg.title = "The Untimely Death of Mr. Bigglesworth";
		cfg.useGL20 = false;
		cfg.width = 480;
		cfg.height = 320;
		cfg.resizable = false;
		
		new LwjglApplication(new Program(), cfg);
	}
}
