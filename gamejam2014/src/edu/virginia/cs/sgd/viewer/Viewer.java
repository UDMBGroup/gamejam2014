package edu.virginia.cs.sgd.viewer;

import java.util.List;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL10;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.math.Vector3;

import edu.virginia.cs.sgd.game.model.Model;
import edu.virginia.cs.sgd.util.Point;
import edu.virginia.cs.sgd.util.SingletonAssetManager;

public class Viewer {

	private Point currentCenter;
	private OrthographicCamera camera;
	private SpriteBatch batch;
	private OrthogonalTiledMapRenderer mapRenderer;
	
	public Viewer(OrthogonalTiledMapRenderer mapRenderer)
	{
		camera = new OrthographicCamera();
		batch = new SpriteBatch();
		currentCenter = new Point(0, 0);
		this.mapRenderer = mapRenderer;		
	}
	
	public void dispose()
	{
		batch.dispose();
		mapRenderer.dispose();
	}
	
	public void updateCamera(Point characterPosition)
	{
		this.moveMap(currentCenter.getX() - characterPosition.getX(), currentCenter.getY() - characterPosition.getY());
		currentCenter = characterPosition;
		
		camera.update();
	}

	public void resize(int width, int height) {
		
		camera.setToOrtho(false, width, height);
		camera.update();
		mapRenderer.setView(camera);
	}
	
	public void renderView(Model m)
	{
		Gdx.gl.glClearColor(1, 1, 1, 1);
		Gdx.gl.glClear(GL10.GL_COLOR_BUFFER_BIT);
		
		// map renderer
		mapRenderer.render();
		
		batch.setProjectionMatrix(camera.combined);
		batch.begin();
		
		List<RenderData> listRData = m.getRenderData();
		for (RenderData rData : listRData)
		{
			// draw game objects		
			Texture tex = SingletonAssetManager.getInstance().get(rData.getName());
			Point p0 = rData.getPos();
			
			//batch.draw(tex, p0.getX(), p0.getY(), tex.getWidth(), tex.getHeight(), 0, 0, tex.getWidth(), tex.getHeight(), false, false);
			batch.draw(tex, p0.getX(), p0.getY());
		}

		
		batch.end();		
	}
	
    public void moveMap(int deltaX, int deltaY) {
        Vector3 delta = new Vector3(-deltaX * camera.zoom, deltaY * camera.zoom, 0);
        camera.translate(delta);
    }        	
}