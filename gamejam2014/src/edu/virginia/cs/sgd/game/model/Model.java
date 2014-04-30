package edu.virginia.cs.sgd.game.model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Queue;
import java.util.Scanner;

import com.badlogic.gdx.maps.MapObject;
import com.badlogic.gdx.maps.MapObjects;
import com.badlogic.gdx.maps.MapProperties;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer.Cell;

import edu.virginia.cs.sgd.util.Point;
import edu.virginia.cs.sgd.util.SingletonAssetManager;
import edu.virginia.cs.sgd.viewer.RenderData;

public class Model {

	private Scanner fileIn;
	private TiledMap map;
	private int mapSize = 49;
	private Map<Evidence, String> initialIsShown;

	private Queue<String> players;

	private Map<String, Evidence> evidence;
	private Map<String, Character> characters;
	private Map<Point, Point> roomTrans;

	private String messageOnScreen = "Oh No! We are at the Global Game Jam, and we were arguing while brainstorming ideas! The lights went out, now Mr. Bigglesworth is dead! Who dunnit?! [Enter - switch characters, Z - interact]";

	private boolean journalBool = false; // DELTE THIS
	private boolean pauseT = false;
	private boolean accuseBool = false;

	private ArrayList<Integer> accusation;
	private ArrayList<Integer> correctAccuse;

	public Model(TiledMap map) {
		this.map = map;
		evidence = new HashMap<String, Evidence>();
		characters = new HashMap<String, Character>();
		roomTrans = new HashMap<Point, Point>();
		initialIsShown = new HashMap<Evidence, String>();
		accusation = new ArrayList<Integer>();

		correctAccuse = new ArrayList<Integer>();
		correctAccuse.add(1); // which character?
		correctAccuse.add(2); // which room?
		correctAccuse.add(3); // which weapon?

		this.readInFile();

		Iterator<MapObject> i = map.getLayers().get("Evidence").getObjects()
				.iterator();

		while (i.hasNext()) {
			MapObject o = i.next();
			int x = Integer.parseInt((String) o.getProperties().get("x"));
			int y = Integer.parseInt((String) o.getProperties().get("y"));

			Point p = new Point(x, mapSize - y);

			System.out.println(o.getName());
			evidence.get(o.getName()).setPos(p);
			System.out.println(o.getName() + " " + x + " " + y);
		}

		i = map.getLayers().get("RoomTrans").getObjects().iterator();
		MapObjects rt = map.getLayers().get("RoomTrans").getObjects();

		while (i.hasNext()) {
			MapObject o = i.next();
			int x = Integer.parseInt((String) o.getProperties().get("x"));
			int y = mapSize
					- Integer.parseInt((String) o.getProperties().get("y"));

			Point p = new Point(x, y);

			int xNext = Integer.parseInt((String) (rt.get((String) o
					.getProperties().get("next"))).getProperties().get("x"));
			int yNext = mapSize
					- Integer.parseInt((String) (rt.get((String) o
							.getProperties().get("next"))).getProperties().get(
							"y"));

			Point pNext = new Point(xNext, yNext);

			roomTrans.put(p, pNext);
		}

		Character programmer = new Character("John Nicholson", 0, new Point(9,
				mapSize - 9), this.evidence, this.initialIsShown);
		Character artist = new Character("Scarlet Velvet", 1, new Point(9,
				mapSize - 7), this.evidence, this.initialIsShown);
		Character writer = new Character("Annie N.", 2, new Point(10,
				mapSize - 8), this.evidence, this.initialIsShown);

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

	public void openJournal(String activeCharacter) {
		// First, I want to get all the evidence that the active Character has
		// seen. Make a collection out of them.
		// Then find a good way to display them

		if (journalBool == true)
			journalBool = false;
		else
			journalBool = true;

	}

	public Boolean getBooleanToShowJournal() {
		return journalBool;
	}

	public boolean getBooleanToShowAccuse() {
		return accuseBool;
	}

	public void setAccuse() {
		accuseBool = true;
	}

	public int getAccuseStateFlag() {
		// 0 means no character accused
		// 1 means character accused
		// 2 means room selected
		// 3 means weapon selected
		return accusation.size();
	}

	public void readInFile() {
		System.out.println(SingletonAssetManager.getInstance().getModelData());
		fileIn = new Scanner(SingletonAssetManager.getInstance().getModelData()
				.read());
		int count = 1;
		String current = "";

		Evidence ev = null;
		while (fileIn.hasNextLine()) {
			if (count % 5 == 0 && count > 0) {
				initialIsShown.put(ev, fileIn.nextLine());
			} else if ((count - 1) % 5 == 0) {
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

	public String getMonologue(String evidence_arg, int character) {
		return this.evidence.get(evidence_arg).getCharMonologue(character);
	}

	public void move(String character, Direction dir) {

		Point p = characters.get(character).getPos();
		int newX = p.getX();
		int newY = p.getY();

		for (Point point : roomTrans.keySet()) {
			if (point.getX() == newX && point.getY() == newY) {
				p.setX(roomTrans.get(point).getX());
				p.setY(roomTrans.get(point).getY());
			}
		}

		switch (dir) {
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

		for (String otherCharName : characters.keySet()) {
			if (otherCharName.equals(character))
				continue;
			Point p1 = characters.get(otherCharName).getPos();
			if (p1.getX() == newX && p1.getY() == newY)
				return;
		}

		if (c == null) {
			p.setX(newX);
			p.setY(newY);
		}

	}

	public void interact(String character) {
		Point p = characters.get(character).getPos();
		int[] dx = { 0, 1, 0, -1 };
		int[] dy = { -1, 0, 1, 0 };
		for (int i = 0; i < 4; i++) {

			boolean found = false;
			Point p1 = new Point(p.getX() + dx[i], p.getY() + dy[i]);
			for (Evidence ev : evidence.values()) {
				if (characters.containsKey(ev.getName())) {
					Character chara = characters.get(character);
					if (characters.get(ev.getName()).getPos().equals(p1)) {
						String mono = ev.getCharMonologue(chara
								.getCharAssignment());
						chara.setCollected(ev);
						this.setMessageOnScreen(mono);
						this.updateShownBasedOnCollected();
						found = true;
						break;
					}
				} else if (ev.getPos().equals(p1)) {
					// System.out.println(ev.getName());
					Character chara = characters.get(character);
					if (!chara.getIsShown(ev)) {
						this.setMessageOnScreen("Hmmmm");
						found = true;
						break;
					} else {
						String mono = ev.getCharMonologue(chara
								.getCharAssignment());
						chara.setCollected(ev);
						this.setMessageOnScreen(mono);
						this.updateShownBasedOnCollected();
						found = true;
						this.populateJournal(character);
						break;
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
							chara.setShown(this.evidence.get("knives"));
							chara.setShown(this.evidence.get("scuffs"));
						} else if (chara.getName().equals("Annie N.")) {
							chara.setShown(this.evidence.get("portrait"));
							chara.setShown(this.evidence.get("paper scrap"));
							chara.setShown(this.evidence.get("scotch glass"));
							chara.setShown(this.evidence.get("knives"));
							chara.setShown(this.evidence.get("scuffs"));
						} else if (chara.getName().equals("Scarlet Velvet")) {
							chara.setShown(this.evidence.get("snacks"));
							chara.setShown(this.evidence.get("manuscript"));
							chara.setShown(this.evidence.get("the purse"));
							chara.setShown(this.evidence.get("knives"));
							chara.setShown(this.evidence.get("scuffs"));
						}
					}

					if (ev.getName().equals("snacks")) {
						if (chara.getName().equals("Scarlet Velvet")) {
							chara.setShown(this.evidence.get("cable"));
						}
					}

					if (ev.getName().equals("poison book")) {
						if (chara.getName().equals("John Nicholson")) {
							chara.setShown(this.evidence.get("missing weapon"));
						}
					}

					if (ev.getName().equals("manuscript")) {
						if (chara.getName().equals("Scarlet Velvet")
								&& characters.get("programmer").getIsCollected(
										evidence.get("computer"))) {
							chara.setShown(this.evidence.get("computer"));
						}
					}

					if (ev.getName().equals("computer")) {
						if (chara.getName().equals("Scarlet Velvet")) {
							chara.setShown(this.evidence.get("missing weapon"));
						}
					}

					if (ev.getName().equals("missing weapon")) {
						if (chara.getName().equals("John Nicholson")) {
							chara.setShown(this.evidence.get("chair"));
						}
					}
					
					if (ev.getName().equals("chair")) {
						if (chara.getName().equals("John Nicholson")) {
							chara.setShown(this.evidence.get("candles"));
						}
					}
					
					if (ev.getName().equals("the purse")) {
						if (chara.getName().equals("Scarlet Velvet")) {
							chara.setShown(this.evidence.get("chair"));
						}
					}
					
					if (ev.getName().equals("scotch glass")) {
						if (chara.getName().equals("Annie N.")) {
							chara.setShown(this.evidence.get("cabinets"));
						}
					}
					
					if (ev.getName().equals("cabinets")) {
						if (chara.getName().equals("Annie N.")) {
							chara.setShown(this.evidence.get("pots"));
							chara.setShown(this.evidence.get("leftovers"));
						}
					}
					
					if (ev.getName().equals("cable")) {
						if (chara.getName().equals("Scarlet Velvet")) {
							chara.setShown(this.evidence.get("book"));
						}
					}

				}
			}
		}
	}

	public List<RenderData> getRenderData() {

		List<RenderData> res = new ArrayList<RenderData>();

		for (Evidence e : evidence.values()) {
			if (e.getName().equals("programmer")
					|| e.getName().equals("artist")
					|| e.getName().equals("writer")) {
				continue;
			} else {
				res.add(e);
			}
		}

		for (Character c : characters.values()) {
			res.add(c);
		}

		return res;
	}

	public void populateJournal(String character) {
		Map<Evidence, String> populateJournal = new HashMap<Evidence, String>();
		for (Evidence key : (characters.get(character)).getEvidenceMap()
				.keySet()) {
			if (characters.get(character).getIsCollected(key) == true) {
				populateJournal.put(key, key.getCharMonologue(characters.get(
						character).getCharAssignment()));
				if (!characters.get(character).getJournalIterator()
						.contains(key)) {
					LinkedList<Evidence> e = characters.get(character)
							.getJournalIterator();
					e.add(key);
					characters.get(character).setJournalIterator(e);
				}
			}
		}
		characters.get(character).setJournalLog(populateJournal);
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

	public Map<Point, Point> getRoomTrans() {
		return roomTrans;
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

	public boolean getPauseT() {
		return pauseT;
	}

	public void togglePauseT() {
		if (pauseT) {
			pauseT = false;
		} else {
			pauseT = true;
		}
	}

	public void addAccusationParam(int x) {
		if (accusation.size() > 3)
			return;
		accusation.add(x);
		if (accusation.size() == 3) {
			accuse();
		}
	}

	private void accuse() {
		if (accusation.equals(correctAccuse))
			win();
		else
			lose();
	}

	private void win() {
		System.out.println("You win!");

	}

	private void lose() {
		System.out.println("Try again!");

	}

}
