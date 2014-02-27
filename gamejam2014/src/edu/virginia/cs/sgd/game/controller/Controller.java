package edu.virginia.cs.sgd.game.controller;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;

import edu.virginia.cs.sgd.game.model.Direction;
import edu.virginia.cs.sgd.game.model.Evidence;
import edu.virginia.cs.sgd.game.model.Model;

public class Controller {

	public String activePlayer;

	public Controller(String startPlayer) {
		activePlayer = startPlayer;
	}

	public void onKeyPress(Model m, int keyCode) {
		Gdx.app.log(this.getClass().getName(),
				"Key: " + Input.Keys.toString(keyCode));

		switch (keyCode) {
		case Input.Keys.W:
		case Input.Keys.UP:
			m.move(activePlayer, Direction.NORTH);
			break;
		case Input.Keys.S:
		case Input.Keys.DOWN:
			m.move(activePlayer, Direction.SOUTH);
			break;
		case Input.Keys.A:
		case Input.Keys.LEFT:
			m.move(activePlayer, Direction.WEST);
			break;
		case Input.Keys.D:
		case Input.Keys.RIGHT:
			m.move(activePlayer, Direction.EAST);
			break;
		case Input.Keys.ENTER:
			activePlayer = m.getNextPlayer();
			break;
		case Input.Keys.Z:
			m.interact(activePlayer);
			break;
		case Input.Keys.J:
<<<<<<< HEAD
			for(Evidence key: (m.getCurrentPlayer()).getJournalLog().keySet()) {
					System.out.println(key.getName() +"\n");
					System.out.println(m.getCurrentPlayer().getJournalLog().get(key) + "\n");
			}
=======
			m.openJournal(activePlayer);
>>>>>>> origin/Jeremy
			break;
		}
	}

	public String getActiveCharacter() {
		return activePlayer;
	}

}
