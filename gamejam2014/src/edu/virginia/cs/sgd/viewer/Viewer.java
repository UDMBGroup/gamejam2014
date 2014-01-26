package edu.virginia.cs.sgd.viewer;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL10;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.maps.MapObject;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.maps.tiled.renderers.BatchTiledMapRenderer;
import com.badlogic.gdx.math.Vector3;

import edu.virginia.cs.sgd.util.Point;
import edu.virginia.cs.sgd.util.SingletonAssetManager;

public class Viewer {

	private Point currentCenter;
	private OrthographicCamera camera;
	private SpriteBatch batch;
	private BatchTiledMapRenderer mapRenderer;
	
	public Viewer()
	{
		camera = new OrthographicCamera();
		batch = new SpriteBatch();
		currentCenter = new Point(0, 0);
		//TiledMap tMap = SingletonAssetManager.getInstance().get("TestMap");
		//mapRenderer = new GameMapRenderer(tMap);	//get tiled map
		
	}
	
	public void dispose()
	{
		batch.dispose();
		//mapRenderer.dispose();
	}
	
	public void updateCamera(Point characterPosition)
	{
		this.moveMap(currentCenter.getX() - characterPosition.getX(), currentCenter.getY() - characterPosition.getY());
		currentCenter = characterPosition;
		
		camera.update();
	}

	public void renderView()
	{
		Gdx.gl.glClearColor(1, 1, 1, 1);
		Gdx.gl.glClear(GL10.GL_COLOR_BUFFER_BIT);
		
		batch.setProjectionMatrix(camera.combined);
		batch.begin();

		// map renderer
		//mapRenderer.setView(camera);
		//mapRenderer.render();

		// draw game objects		
		// for each object
			Texture tex;	// texture of object
			Point p0;		// the position object
		
			//					x		y		originx	originy						scalex	scaley
			//batch.draw(tex, p0.getX(), p0.getY(), 0, 0, tex.getWidth(), tex.getHeight(), 1, 1, false, false);
		
		batch.end();		
	}
	
    public void moveMap(int deltaX, int deltaY) {
        Vector3 delta = new Vector3(-deltaX * camera.zoom, deltaY * camera.zoom, 0);
        camera.translate(delta);
    }
    
    private class GameMapRenderer extends BatchTiledMapRenderer
    {

		public GameMapRenderer(TiledMap map) {
			super(map);
			// TODO Auto-generated constructor stub
		}

		@Override
		public void renderObject(MapObject object) {
			// TODO Auto-generated method stub
		}

		@Override
		public void renderTileLayer(TiledMapTileLayer layer) {
			// TODO Auto-generated method stub
		}
    	
    }
}