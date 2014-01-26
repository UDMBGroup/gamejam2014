package edu.virginia.cs.sgd;


import com.badlogic.gdx.ApplicationListener;
import com.badlogic.gdx.Game;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.maps.tiled.TiledMap;

import edu.virginia.cs.sgd.input.Input;
import edu.virginia.cs.sgd.screen.AbstractScreen;
import edu.virginia.cs.sgd.screen.GameScreen;
import edu.virginia.cs.sgd.util.SingletonAssetManager;

public class Program extends Game implements ApplicationListener {
	
	public static final String LOG = Program.class.getName(); //GameOfSwords.class.getSimpleName();

	private Input input;
	private AbstractScreen screen;
	
	@Override
	public void create() {
		input = new Input();
		Texture.setEnforcePotImages(false);
		
		loadImmediateAssets();
		loadAssets();
		createScreen(GameScreen.class);
//		createScreen(MapScreen.class);
	}
	
	@Override
	public void dispose() {
		super.dispose();
	}

	@Override
	public void render() {
		Class<? extends AbstractScreen> newScreen = screen.checkScreenChange();
		if(newScreen != null) {
			createScreen(newScreen);
		}
		super.render();

	}

	@Override
	public void resize(int width, int height) {
		super.resize(width, height);
	}

	@Override
	public void pause() {
		super.pause();
	}

	@Override
	public void resume() {
		super.resume();
	}
	
	private void createScreen(Class<? extends AbstractScreen> type) {
		screen = null;
		try {
			screen = type.newInstance();
		} catch (InstantiationException e) {
			e.printStackTrace();
			return;
		} catch (IllegalAccessException e) {
			e.printStackTrace();
			return;
		}
		
		input.setListener(screen);
		setScreen(screen);
	}
	
	private void loadImmediateAssets() {

		SingletonAssetManager m = SingletonAssetManager.getInstance();
		m.load("LibGDX", "data/libgdx.png", Texture.class);
		m.load("sample", "data/samplesprite.png", Texture.class);
		m.load("testmap", "data/test.tmx", TiledMap.class);
		m.finishLoading();
		
	}
	
	private void loadAssets() {
		//SingletonAssetManager m = SingletonAssetManager.getInstance();
		
	}
}
