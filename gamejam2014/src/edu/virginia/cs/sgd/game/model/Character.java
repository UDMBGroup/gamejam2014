package edu.virginia.cs.sgd.game.model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import edu.virginia.cs.sgd.util.Point;
import edu.virginia.cs.sgd.viewer.RenderData;

public class Character extends RenderData {

	final private String charAssignment;
	private Map<String, Integer> isCollected;
	private Map<String, Integer> isShown;

	public Character(String name, String charAssignment, Point pos,
			Map<String, Evidence> evidenceList, Map<Evidence, String> initialIsShown) {
		super(name, pos);
		this.charAssignment = charAssignment;
		this.isCollected = new HashMap<String, Integer>();
		this.isShown = new HashMap<String, Integer>();
		for (String key : evidenceList.keySet()) {
			isCollected.put(key, 0);
		}
		int count = 0;
		for (Evidence key: evidenceList.values()) {
			String[] temp = initialIsShown.get(key).trim().split(",");
			for (int j = 0; j < temp.length; j++) {
				if (temp[j] == charAssignment) {
					isShown.put(key.getName(), 1);
				} else {
					isShown.put(key.getName(), 0);
				}
			}
			count++;
		}
	}

	public static void main(String[] args) {

	}

	public void setCollected(String evidence) {
		if (isCollected.get(evidence) == 0) {
			isCollected.put(evidence, 1);
		} else {
			isCollected.put(evidence, 0);
		}
	}

	public boolean getIsCollected(String evidence) {
		return isCollected.get(evidence) == 1;
	}

	public void setShown(String evidence) {
		if (isShown.get(evidence) == 0) {
			isShown.put(evidence, 1);
		} else {
			isShown.put(evidence, 0);
		}
	}

	public boolean getIsShown(String evidence) {
		return isShown.get(evidence) == 1;
	}

	public String getCharAssignment() {
		return charAssignment;
	}

}
