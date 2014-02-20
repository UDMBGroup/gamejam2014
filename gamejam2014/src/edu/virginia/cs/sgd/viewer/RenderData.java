package edu.virginia.cs.sgd.viewer;

import edu.virginia.cs.sgd.util.Point;

public class RenderData {

	private String name;
	private Point pos;

	public RenderData(String name, Point pos) {
		super();
		this.name = name;
		this.pos = pos;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Point getPos() {
		return pos;
	}

	public void setPos(Point pos) {
		this.pos = pos;
	}

}
