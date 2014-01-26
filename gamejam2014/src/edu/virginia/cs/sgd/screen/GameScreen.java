package edu.virginia.cs.sgd.screen;

import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;

import edu.virginia.cs.sgd.game.Level;
import edu.virginia.cs.sgd.util.SingletonAssetManager;
import edu.virginia.cs.sgd.viewer.Viewer;

public class GameScreen extends AbstractScreen {

	private Level level;
	private Viewer viewer;
	
	public GameScreen() {
		SingletonAssetManager.getInstance().finishLoading();
		
		TiledMap map = SingletonAssetManager.getInstance().get("testmap");

		OrthogonalTiledMapRenderer renderer = new OrthogonalTiledMapRenderer(map, 1);
		viewer = new Viewer(renderer);
		
		level = new Level(map, viewer);
		
	}
	
	@Override
	public void resize(int width, int height) {
		viewer.resize(width, height);
	}
	
	@Override
	public void render(float delta) {
		super.render(delta);
		
		level.update();
		
		level.render();
	}
	
	@Override
	public void keyUp(int keyCode) {
		level.onKeyPress(keyCode);
	}
}
