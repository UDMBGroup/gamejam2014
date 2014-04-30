package edu.virginia.cs.sgd.menu;

import static com.badlogic.gdx.scenes.scene2d.actions.Actions.delay;
import static com.badlogic.gdx.scenes.scene2d.actions.Actions.fadeIn;
import static com.badlogic.gdx.scenes.scene2d.actions.Actions.fadeOut;
import static com.badlogic.gdx.scenes.scene2d.actions.Actions.sequence;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Action;
import com.badlogic.gdx.scenes.scene2d.actions.MoveToAction;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.utils.Drawable;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.Scaling;

import edu.virginia.cs.sgd.screen.AbstractScreen;
import edu.virginia.cs.sgd.screen.GameScreen;
import edu.virginia.cs.sgd.util.SingletonAssetManager;

public class Intro extends AbstractScreen {
	private Image Intro;

	public Intro() {
		super();
	}

	@Override
	public void show() {

		super.show();

		// load the splash image and create the texture region
		Texture splashTexture = SingletonAssetManager.getInstance().get(
				"Intro");
		TextureRegion tr = new TextureRegion(splashTexture);
		Drawable splashDrawable = new TextureRegionDrawable(tr);

		Intro = new Image(splashDrawable, Scaling.stretch);
		Intro.setFillParent(true);

		// configure the fade-in/out effect on the splash image

		Intro.setPosition(0, -320);
		MoveToAction moveTo = new MoveToAction();
		moveTo.setPosition(0, 640);
		moveTo.setDuration(50f);
		Intro.addAction(sequence(moveTo, new Action() {

					@Override
					public boolean act(float delta) { // the last action will
														// move to the next
														// screen
						changeScreen(SplashScreen.class);
						return true;
					}
				}));

		// and finally we add the actor to the stage
		stage.addActor(Intro);

	}
}
