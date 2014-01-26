package edu.virginia.cs.sgd.screen;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Music;

import edu.virginia.cs.sgd.Program;
import edu.virginia.cs.sgd.game.Level;
import edu.virginia.cs.sgd.util.SingletonAssetManager;

public class GameScreen extends AbstractScreen {

	private Level level;
	private Music bgMusic;
//	private Viewer viewer;
	
	public GameScreen() {
		SingletonAssetManager.getInstance().finishLoading();
		bgMusic = SingletonAssetManager.getInstance().get("BGMusic");	
		bgMusic.play();
	}
	
	@Override
	public void render(float delta) {
		super.render(delta);
	}
	
	@Override
	public void keyUp(int keyCode) {
		level.onKeyPress(keyCode);
	}
	
	@Override
	public void dispose() {
		bgMusic.dispose();
	}
}
