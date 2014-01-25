package edu.virginia.cs.sgd.game.model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import com.badlogic.gdx.maps.tiled.TiledMap;

public class Model {

	private TiledMap map;
	private ArrayList<String> evidenceList;
	private Map<String, ArrayList<String>> evidenceTable;

	public Model(TiledMap map) {
		this.map = map;
		evidenceList = new ArrayList<String>();
		evidenceTable = new HashMap<String, ArrayList<String>>();
		
//		evidenceList.add("");
		
//		for (String ev; evidenceList) {
//			evidenceTable.put(ev, )
//		}
	}

	public static void main(String[] args) {

	}

}
