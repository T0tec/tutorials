package org.t0tec.tutorials;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

public class Main {
	public static void main(String[] args) {
		String filename = "person.ser";
		Person p = new Person("t0tec", "xipe");

		// save the object to file
		FileOutputStream fos = null;
		ObjectOutputStream out = null;
		try {
			fos = new FileOutputStream(filename);
			out = new ObjectOutputStream(fos);
			out.writeObject(p);

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
			p = (Person) in.readObject();
			in.close();
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		System.out.println(p);
	}
}
