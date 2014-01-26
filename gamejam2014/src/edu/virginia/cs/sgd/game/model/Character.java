package edu.virginia.cs.sgd.game.model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import edu.virginia.cs.sgd.util.Point;

public class Character {

	private String name;
	private int charAssignment;
	private Point pos;
	private Map<String, Integer> isCollected;
	private Map<String, Integer> isShown;
	
	public Character() {
		this.name = "";
		this.charAssignment = -1;
		this.pos = null;
		this.isCollected = null;
		this.isShown = null;
	}
	
	public Character(String name, int charAssignment, Point pos, ArrayList<String> evidenceList) {
		this.name = name;
		this.charAssignment = charAssignment;
		this.pos = pos;
		this.isCollected = new HashMap<String, Integer>();
		this.isShown = new HashMap<String, Integer>();
		for (String s: evidenceList) {
			isCollected.put(s, 0);
		}
		for (String s: evidenceList) {
			isShown.put(s, 0);
		}
	}
	
	public static void main(String[] args) {

	}
	
	public void setCollected(String evidence) {
		if (isCollected.get(evidence) == 0) {
			isCollected.put(evidence, 1);
		} else {
			isCollected.put(evidence,  0);
		}
	}
	
	public boolean getIsCollected(String evidence) {
		return isCollected.get(evidence) == 1;
	}
	
	public void setShown(String evidence) {
		if (isShown.get(evidence) == 0) {
			isShown.put(evidence, 1);
		} else {
			isShown.put(evidence,  0);
		}
	}
	
	public boolean getIsShown(String evidence) {
		return isShown.get(evidence) == 1;
	}

}
