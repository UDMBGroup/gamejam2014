package edu.virginia.cs.sgd.menu;

import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.utils.Drawable;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;

import edu.virginia.cs.sgd.screen.AbstractScreen;
import edu.virginia.cs.sgd.util.SingletonAssetManager;

public class Again extends AbstractScreen {
	private Image Again;

	public Again() {
		super();
	}

	@Override
	public void show() {

		super.show();

		// load the splash image and create the texture region
		Texture splashTexture = SingletonAssetManager.getInstance()
				.get("Again");
		TextureRegion tr = new TextureRegion(splashTexture);
		Drawable splashDrawable = new TextureRegionDrawable(tr);

		Again = new Image(splashDrawable);

		Again.setPosition(0, 0);

		// and finally we add the actor to the stage
		stage.addActor(Again);
	}

	@Override
	public void keyUp(int keyCode) {
		if (keyCode == Input.Keys.ESCAPE || keyCode == Input.Keys.N) {
			System.exit(0);
		} else if (keyCode == Input.Keys.Y) {
			changeScreen(Intro.class);
		}
	}

}