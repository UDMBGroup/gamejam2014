package edu.virginia.cs.sgd.game.model;

import java.util.HashMap;
import java.util.Map;

import edu.virginia.cs.sgd.util.Point;
import edu.virginia.cs.sgd.viewer.RenderData;

public class Character extends RenderData {

	final private String charAssignment;
	private Map<Evidence, Boolean> isCollected;
	private Map<Evidence, Boolean> isShown;

	public Character(String name, String charAssignment, Point pos,
			Map<String, Evidence> evidenceList,
			Map<Evidence, String> initialIsShown) {
		super(name, pos);
		this.charAssignment = charAssignment;
		this.isCollected = new HashMap<Evidence, Boolean>();
		this.isShown = new HashMap<Evidence, Boolean>();
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

	public void setCollected(Evidence evidence) {
		if (!isCollected.get(evidence)) {
			isCollected.put(evidence, true);
		}
	}

	public boolean getIsCollected(Evidence evidence) {
		return isCollected.get(evidence);
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

	public String getCharAssignment() {
		return charAssignment;
	}

}
