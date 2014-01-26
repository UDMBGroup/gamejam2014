package edu.virginia.cs.sgd.game;

import com.badlogic.gdx.maps.tiled.TiledMap;

import edu.virginia.cs.sgd.game.controller.Controller;
import edu.virginia.cs.sgd.game.model.Model;

public class Level {
	
	private Model m;
	private Controller c;
	
	public Level(TiledMap map) {
		m = new Model(map);
		c = new Controller();
	}

	public void onKeyPress(int keyCode) {
		
		c.onKeyPress(m, keyCode);
	}
	
	public void update() {
		
		
		
	}
	
	public void render() {
		
	}
}
