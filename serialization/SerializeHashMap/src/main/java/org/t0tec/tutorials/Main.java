package org.t0tec.tutorials;

import java.awt.Point;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Random;

import org.t0tec.tutorials.pojo.MainObject;
import org.t0tec.tutorials.pojo.SubObject;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

public class Main {

	public static void main(String[] args) {
		Main main = new Main();
		main.complexObjectJsonSerialization();
	}
	
	private void hashMapToJSONSerialization() {
		Map<String, String> map = new LinkedHashMap<String, String>();
		map.put("key1", "value1");
		map.put("key2", "value2");
		map.put("key3", "value3");

		Gson gson = new Gson();

		// Serialize
		String json = gson.toJson(map);
		System.out.println("json: " + json);

		// Deserialize
		Map<String, String> deserializedMap = gson.fromJson(json, new TypeToken<Map<String, String>>() {}.getType());
		System.out.println("json: " + deserializedMap);
	}
	
	private void complexObjectJsonSerialization() {
		MainObject mObj = new MainObject();
		mObj.setId(new Random().nextInt(10));
		
		for (int i = 0; i < 5; i++) {
			SubObject subObj = new SubObject();
			subObj.setId(i);
			subObj.setPoint(new Point(i, i));
			mObj.getSubObjects().put(i, subObj);
		}
		
		Gson gson = new Gson();
		
		String json = gson.toJson(mObj);
		System.out.println("json: " + json);
		
		
		// Deserialize
		MainObject deserializedMObj = gson.fromJson(json, new TypeToken<MainObject>() {}.getType());
		System.out.println("json: " + deserializedMObj);
		

		
//	    Iterator<Entry<Integer, SubObject>> it = deserializedMObj.getSubObjects().entrySet().iterator();
//	    while (it.hasNext()) {
//	        Map.Entry pairs = (Map.Entry)it.next();
//	        System.out.println(pairs.getKey() + "/" + pairs.getValue());
//	        it.remove(); // avoids a ConcurrentModificationException
//	    }
	    	    
		
//		for (Map.Entry<Integer, SubObject> entry : deserializedMObj.getSubObjects().entrySet()) {
//			System.out.println(entry.getKey() + "/" + entry.getValue());
//		}
		
	}
	
	private void complexObjectToFileSerialization() {
		MainObject mObj = new MainObject();
		mObj.setId(new Random().nextInt(10));
		
		for (int i = 0; i < 5; i++) {
			SubObject subObj = new SubObject();
			subObj.setId(i);
			subObj.setPoint(new Point(i, i));
			mObj.getSubObjects().put(i, subObj);
		}
		
		String filename = "mainobject.ser";
		// save the object to file
		FileOutputStream fos = null;
		ObjectOutputStream out = null;
		try {
			fos = new FileOutputStream(filename);
			out = new ObjectOutputStream(fos);
			out.writeObject(mObj);

			out.close();
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		
		
		// read the object from file
		// save the object to file
		FileInputStream fis = null;
		ObjectInputStream in = null;
		try {
			fis = new FileInputStream(filename);
			in = new ObjectInputStream(fis);
			mObj = (MainObject) in.readObject();
			in.close();
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		
		System.out.println(mObj);
	}

}
