package edu.virginia.cs.sgd.viewer;

import java.util.List;
import java.util.Map;

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
import edu.virginia.cs.sgd.game.timer.Timer;
import edu.virginia.cs.sgd.util.Point;
import edu.virginia.cs.sgd.util.SingletonAssetManager;

public class Viewer {

	private Point currentCenter;
	private OrthographicCamera camera;
	private Batch batch;
	private OrthogonalTiledMapRenderer mapRenderer;
	private BitmapFont font;
	private Texture textBoxTexture;
	private Texture writerJournal;
	private Texture artistJournal;
	private Texture programmerJournal;
	private Timer time;
	public boolean bool = true;
	private String characterAccuseText;
	private String roomAccuseText;
	private String weaponAccuseText;

	public Viewer(OrthogonalTiledMapRenderer mapRenderer) {
		camera = new OrthographicCamera();
		batch = mapRenderer.getSpriteBatch();
		currentCenter = new Point(0, 0);
		this.mapRenderer = mapRenderer;
		font = new BitmapFont(); // uses arial 15 by default
		textBoxTexture = SingletonAssetManager.getInstance().get("Textbox");
		writerJournal = SingletonAssetManager.getInstance().get("writerJ");
		programmerJournal = SingletonAssetManager.getInstance().get(
				"programmerJ");
		artistJournal = SingletonAssetManager.getInstance().get("artistJ");
		time = new Timer();

		setAccuseTexts();

		updateCamera();
		camera.update();
	}

	private void setAccuseTexts() {
		characterAccuseText = "Select a character!";

		roomAccuseText = "Select a room!";

		weaponAccuseText = "Select a weapon!";
	}

	public void dispose() {
		mapRenderer.dispose();
	}

	public void updateCamera(Point characterPosition) {
		this.moveMap((characterPosition.getX() - currentCenter.getX()) * 32,
				(characterPosition.getY() - currentCenter.getY()) * 32);
		currentCenter = characterPosition.copy();

		updateCamera();
	}

	public void resize(int width, int height) {

		camera.setToOrtho(false, width, height);

		camera.translate(-Gdx.graphics.getWidth() / 2 + 16,
				-Gdx.graphics.getHeight() / 2 + 16);

		updateCamera();
	}

	private void updateCamera() {

		camera.update();
		mapRenderer.setView(camera);

	}

	public void renderView(Model m) { // Screen is 480 by 320
		Gdx.gl.glClearColor(0, 0, 0, 0);
		Gdx.gl.glClear(GL10.GL_COLOR_BUFFER_BIT);

		// map renderer
		mapRenderer.render();

		// batch.setProjectionMatrix(camera.combined);
		batch.begin();

		if (m.getBooleanToShowJournal()) {
			switch (m.getCurrentPlayer().getCharAssignment()) {

			case 0:
				batch.draw(programmerJournal, currentCenter.getX() * 32 - 225,
						currentCenter.getY() * 32 - 145, 480, 320);
				String eviName = m.getCurrentPlayer().getJournalEvidenceName();
				font.draw(batch, eviName, currentCenter.getX() * 32,
						currentCenter.getY() * 32 + 100);
				String eviMono = m.getCurrentPlayer().getJournalEvidenceMono();

				String[] allWords = eviMono.split(" ");
				int numChars = 0;
				int lineNum = 0;
				String messageLine = "";
				for (String word : allWords) {

					messageLine += word + " ";
					numChars += word.length() + 1;
					if (numChars >= 60) {
						font.draw(batch, messageLine,
								currentCenter.getX() * 32 - 175,
								currentCenter.getY() * 32 - 60 - (lineNum * 20));
						lineNum++;
						messageLine = "";
						numChars = 0;
					}

				}
				font.draw(batch, messageLine, currentCenter.getX() * 32 - 175,
						currentCenter.getY() * 32 - 60 - (lineNum * 20));
				break;

			case 1:
				batch.draw(artistJournal, currentCenter.getX() * 32 - 225,
						currentCenter.getY() * 32 - 145, 480, 320);
				String eviNameA = m.getCurrentPlayer().getJournalEvidenceName();
				font.draw(batch, eviNameA, currentCenter.getX() * 32 - 175,
						currentCenter.getY() * 32 + 100);
				String eviMonoA = m.getCurrentPlayer().getJournalEvidenceMono();

				String[] allWordsA = eviMonoA.split(" ");
				int numCharsA = 0;
				int lineNumA = 0;
				String messageLineA = "";
				for (String word : allWordsA) {

					messageLineA += word + " ";
					numCharsA += word.length() + 1;
					if (numCharsA >= 20) {
						font.draw(batch, messageLineA,
								currentCenter.getX() * 32 + 50,
								currentCenter.getY() * 32 + 110
										- (lineNumA * 20));
						lineNumA++;
						messageLineA = "";
						numCharsA = 0;
					}

				}
				font.draw(batch, messageLineA, currentCenter.getX() * 32 + 50,
						currentCenter.getY() * 32 + 110 - (lineNumA * 20));
				break;

			case 2:
				batch.draw(writerJournal, currentCenter.getX() * 32 - 225,
						currentCenter.getY() * 32 - 145, 480, 320);
				String eviNameW = m.getCurrentPlayer().getJournalEvidenceName();
				font.draw(batch, eviNameW, currentCenter.getX() * 32 - 175,
						currentCenter.getY() * 32 + 100);
				String eviMonoW = m.getCurrentPlayer().getJournalEvidenceMono();

				String[] allWordsW = eviMonoW.split(" ");
				int numCharsW = 0;
				int lineNumW = 0;
				String messageLineW = "";
				for (String word : allWordsW) {

					messageLineW += word + " ";
					numCharsW += word.length() + 1;
					if (numCharsW >= 20) {
						font.draw(batch, messageLineW,
								currentCenter.getX() * 32 + 50,
								currentCenter.getY() * 32 + 110
										- (lineNumW * 20));
						lineNumW++;
						messageLineW = "";
						numCharsW = 0;
					}

				}
				font.draw(batch, messageLineW, currentCenter.getX() * 32 + 50,
						currentCenter.getY() * 32 + 110 - (lineNumW * 20));
				break;

			default:
				break;

			}
		} else if (m.getBooleanToShowAccuse()) {
			batch.draw(textBoxTexture, currentCenter.getX() * 32 - 225,
					currentCenter.getY() * 32 - 145, 480, 320);
			// write message to choose character/weapon/room
			switch (m.getAccuseStateFlag()) {
			case 0:
				// show character text
				font.draw(batch, characterAccuseText,
						currentCenter.getX() * 32 - 50,
						currentCenter.getY() * 32 + 50);
				font.draw(batch, "1. The Programmer",
						currentCenter.getX() * 32 - 200,
						currentCenter.getY() * 32 + 20);
				font.draw(batch, "2. The Artist",
						currentCenter.getX() * 32 - 50,
						currentCenter.getY() * 32 + 20);
				font.draw(batch, "3. The Writer",
						currentCenter.getX() * 32 + 100,
						currentCenter.getY() * 32 + 20);
				break;
			case 1:
				// show room text
				font.draw(batch, roomAccuseText,
						currentCenter.getX() * 32 - 50,
						currentCenter.getY() * 32 + 50);
				font.draw(batch, "1. The Study",
						currentCenter.getX() * 32 - 200,
						currentCenter.getY() * 32 + 20);
				font.draw(batch, "2. The Kitchen",
						currentCenter.getX() * 32 - 50,
						currentCenter.getY() * 32 + 20);
				font.draw(batch, "3. The Dining Room",
						currentCenter.getX() * 32 + 100,
						currentCenter.getY() * 32 + 20);
				break;
			case 2:
				// show weapon text
				font.draw(batch, weaponAccuseText,
						currentCenter.getX() * 32 - 50,
						currentCenter.getY() * 32 + 50);
				font.draw(batch, "1. The Cable",
						currentCenter.getX() * 32 - 200,
						currentCenter.getY() * 32 + 20);
				font.draw(batch, "2. The Poison",
						currentCenter.getX() * 32 - 50,
						currentCenter.getY() * 32 + 20);
				font.draw(batch, "3. The Candlestick",
						currentCenter.getX() * 32 + 100,
						currentCenter.getY() * 32 + 20);
				break;
			}
		} else {
			List<RenderData> listRData = m.getRenderData();
			for (RenderData rData : listRData) {
				// draw game objects
				Texture tex = SingletonAssetManager.getInstance().get(
						rData.getName());
				Point p0 = rData.getPos();

				if (rData instanceof Evidence) {

					if (!(rData.getName().equals("John Nicholson")
							|| rData.getName().equals("Annie N.") || rData
							.getName().equals("Scarlet Velvet"))) {
						// programmer - 2
						// artist - 0
						// writer - 1
						int texWidth = tex.getWidth() / 3;
						int texIndex = -1;
						Character chara = m.getCurrentPlayer();
						// System.out.println(rData.getName());
						if (chara.getIsShown((Evidence) rData)) {
							if (chara.getName().equals("John Nicholson")) {
								texIndex = 2;
							} else if (chara.getName().equals("Annie N.")) {
								texIndex = 1;
							} else {
								texIndex = 0;
							}
						}

						if (texIndex != -1) {
							// System.out.println("texIndex = " + texIndex);
							batch.draw(tex, p0.getX() * 32, p0.getY() * 32,
									texWidth, tex.getHeight(), texIndex
											* texWidth, 0, texWidth,
									tex.getHeight(), false, false);
						}
					}
				} else {
					batch.draw(tex, p0.getX() * 32, p0.getY() * 32);
				}
			}

			String messageOnScreen = m.getMessageOnScreen();
			// if (!messageOnScreen.isEmpty())
			// {
			batch.draw(textBoxTexture, currentCenter.getX() * 32 - 225,
					currentCenter.getY() * 32 - 145);

			String[] allWords = messageOnScreen.split(" ");
			int numChars = 0;
			int lineNum = 0;
			String messageLine = "";
			for (String word : allWords) {

				messageLine += word + " ";
				numChars += word.length() + 1;
				if (numChars >= 60) {
					font.draw(batch, messageLine,
							currentCenter.getX() * 32 - 200,
							currentCenter.getY() * 32 - 60 - (lineNum * 20));
					lineNum++;
					messageLine = "";
					numChars = 0;
				}

			}
			font.draw(batch, messageLine, currentCenter.getX() * 32 - 200,
					currentCenter.getY() * 32 - 60 - ((lineNum) * 20));
			// }
		}
		if (m.getPauseT()) {
			time.pause();
		} else if (m.getEndT()){
			time.end();
			m.setEndTimer();
		} else {
			time.resume();
			if (time.getCurrentTime() == 0) {
				m.setAccuse();
			} else {
				font.draw(batch, time.toString(),
						currentCenter.getX() * 32 + 215,
						currentCenter.getY() * 32 - 120);
			}
		}
		batch.end();

	}

	public void moveMap(int deltaX, int deltaY) {
		Vector3 delta = new Vector3(deltaX * camera.zoom, deltaY * camera.zoom,
				0);
		camera.translate(delta);
	}
}