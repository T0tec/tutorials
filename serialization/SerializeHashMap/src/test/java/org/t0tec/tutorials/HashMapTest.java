package org.t0tec.tutorials;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.HashMap;

import org.junit.Assert;
import org.junit.Test;

public class HashMapTest {
	@Test
	public void testHashMapSerialization() throws IOException, ClassNotFoundException {
		HashMap<Key, Integer> map = new HashMap<Key, Integer>();
		map.put(new Key("abc"), 1);
		map.put(new Key("def"), 2);

		ByteArrayOutputStream out = new ByteArrayOutputStream();
		ObjectOutputStream objOut = new ObjectOutputStream(out);
		objOut.writeObject(map);
		objOut.close();
		Key.xor = 0x7555AAAA; // make the hashcodes different
		ObjectInputStream objIn = new ObjectInputStream(new ByteArrayInputStream(out.toByteArray()));
		HashMap actual = (HashMap) objIn.readObject();
		// now try to get a value
		Assert.assertEquals(2, actual.get(new Key("def")));
	}

	static class Key implements Serializable {
		private String keyString;
		static int xor = 0;

		Key(String keyString) {
			this.keyString = keyString;
		}

		@Override
		public int hashCode() {
			return keyString.hashCode() ^ xor;
		}

		@Override
		public boolean equals(Object obj) {
			Key otherKey = (Key) obj;
			return keyString.equals(otherKey.keyString);
		}
	}
}
