package edu.virginia.cs.sgd.game.model;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.ListIterator;
import java.util.Map;

import edu.virginia.cs.sgd.util.Point;
import edu.virginia.cs.sgd.viewer.RenderData;

public class Character extends RenderData {

	final private int charAssignment;
	private Map<Evidence, Boolean> isCollected;
	private Map<Evidence, Boolean> isShown;
	private Map<Evidence, String> journalLog;
	private LinkedList<Evidence> journalIterator;
	private String journalEvidenceName;
	private String journalEvidenceMono;
	private ListIterator<Evidence> journIter;

	public Character(String name, int charAssignment, Point pos,
			Map<String, Evidence> evidenceList,
			Map<Evidence, String> initialIsShown) {
		super(name, pos);
		this.charAssignment = charAssignment;
		this.isCollected = new HashMap<Evidence, Boolean>();
		this.isShown = new HashMap<Evidence, Boolean>();
		this.journalLog = new HashMap<Evidence, String>();
		this.journalIterator = new LinkedList<Evidence>();
		this.journalEvidenceName = "";
		this.journalEvidenceMono = "";
		for (Evidence key : evidenceList.values()) {
			isCollected.put(key, false);
		}
		for (Evidence key : evidenceList.values()) {
			String[] temp = initialIsShown.get(key).trim().split(",");
			for (int j = 0; j < temp.length; j++) {
				if (temp[j].equals(charAssignment)) {
					isShown.put(key, true);
				} else {
					isShown.put(key, false);
				}
			}
		}
	}
	/**
	 * @return the journIter
	 */
	public ListIterator<Evidence> getJournIter() {
		return journIter;
	}
	/**
	 * @param journIter the journIter to set
	 */
	public void setJournIter(ListIterator<Evidence> journIter) {
		this.journIter = journIter;
	}
	/**
	 * @return the journalEvidenceName
	 */
	public String getJournalEvidenceName() {
		return journalEvidenceName;
	}

	/**
	 * @param journalEvidenceName the journalEvidenceName to set
	 */
	public void setJournalEvidenceName(String journalEvidenceName) {
		this.journalEvidenceName = journalEvidenceName;
	}

	/**
	 * @return the journalEvidenceMono
	 */
	public String getJournalEvidenceMono() {
		return journalEvidenceMono;
	}

	/**
	 * @param journalEvidenceMono the journalEvidenceMono to set
	 */
	public void setJournalEvidenceMono(String journalEvidenceMono) {
		this.journalEvidenceMono = journalEvidenceMono;
	}

	/**
	 * @param journalIterator the journalIterator to set
	 */
	public void setJournalIterator(LinkedList<Evidence> journalIterator) {
		this.journalIterator = journalIterator;
	}

	/**
	 * @return the journalIterator
	 */
	public LinkedList<Evidence> getJournalIterator() {
		return journalIterator;
	}

	/**
	 * @return the journalLog
	 */
	public Map<Evidence, String> getJournalLog() {
		return journalLog;
	}

	/**
	 * @param journalLog
	 *            the journalLog to set
	 */
	public void setJournalLog(Map<Evidence, String> journalLog) {
		this.journalLog = journalLog;
	}

	public void setCollected(Evidence evidence) {
		if (!isCollected.get(evidence)) {
			isCollected.put(evidence, true);
		}
	}

	public boolean getIsCollected(Evidence evidence) {
		return isCollected.get(evidence);
	}

	public Map<Evidence, Boolean> getEvidenceMap() {
		return this.isCollected;
	}

	public void setShown(Evidence evidence) {
		if (!isShown.get(evidence)) {
			isShown.put(evidence, true);
		}
	}

	public boolean getIsShown(Evidence evidence) {
		if (evidence.getName().equals("John Nicholson")
				|| evidence.getName().equals("Annie N.")
				|| evidence.getName().equals("Scarlet Velvet")
				|| evidence.getName().equals("the corpse")) {
			return true;
		}

		return isShown.get(evidence);
	}

	public int getCharAssignment() {
		return charAssignment;
	}

}
