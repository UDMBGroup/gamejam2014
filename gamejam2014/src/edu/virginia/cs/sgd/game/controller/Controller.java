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
		case Input.Keys.ESCAPE:
			m.endTimer();
			break;
		case Input.Keys.W:
		case Input.Keys.UP:
			if (!m.getBooleanToShowJournal() && !m.getBooleanToShowAccuse()) {
				m.move(activePlayer, Direction.NORTH);
			}
			break;
		case Input.Keys.S:
		case Input.Keys.DOWN:
			if (!m.getBooleanToShowJournal() && !m.getBooleanToShowAccuse()) {
				m.move(activePlayer, Direction.SOUTH);
			}
			break;
		case Input.Keys.A:
		case Input.Keys.LEFT:
			if (m.getBooleanToShowJournal() && !m.getBooleanToShowAccuse()) {
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
			} else if (!m.getBooleanToShowAccuse()) {
				m.move(activePlayer, Direction.WEST);
			}
			break;
		case Input.Keys.D:
		case Input.Keys.RIGHT:
			if (m.getBooleanToShowJournal() && !m.getBooleanToShowAccuse()) {
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
			} else if (!m.getBooleanToShowAccuse()){
				m.move(activePlayer, Direction.EAST);
			}
			break;
		case Input.Keys.ENTER:
			if (!m.getBooleanToShowJournal() && !m.getBooleanToShowAccuse()) {
				activePlayer = m.getNextPlayer();
			}
			break;
		case Input.Keys.Z:
			if (!m.getBooleanToShowJournal() && !m.getBooleanToShowAccuse()) {
				m.interact(activePlayer);
			}
			break;
		case Input.Keys.J:
			if (m.getBooleanToShowAccuse())
				break;
			m.togglePauseT();
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
		case Input.Keys.NUM_0:
		case Input.Keys.NUMPAD_0:
			if (m.getBooleanToShowAccuse()) {
				m.addAccusationParam(0);
			}
			break;
		case Input.Keys.NUM_1:
		case Input.Keys.NUMPAD_1:
			if (m.getBooleanToShowAccuse()) {
				m.addAccusationParam(1);
			}
			break;
		case Input.Keys.NUM_2:
		case Input.Keys.NUMPAD_2:
			if (m.getBooleanToShowAccuse()) {
				m.addAccusationParam(2);
			}
			break;
		case Input.Keys.NUM_3:
		case Input.Keys.NUMPAD_3:
			if (m.getBooleanToShowAccuse()) {
				m.addAccusationParam(3);
			}
			break;
		case Input.Keys.NUM_4:
		case Input.Keys.NUMPAD_4:
			if (m.getBooleanToShowAccuse()) {
				m.addAccusationParam(4);
			}
			break;
		case Input.Keys.NUM_5:
		case Input.Keys.NUMPAD_5:
			if (m.getBooleanToShowAccuse()) {
				m.addAccusationParam(5);
			}
			break;
		case Input.Keys.NUM_6:
		case Input.Keys.NUMPAD_6:
			if (m.getBooleanToShowAccuse()) {
				m.addAccusationParam(6);
			}
			break;
		case Input.Keys.NUM_7:
		case Input.Keys.NUMPAD_7:
			if (m.getBooleanToShowAccuse()) {
				m.addAccusationParam(7);
			}
			break;
		case Input.Keys.NUM_8:
		case Input.Keys.NUMPAD_8:
			if (m.getBooleanToShowAccuse()) {
				m.addAccusationParam(8);
			}
			break;
		case Input.Keys.NUM_9:
		case Input.Keys.NUMPAD_9:
			if (m.getBooleanToShowAccuse()) {
				m.addAccusationParam(9);
			}
			break;
		}
	}

	public String getActiveCharacter() {
		return activePlayer;
	}

}
