package edu.virginia.cs.sgd.screen;

import edu.virginia.cs.sgd.game.Level;
import edu.virginia.cs.sgd.util.SingletonAssetManager;

public class GameScreen extends AbstractScreen {

	private Level level;
//	private Viewer viewer;
	
	public GameScreen() {
		SingletonAssetManager.getInstance().finishLoading();
		
		
	}
	
	@Override
	public void render(float delta) {
		super.render(delta);
		
		
	}
	
	@Override
	public void keyUp(int keyCode) {
		level.onKeyPress(keyCode);
	}
}
