package edu.virginia.cs.sgd.game.model;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

import com.badlogic.gdx.maps.MapObject;
import com.badlogic.gdx.maps.MapProperties;
import com.badlogic.gdx.maps.tiled.TiledMap;

import edu.virginia.cs.sgd.util.Point;
import edu.virginia.cs.sgd.viewer.RenderData;

public class Model {

	private Scanner fileIn;
	private File modelData;
	private TiledMap map;
	private Map<String, Evidence> evidenceList;
	private ArrayList<String> initialIsShown;

	public Model(TiledMap map) {
		this.map = map;
		
		Iterator<MapObject> i = map.getLayers().get("Evidence").getObjects().iterator();
		while(i.hasNext()) {
			MapObject o = i.next();
			
			System.out.println(o.getName());
		}
		
		evidenceList = new HashMap<String, Evidence>();
		initialIsShown = new ArrayList<String>();
		

		this.readInFile();
		
		Character programmer = new Character("John Nicholson", "0", new Point(0, 0), this.evidenceList, this.initialIsShown);
		Character artist = new Character("Scarlet Velvet", "1", new Point(0, 0), this.evidenceList, this.initialIsShown);
		Character writer = new Character("Annie N.", "2", new Point(0, 0), this.evidenceList, this.initialIsShown);

	}
	
	public void readInFile() {
		try {
			modelData = new File("ModelData.txt");
			fileIn = new Scanner(modelData);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		int count = 1;
		String current = "";

		while (fileIn.hasNextLine()) {
			if (count % 5 == 0 && count > 0) {
				initialIsShown.add(fileIn.nextLine());
			} else if ((count-1) % 5 == 0) {
				current = fileIn.nextLine();
				evidenceList.put(current, new Evidence(current, new Point(0,0)));
			} else {
				evidenceList.get(current).addToMonologues(fileIn.nextLine());
			}
			count++;
		}
		
		fileIn.close();
	}

	public static void main(String[] args) {
//		Model test = new Model(null);
//		for (String key: test.evidenceList.keySet()) {
//			System.out.println(test.evidenceList.get(key).getMonologues());
//		}
//		System.out.println(test.initialIsShown);
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

//		Cell c = ((TiledMapTileLayer) map.getLayers().get("Collision"))
//				.getCell(newX, newY);
	}
	
	public void interact(String character, Direction dir) {
		
	}
	
	public List<RenderData> getRenderData() {
		return new ArrayList<RenderData>();
	}
}
