package org.t0tec.tutorials.pojo;

import java.io.Serializable;
import java.util.HashMap;
import java.util.LinkedHashMap;

public class MainObject implements Serializable {
	private int id;
	private HashMap<Integer, SubObject> subObjects;
	
	
	public MainObject() {
		subObjects = new LinkedHashMap<Integer, SubObject>();
	}
	
	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public HashMap<Integer, SubObject> getSubObjects() {
		return subObjects;
	}

	public void setSubObjects(HashMap<Integer, SubObject> hashes) {
		this.subObjects = hashes;
	}

	@Override
	public String toString() {
		return "MainObject [id=" + id + ", subObjects=" + subObjects + "]";
	}
}
