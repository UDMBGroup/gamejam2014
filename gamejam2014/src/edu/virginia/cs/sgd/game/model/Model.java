package edu.virginia.cs.sgd.game.model;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

import com.badlogic.gdx.maps.MapProperties;
import com.badlogic.gdx.maps.tiled.TiledMap;

import edu.virginia.cs.sgd.util.Point;

public class Model {

	private Scanner fileIn;
	private File modelData;
	private TiledMap map;
	private ArrayList<String> evidenceList;
	private Map<String, ArrayList<String>> evidenceTable;

	public Model(TiledMap map) {
		this.map = map;
		evidenceList = new ArrayList<String>();
		evidenceTable = new HashMap<String, ArrayList<String>>();

		this.readInFile();
		
		Character programmer = new Character("John Nicholson", 0, new Point(0, 0), this.evidenceList);
		Character artist = new Character("Scarlet Velvet", 1, new Point(0, 0), this.evidenceList);
		Character writer = new Character("Annie N.", 2, new Point(0, 0), this.evidenceList);

	}
	
	public void readInFile() {
		try {
			modelData = new File("ModelData.txt");
			fileIn = new Scanner(modelData);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		int count = 1;
		ArrayList<String> temp = new ArrayList<String>();

		while (fileIn.hasNextLine()) {
			if (count % 5 == 0 && count > 0) {
				fileIn.nextLine();
			} else if ((count-1) % 5 == 0) {
				evidenceList.add(fileIn.nextLine());
			} else {
				temp.add(fileIn.nextLine());
			}
			if (temp.size() == 3) {
				evidenceTable.put(evidenceList.get(((count-1)/5)), temp);
				temp = new ArrayList<String>();
			}
			count++;
		}
		
		fileIn.close();
	}

	public static void main(String[] args) {
//		Model test = new Model(null);
//		System.out.println(test.evidenceTable);
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

	public String getMonologue(String evidence, int character) {
		return evidenceTable.get(evidence).get(character);
	}
	
}
