package edu.virginia.cs.sgd;

import com.badlogic.gdx.ApplicationListener;
import com.badlogic.gdx.Game;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.maps.tiled.TiledMap;

import edu.virginia.cs.sgd.input.Input;
import edu.virginia.cs.sgd.menu.Intro;
import edu.virginia.cs.sgd.screen.AbstractScreen;
import edu.virginia.cs.sgd.util.SingletonAssetManager;

public class Program extends Game implements ApplicationListener {

	public static final String LOG = Program.class.getName();

	private Input input;
	private AbstractScreen screen;

	@Override
	public void create() {
		input = new Input();
		Texture.setEnforcePotImages(false);

		loadImmediateAssets();
		loadAssets();
		createScreen(Intro.class);
		// createScreen(SplashScreen.class);
	}

	@Override
	public void dispose() {
		super.dispose();
	}

	@Override
	public void render() {
		Class<? extends AbstractScreen> newScreen = screen.checkScreenChange();
		if (newScreen != null) {
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
		// m.load("LibGDX", "data/libgdx.png", Texture.class);
		// m.load("sample", "data/samplesprite.png", Texture.class);

		m.load("poison book", "data/books.png", Texture.class);
		m.load("cable", "data/cables.png", Texture.class);
		m.load("snacks", "data/chips.png", Texture.class);
		m.load("computer", "data/comps.png", Texture.class);
		m.load("scotch glass", "data/glasses.png", Texture.class);
		m.load("manuscript", "data/manuscripts.png", Texture.class);
		m.load("missing weapon", "data/missingweapons.png", Texture.class);
		m.load("portrait", "data/portraits.png", Texture.class);
		m.load("the purse", "data/purses.png", Texture.class);
		m.load("paper scrap", "data/scraps.png", Texture.class);
		m.load("poison book", "data/books.png", Texture.class);
		m.load("the corpse", "data/deadbodies.png", Texture.class);
		m.load("book", "data/book.png", Texture.class);
		m.load("candles", "data/candles.png", Texture.class);
		m.load("chair", "data/kitchenChairs.png", Texture.class);
		m.load("knives", "data/knives.png", Texture.class);
		m.load("leftovers", "data/leftovers.png", Texture.class);
		m.load("pots", "data/pots.png", Texture.class);
		m.load("scuffs", "data/scuffs.png", Texture.class);
		m.load("cabinets", "data/cabinets.png", Texture.class);

		m.load("BGMusic", "data/theSituationMainTheme2.wav", Music.class);

		m.load("John Nicholson", "data/prog.png", Texture.class);
		m.load("Scarlet Velvet", "data/art.png", Texture.class);
		m.load("Annie N.", "data/write.png", Texture.class);

		m.load("Textbox", "data/textbox.png", Texture.class);
		m.load("SplashScreen", "data/splashscreen.png", Texture.class);
		m.load("Intro", "data/intro.png", Texture.class);
		m.load("Win", "data/win.png", Texture.class);
		m.load("Lose", "data/lose.png", Texture.class);
		m.load("Credits", "data/credits.png", Texture.class);
		m.load("Again", "data/again.png", Texture.class);
		m.load("writerJ", "data/writerJ.png", Texture.class);
		m.load("programmerJ", "data/programmerJ.png", Texture.class);
		m.load("artistJ", "data/artistJ.png", Texture.class);

		// m.load("testmap", "data/test.tmx", TiledMap.class);
		m.load("map", "data/sitmapver4.tmx", TiledMap.class);

		m.loadModelData("data/ModelData.txt");
		m.finishLoading();

	}

	private void loadAssets() {
		// SingletonAssetManager m = SingletonAssetManager.getInstance();

	}
}
