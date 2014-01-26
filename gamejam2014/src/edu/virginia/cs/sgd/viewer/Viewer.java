package edu.virginia.cs.sgd.viewer;

import java.util.List;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL10;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.math.Vector3;

import edu.virginia.cs.sgd.game.model.Character;
import edu.virginia.cs.sgd.game.model.Evidence;
import edu.virginia.cs.sgd.game.model.Model;
import edu.virginia.cs.sgd.util.Point;
import edu.virginia.cs.sgd.util.SingletonAssetManager;

public class Viewer {

	private Point currentCenter;
	private OrthographicCamera camera;
	private Batch batch;
	private OrthogonalTiledMapRenderer mapRenderer;
	private BitmapFont font;
	private Texture textBoxTexture;
	
	public Viewer(OrthogonalTiledMapRenderer mapRenderer)
	{
		camera = new OrthographicCamera();
		batch = mapRenderer.getSpriteBatch();
		currentCenter = new Point(0, 0);
		this.mapRenderer = mapRenderer;	
		font = new BitmapFont();	//uses arial 15 by default
		textBoxTexture = SingletonAssetManager.getInstance().get("Textbox");
		
		updateCamera();
		camera.update();
	}
	
	public void dispose()
	{
		mapRenderer.dispose();
	}
	
	public void updateCamera(Point characterPosition)
	{
		this.moveMap((characterPosition.getX() - currentCenter.getX()) * 32, (characterPosition.getY() - currentCenter.getY()) * 32);
		currentCenter = characterPosition.copy();

		updateCamera();
	}

	public void resize(int width, int height) {
		
		camera.setToOrtho(false, width, height);

		camera.translate(-Gdx.graphics.getWidth() / 2 + 16, -Gdx.graphics.getHeight() / 2 + 16);
		
		updateCamera();
	}
	
	private void updateCamera() {

		camera.update();
		mapRenderer.setView(camera);
		
	}
	
	public void renderView(Model m)
	{
		Gdx.gl.glClearColor(0, 0, 0, 0);
		Gdx.gl.glClear(GL10.GL_COLOR_BUFFER_BIT);
		
		// map renderer
		mapRenderer.render();
		
//		batch.setProjectionMatrix(camera.combined);
		batch.begin();
		
		List<RenderData> listRData = m.getRenderData();
		for (RenderData rData : listRData)
		{
			// draw game objects		
			Texture tex = SingletonAssetManager.getInstance().get(rData.getName());
			Point p0 = rData.getPos();
			
			if (rData instanceof Evidence) {
				
				if (!(rData.getName().equals("John Nicholson")
						|| rData.getName().equals("Annie N.")
						|| rData.getName().equals("Scarlet Velvet")))
				{
					// programmer - 2
					// artist - 0
					// writer - 1
					int texWidth = tex.getWidth() / 3;
					int texIndex = -1;
					Character chara = m.getCurrentPlayer();
					//System.out.println(rData.getName());
					if (chara.getIsShown((Evidence)rData)) {
						if (chara.getName().equals("John Nicholson")) {
							texIndex = 2;
						} else if (chara.getName().equals("Annie N.")) {
							texIndex = 1;
						} else {
							texIndex = 0;
						}
					}
					
					if (texIndex != -1) {
						//System.out.println("texIndex = " + texIndex);
						batch.draw(tex, p0.getX() * 32, p0.getY() * 32, texWidth, tex.getHeight(), texIndex * texWidth, 0, texWidth, tex.getHeight(), false, false);
					}
				}
			}
			else {
				batch.draw(tex, p0.getX() * 32, p0.getY() * 32);
			}
		}
		String messageOnScreen = m.getMessageOnScreen();
		if (!messageOnScreen.isEmpty())
		{
			batch.draw(textBoxTexture, currentCenter.getX() * 32 - 225, currentCenter.getY() * 32 - 145);
			
			font.draw(batch, messageOnScreen, currentCenter.getX() * 32 - 200, currentCenter.getY() * 32 - 75);

		}
		batch.end();		
	}
	
    public void moveMap(int deltaX, int deltaY) {
        Vector3 delta = new Vector3(deltaX * camera.zoom, deltaY * camera.zoom, 0);
        camera.translate(delta);
    }        	
}