package edu.virginia.cs.sgd.game.model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import edu.virginia.cs.sgd.util.Point;
import edu.virginia.cs.sgd.viewer.RenderData;

public class Character extends RenderData{

	final private String charAssignment;
	private Map<String, Integer> isCollected;
	private Map<String, Integer> isShown;

	public Character() {
		super("name", null);
		this.charAssignment = "";
		this.isCollected = null;
		this.isShown = null;
	}

	public Character(String name, String charAssignment, Point pos,
			ArrayList<Evidence> evidenceList, ArrayList<String> initialIsShown) {
		super(name, pos);
		this.charAssignment = charAssignment;
		this.isCollected = new HashMap<String, Integer>();
		this.isShown = new HashMap<String, Integer>();
		for (Evidence e : evidenceList) {
			isCollected.put(e.getName(), 0);
		}
		for (int i = 0; i < evidenceList.size(); i++) {
			String[] temp = initialIsShown.get(i).trim().split(",");
			if (temp[0] == charAssignment || temp[1] == charAssignment
					|| temp[2] == charAssignment) {
				isShown.put(evidenceList.get(i).getName(), 1);
			} else {
				isShown.put(evidenceList.get(i).getName(), 0);
			}
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
