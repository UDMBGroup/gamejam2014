package edu.virginia.cs.sgd.game.model;

import java.util.ArrayList;

import edu.virginia.cs.sgd.util.Point;
import edu.virginia.cs.sgd.viewer.RenderData;

public class Evidence extends RenderData{

	private ArrayList<String> monologues;
	
	public Evidence(String name, Point pos) {
		super(name, pos);
		monologues = new ArrayList<String>();
	}

	public static void main(String[] args) {

	}
	
	public void addToMonologues(String s) {
		monologues.add(s);
	}
	
	public ArrayList<String> getMonologues() {
		return monologues;
	}
	
	public String getCharMonologue(String charAssignment) {
		return monologues.get(Integer.parseInt(charAssignment));
	}

}
