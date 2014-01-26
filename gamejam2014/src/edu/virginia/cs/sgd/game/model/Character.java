package edu.virginia.cs.sgd.game.model;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import edu.virginia.cs.sgd.util.Point;
import edu.virginia.cs.sgd.viewer.RenderData;

public class Character extends RenderData {

	final private String charAssignment;
	private Map<Evidence, Integer> isCollected;
	private Map<Evidence, Integer> isShown;

	public Character(String name, String charAssignment, Point pos,
			Map<String, Evidence> evidenceList, Map<Evidence, String> initialIsShown) {
		super(name, pos);
		this.charAssignment = charAssignment;
		this.isCollected = new HashMap<Evidence, Integer>();
		this.isShown = new HashMap<Evidence, Integer>();
		for (Evidence key : evidenceList.values()) {
			isCollected.put(key, 0);
		}
		int count = 0;
		for (Evidence key: evidenceList.values()) {
			String[] temp = initialIsShown.get(key).trim().split(",");
			for (int j = 0; j < temp.length; j++) {
				if (temp[j].equals(charAssignment)) {
					isShown.put(key, 1);
				} else {
					isShown.put(key, 0);
				}
			}
			count++;
		}
	}

	public void setCollected(Evidence evidence) {
		if (isCollected.get(evidence) == 0) {
			isCollected.put(evidence, 1);
		}
	}

	public boolean getIsCollected(Evidence evidence) {
		return isCollected.get(evidence) == 1;
	}
	
	public void setShown(Evidence evidence) {
		if (isShown.get(evidence) == 0) {
			isShown.put(evidence, 1);
		}
	}

	public boolean getIsShown(Evidence evidence) {
		if (evidence.getName().equals("John Nicholson")
				|| evidence.getName().equals("Annie N.")
				|| evidence.getName().equals("Scarlet Velvet")
				|| evidence.getName().equals("the corpse"))
			return true;
			
		return isShown.get(evidence) == 1;
	}

	public String getCharAssignment() {
		return charAssignment;
	}

}
