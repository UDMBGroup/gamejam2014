package edu.virginia.cs.sgd.game.controller;

import java.util.LinkedList;
import java.util.ListIterator;

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
			if (m.getBooleanToShowJournal()) {
				if (m.getCurrentPlayer().getJournalIterator().size() > 0) {
					if (m.getCurrentPlayer().getJournIter().hasPrevious()) {
						Evidence e = m.getCurrentPlayer().getJournIter()
								.previous();
						m.getCurrentPlayer()
								.setJournalEvidenceName(e.getName());
						m.getCurrentPlayer().setJournalEvidenceMono(
								e.getCharMonologue(m.getCurrentPlayer()
										.getCharAssignment()));
					} else {
						ListIterator<Evidence> list = m
								.getCurrentPlayer()
								.getJournalIterator()
								.listIterator(
										m.getCurrentPlayer()
												.getJournalIterator().size());
						m.getCurrentPlayer().setJournIter(list);
						Evidence e = m.getCurrentPlayer().getJournIter()
								.previous();
						m.getCurrentPlayer()
								.setJournalEvidenceName(e.getName());
						m.getCurrentPlayer().setJournalEvidenceMono(
								e.getCharMonologue(m.getCurrentPlayer()
										.getCharAssignment()));
					}
				}
			} else {
				m.move(activePlayer, Direction.WEST);
			}
			break;
		case Input.Keys.D:
		case Input.Keys.RIGHT:
			if (m.getBooleanToShowJournal()) {
				if (m.getCurrentPlayer().getJournalIterator().size() > 0) {
					if (m.getCurrentPlayer().getJournIter().hasNext()) {
						Evidence e = m.getCurrentPlayer().getJournIter().next();
						m.getCurrentPlayer()
								.setJournalEvidenceName(e.getName());
						m.getCurrentPlayer().setJournalEvidenceMono(
								e.getCharMonologue(m.getCurrentPlayer()
										.getCharAssignment()));
					} else {
						ListIterator<Evidence> list = m.getCurrentPlayer()
								.getJournalIterator().listIterator();
						m.getCurrentPlayer().setJournIter(list);
						Evidence e = m.getCurrentPlayer().getJournIter().next();
						m.getCurrentPlayer()
								.setJournalEvidenceName(e.getName());
						m.getCurrentPlayer().setJournalEvidenceMono(
								e.getCharMonologue(m.getCurrentPlayer()
										.getCharAssignment()));
					}
				}
			} else {
				m.move(activePlayer, Direction.EAST);
			}
			break;
		case Input.Keys.ENTER:
			if (!m.getBooleanToShowJournal()) {
				activePlayer = m.getNextPlayer();
			}
			break;
		case Input.Keys.Z:
			m.interact(activePlayer);
			break;
		case Input.Keys.J:
			ListIterator<Evidence> list = m.getCurrentPlayer()
					.getJournalIterator().listIterator();
			m.getCurrentPlayer().setJournIter(list);
			if (m.getCurrentPlayer().getJournalIterator().size() > 0) {
				Evidence e = m.getCurrentPlayer().getJournalIterator()
						.peekFirst();
				m.getCurrentPlayer().setJournalEvidenceName(e.getName());
				m.getCurrentPlayer().setJournalEvidenceMono(
						e.getCharMonologue(m.getCurrentPlayer()
								.getCharAssignment()));
			}
			m.openJournal(activePlayer);

			break;
		}
	}

	public String getActiveCharacter() {
		return activePlayer;
	}

}
