package edu.virginia.cs.sgd.game.controller;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;

import edu.virginia.cs.sgd.util.Point;

public class Controller {

	int x, y;
	
	public Controller() {
		x = 5;
		y = 5;
	}
	
	public void onKeyPress(int keyCode) {
		Gdx.app.log(this.getClass().getName(), "Key: " + Input.Keys.toString(keyCode));
		
		switch(keyCode) {
		case Input.Keys.UP:
			y++;
			break;
		case Input.Keys.DOWN:
			y--;
			break;
		case Input.Keys.LEFT:
			x--;
			break;
		case Input.Keys.RIGHT:
			x++;
			break;
		}
	}
	
	public Point getPoint() {
		return new Point(x, y);
	}
}
