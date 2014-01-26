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
	private Map<Evidence, String> initialIsShown;

	private Queue<String> players;
	
	private Map<String, Evidence> evidence;
	private Map<String, Character> characters;
	
	private String messageOnScreen = "Oh No! We are at the Global Game Jam, and we we're arguing while brainstorming ideas! The lights went out, now Mr. Bigglesworth is dead! Who dunnit?! [Enter - switch characters, Z - interact]";

	public Model(TiledMap map) {
		this.map = map;
		evidence = new HashMap<String, Evidence>();
		characters = new HashMap<String, Character>();
		initialIsShown = new HashMap<Evidence, String>();

		this.readInFile();
		
		Iterator<MapObject> i = map.getLayers().get("Evidence").getObjects().iterator();

		while(i.hasNext()) {
			MapObject o = i.next();
			int x = Integer.parseInt( (String)o.getProperties().get("x"));
			int y = Integer.parseInt( (String)o.getProperties().get("y"));

			Point p = new Point((int) x, (int) y);
			
			evidence.get(o.getName()).setPos(p);
			System.out.println(o.getName() + " " + x + " " + y);
		}

		Character programmer = new Character("John Nicholson", "0", new Point(9, 5), this.evidence, this.initialIsShown);
		Character artist = new Character("Scarlet Velvet", "1", new Point(9, 7), this.evidence, this.initialIsShown);
		Character writer = new Character("Annie N.", "2", new Point(10, 6), this.evidence, this.initialIsShown);

		programmer.setShown(this.evidence.get("the corpse"));
		artist.setShown(this.evidence.get("the corpse"));
		writer.setShown(this.evidence.get("the corpse"));
		
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
				evidence.put(current, ev);
			} else {
				evidence.get(current).addToMonologues(fileIn.nextLine());
			}
			count++;
		}

		fileIn.close();
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

	public String getMonologue(String evidence_arg, String character) {
		return this.evidence.get(evidence_arg).getCharMonologue(character);
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

	public void interact(String character) {
		Point p = characters.get(character).getPos();
		int[] dx = { 0, 1, 0, -1};
		int[] dy = { -1, 0, 1, 0};
		for (int i = 0; i < 4; i++) {
			
			boolean found = false;
			Point p1 = new Point(p.getX() + dx[i], p.getY() + dy[i]);
			for (Evidence ev : evidence.values())
			{
				if (ev.getPos().equals(p1))
				{
					//System.out.println(ev.getName());
					Character chara = characters.get(character);
					if (!chara.getIsShown(ev)) {
						this.setMessageOnScreen("Hmmmm");
						found = true; break;
					}
					else {
						String mono = ev.getCharMonologue(chara.getCharAssignment());
						chara.setCollected(ev);
						this.setMessageOnScreen(mono);
						this.updateShownBasedOnCollected();
						found = true; break;
					}
				}
			}
			if (found)
				break;
		}
	}
	
	public void updateShownBasedOnCollected() {
		for (Character chara : characters.values()) {
			for (Evidence ev : evidence.values()) {
				if (chara.getIsCollected(ev)) {
					if (ev.getName().equals("the corpse")) {
						if (chara.getName().equals("John Nicholson")) {
							chara.setShown(this.evidence.get("poison book"));
							chara.setShown(this.evidence.get("computer"));
							break;
						} else if (chara.getName().equals("Annie N.")) {
							chara.setShown(this.evidence.get("portrait"));
							chara.setShown(this.evidence.get("paper scrap"));
							break;
						} else if (chara.getName().equals("Scarlet Velvet")) {
							chara.setShown(this.evidence.get("snacks"));
							chara.setShown(this.evidence.get("manuscript"));
							break;
						}
					}
					
					if (ev.getName().equals("snacks")) {
						if (chara.getName().equals("Scarlet Velvet")) {
							chara.setShown(this.evidence.get("cable"));
							break;
						}
					}
					
					if (ev.getName().equals("poison book")) {
						if (chara.getName().equals("John Nicholson")) {
							chara.setShown(this.evidence.get("scotch glass"));
							break;
						}
					}
					
					if (ev.getName().equals("computer")) {
						if (chara.getName().equals("John Nicholson")) {
							chara.setShown(this.evidence.get("the purse"));
							break;
						}
					}
					
					if (ev.getName().equals("manuscript")) {
						if (chara.getName().equals("Scarlet Velvet")
								&& characters.get("John Nicholson").getIsCollected(evidence.get("computer"))) {
							chara.setShown(this.evidence.get("computer"));
							break;
						}
					}
					
					if (ev.getName().equals("computer")) {
						if (chara.getName().equals("Scarlet Velvet")) {
							chara.setShown(this.evidence.get("missing weapon"));
							break;
						}
					}
					
					if (ev.getName().equals("missing weapon")) {
						if (chara.getName().equals("Scarlet Velvet")) {
							chara.setShown(this.evidence.get("the purse"));
							break;
						}
					}
					
				}
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
	
	public Character getCurrentPlayer() {
		return characters.get(players.peek());
	}
	public String getCurrentPlayerName() {
		return players.peek();
	}
	public Evidence getEvidenceFromName(String name) {
		return evidence.get(name);
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
