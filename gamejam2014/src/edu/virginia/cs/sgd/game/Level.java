package edu.virginia.cs.sgd.game;

import com.badlogic.gdx.maps.tiled.TiledMap;

import edu.virginia.cs.sgd.game.controller.Controller;
import edu.virginia.cs.sgd.game.model.Model;
import edu.virginia.cs.sgd.util.Point;
import edu.virginia.cs.sgd.viewer.Viewer;

public class Level {

	private Model m;
	private Controller c;
	private Viewer v;

	public Level(TiledMap map, Viewer viewer) {
		m = new Model(map);
		c = new Controller(m.getNextPlayer());
		v = viewer;
	}

	public void onKeyPress(int keyCode) {

		c.onKeyPress(m, keyCode);
	}

	public void update() {

	}

	public void render() {

		String character = c.getActiveCharacter();
		Point pos = m.getPosOfCharacter(character);

		v.updateCamera(pos);
		v.renderView(m);

	}
}
