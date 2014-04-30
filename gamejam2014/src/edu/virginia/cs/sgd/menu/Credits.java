package edu.virginia.cs.sgd.menu;

import static com.badlogic.gdx.scenes.scene2d.actions.Actions.sequence;

import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Action;
import com.badlogic.gdx.scenes.scene2d.actions.MoveToAction;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.utils.Drawable;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;

import edu.virginia.cs.sgd.screen.AbstractScreen;
import edu.virginia.cs.sgd.util.SingletonAssetManager;

public class Credits extends AbstractScreen {
	private Image Credits;

	public Credits() {
		super();
	}

	@Override
	public void show() {

		super.show();

		// load the splash image and create the texture region
		Texture splashTexture = SingletonAssetManager.getInstance().get(
				"Credits");
		TextureRegion tr = new TextureRegion(splashTexture);
		Drawable splashDrawable = new TextureRegionDrawable(tr);

		Credits = new Image(splashDrawable);

		Credits.setPosition(0, -1000);
		MoveToAction moveTo = new MoveToAction();
		moveTo.setPosition(0, 320);
		moveTo.setDuration(30f);
		Credits.addAction(sequence(moveTo, new Action() {

			@Override
			public boolean act(float delta) { // the last action will
												// move to the next
												// screen
				changeScreen(Again.class);
				return true;
			}
		}));

		// and finally we add the actor to the stage
		stage.addActor(Credits);

	}

	@Override
	public void keyUp(int keyCode) {
		if (keyCode == Input.Keys.ESCAPE) {
			changeScreen(Again.class);
		}
	}

}