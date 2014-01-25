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
	
	public Character() {
		this.name = "";
		this.charAssignment = -1;
		this.pos = null;
		this.isCollected = null;
	}
	
	public Character(String name, int charAssignment, Point pos, ArrayList<String> evidenceList) {
		this.name = name;
		this.charAssignment = charAssignment;
		this.pos = pos;
		this.isCollected = new HashMap<String, Integer>();
		for (String s: evidenceList) {
			isCollected.put(s, 0);
		}
	}
	
	public static void main(String[] args) {

	}
	
	public void setCollected(String evidence) {
		isCollected.put(evidence, 1);
	}
	
	public boolean getIsCollected(String evidence) {
		return isCollected.get(evidence) == 1;
	}

}
