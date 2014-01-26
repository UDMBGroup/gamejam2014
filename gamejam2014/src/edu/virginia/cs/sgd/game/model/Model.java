package edu.virginia.cs.sgd.game.model;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Queue;
import java.util.Scanner;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.maps.MapObject;
import com.badlogic.gdx.maps.MapProperties;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer.Cell;

import edu.virginia.cs.sgd.util.Point;
import edu.virginia.cs.sgd.util.SingletonAssetManager;
import edu.virginia.cs.sgd.viewer.RenderData;

public class Model {

	private Scanner fileIn;
	private File modelData;
	private TiledMap map;
	private Map<String, Evidence> evidenceList;
	private Map<Evidence, String> initialIsShown;

	private Queue<String> players;
	
	private Map<String, Evidence> evidence;
	private Map<String, Character> characters;
	
	private String messageOnScreen = "Testing string...";

	public Model(TiledMap map) {
		this.map = map;
		evidence = new HashMap<String, Evidence>();
		characters = new HashMap<String, Character>();

		evidenceList = new HashMap<String, Evidence>();
		initialIsShown = new HashMap<Evidence, String>();

		Iterator<MapObject> i = map.getLayers().get("Evidence").getObjects().iterator();

		while(i.hasNext()) {
			MapObject o = i.next();
			float x = (Float) o.getProperties().get("x");
			float y = (Float) o.getProperties().get("y");

			Point p = new Point((int) x, (int) y);

			evidence.get(o.getName()).setPos(p);

		}

		this.readInFile();

		Character programmer = new Character("John Nicholson", "0", new Point(1, 1), this.evidenceList, this.initialIsShown);
		Character artist = new Character("Scarlet Velvet", "1", new Point(1, 3), this.evidenceList, this.initialIsShown);
		Character writer = new Character("Annie N.", "2", new Point(3, 1), this.evidenceList, this.initialIsShown);

		characters.put("programmer", programmer);
		characters.put("artist", artist);
		characters.put("writer", writer);
		
		players = new LinkedList<String>();
		players.add("writer");
		players.add("programmer");
		players.add("artist");
	}

	public void readInFile() {
		System.out.println(SingletonAssetManager.getInstance().getModelData());
		fileIn = new Scanner(SingletonAssetManager.getInstance().getModelData().read());
		int count = 1;
		String current = "";

		Evidence ev = null;
		while (fileIn.hasNextLine()) {
			if (count % 5 == 0 && count > 0) {
				initialIsShown.put(ev, fileIn.nextLine());
			} else if ((count-1) % 5 == 0) {
				current = fileIn.nextLine();
				ev = new Evidence(current, new Point(0, 0));
				evidenceList.put(current, ev);
			} else {
				evidenceList.get(current).addToMonologues(fileIn.nextLine());
			}
			count++;
		}

		fileIn.close();
	}

	public static void main(String[] args) {
//				Model test = new Model(null);
//				for (String key: test.evidenceList.keySet()) {
//					System.out.println(test.evidenceList.get(key).getMonologues());
//				}
//				System.out.println(test.initialIsShown);
	}

	public int getMapWidth() {
		MapProperties prop = map.getProperties();
		int mapWidth = prop.get("width", Integer.class);
		return mapWidth;
	}

	public int getMapHeight() {
		MapProperties prop = map.getProperties();
		int mapHeight = prop.get("height", Integer.class);
		return mapHeight;
	}

	public String getMonologue(String evidence, String character) {
		return evidenceList.get(evidence).getCharMonologue(character);
	}
	public void move(String character, Direction dir) {

		Point p = characters.get(character).getPos();
		int newX = p.getX();
		int newY = p.getY();

		switch(dir) {
		case NORTH:
			newY++;
			break;
		case SOUTH:
			newY--;
			break;
		case EAST:
			newX++;
			break;
		case WEST:
			newX--;
			break;
		}
		Cell c = ((TiledMapTileLayer) map.getLayers().get("Collision"))
				.getCell(newX, newY);

		for (String otherCharName : characters.keySet())
		{
			if (otherCharName.equals(character)) continue;
			Point p1 = characters.get(otherCharName).getPos();
			if (p1.getX() == newX && p1.getY() == newY)
				return;
		}
		
		if(c == null) {
			p.setX(newX);
			p.setY(newY);
		}
	}

	public void interact(String character, Direction dir) {
		Point p = characters.get(character).getPos();
		Point p1 = new Point(p.getX(), p.getY());
		switch (dir) {
		case NORTH:
			p1.setY(p1.getY() + 1);
			break;
		case SOUTH:
			p1.setY(p1.getY() - 1);
			break;
		case EAST:
			p1.setX(p1.getX() + 1);
			break;
		case WEST:
			p1.setX(p1.getX() - 1);
			break;
		}
		for (Evidence ev : evidence.values())
		{
			if (ev.getPos().equals(p1))
			{
				System.out.println(ev.getName());
				// conjure up appropriate monologue
			}
		}
	}

	public List<RenderData> getRenderData() {

		List<RenderData> res = new ArrayList<RenderData>();

		for(Evidence e : evidence.values()) {
			res.add(e);
		}

		for(Character c : characters.values()) {
			res.add(c);
		}

		return res;
	}
	
	public Point getPosOfCharacter(String character) {
		return characters.get(character).getPos();
	}
	
	public String getNextPlayer() {
		String top = players.poll();
		players.add(top);
		return players.peek();
	}
	
	public String getMessageOnScreen() {
		return messageOnScreen;
	}
	public void clearMessageOnScreen() {
		messageOnScreen = "";
	}
	public void setMessageOnScreen(String message) {
		messageOnScreen = message;
	}
}
