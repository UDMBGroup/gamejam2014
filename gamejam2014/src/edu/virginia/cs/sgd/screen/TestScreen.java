package edu.virginia.cs.sgd.screen;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Buttons;
import com.badlogic.gdx.graphics.GL10;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.Texture.TextureFilter;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

import edu.virginia.cs.sgd.game.controller.Controller;
import edu.virginia.cs.sgd.util.Point;
import edu.virginia.cs.sgd.util.SingletonAssetManager;

public class TestScreen extends AbstractScreen {
	private Controller controller;
	
	private OrthographicCamera camera;
	private SpriteBatch batch;
	private Texture texture;
	private Sprite sprite;
	
	private TextureRegion sample;
	
	@Override
	public void show() {	
		controller = new Controller();
		
		float w = Gdx.graphics.getWidth();
		float h = Gdx.graphics.getHeight();
		
		camera = new OrthographicCamera();
		batch = new SpriteBatch();
		
		texture = SingletonAssetManager.getInstance().get("LibGDX");
		texture.setFilter(TextureFilter.Linear, TextureFilter.Linear);
		
		TextureRegion region = new TextureRegion(texture, 0, 0, 512, 275);
		
		sprite = new Sprite(region);
		sprite.setSize(0.9f, 0.9f * sprite.getHeight() / sprite.getWidth());
		sprite.setOrigin(sprite.getWidth()/2, sprite.getHeight()/2);
		sprite.setPosition(-sprite.getWidth()/2, -sprite.getHeight()/2);
		
		Texture tex = SingletonAssetManager.getInstance().get("sample");
		sample = new TextureRegion(tex, 0, 0, 32, 32);
		
	}

	@Override
	public void dispose() {
		batch.dispose();
		texture.dispose();
	}

	@Override
	public void render(float delta) {		
		Gdx.gl.glClearColor(1, 1, 1, 1);
		Gdx.gl.glClear(GL10.GL_COLOR_BUFFER_BIT);
		
//		batch.setProjectionMatrix(camera.combined);
		batch.begin();
		//sprite.draw(batch);

		Point p = controller.getPoint();
		batch.draw(sample, (float) p.getX() * 32, (float) p.getY() * 32);
		
		batch.end();
	}

	@Override
	public void resize(int width, int height) {
		
		//viewer.resize(width, height);
	}

	@Override
	public void keyDown(int keyCode) {
		
	}

	@Override
	public void keyUp(int keyCode) {
		controller.onKeyPress(keyCode);
	}

	@Override
	public void touchDown(int screenX, int screenY, int pointer, int button) {
		
	}

	@Override
	public void touchUp(int screenX, int screenY, int pointer, int button, boolean dragging) {
		
		if(dragging) {
			return;
		}
		
		//Point coords = viewer.getCoord(screenX, screenY);

		if(button == Buttons.LEFT) {
			//level.onTouch(coords);
		}
	}

	@Override
	public void scrolled(int amount) {
		//viewer.zoomMap(amount == 1);
	}
	
	@Override
	public void touchDragged(int screenX, int screenY, int pointer, int deltaX, int deltaY) {
		//viewer.moveMap(deltaX, deltaY);
	}
}
