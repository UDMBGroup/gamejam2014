package edu.virginia.cs.sgd.game.model;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

import com.badlogic.gdx.maps.MapProperties;
import com.badlogic.gdx.maps.tiled.TiledMap;

public class Model {

	private Scanner fileIn;
	private File modelData;
	private TiledMap map;
	private ArrayList<String> evidenceList;
	private Map<String, ArrayList<String>> evidenceTable;
	private Map<String, Integer> charAssignment;

	public Model(TiledMap map) {
		this.map = map;
		evidenceList = new ArrayList<String>();
		evidenceTable = new HashMap<String, ArrayList<String>>();
		charAssignment = new HashMap<String, Integer>(3);
		
		charAssignment.put("programmer", 0);
		charAssignment.put("artist", 1);
		charAssignment.put("writer", 2);

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

	public String getMonologue(String evidence, String character) {
		return evidenceTable.get(evidence).get(charAssignment.get(character));
	}
}
