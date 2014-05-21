package org.t0tec.tutorials;

import java.io.Serializable;

// Certain system-level classes such as Thread, OutputStream and its subclasses, and 
// Socket are not serializable. If you serializable class contains such objects, it must mark then as "transient". 

public class Person implements Serializable {
	private String firstName;
	private String lastName;
	// stupid example for transient
	transient private Thread myThread;

	public Person(String firstName, String lastName) {
		this.firstName = firstName;
		this.lastName = lastName;
		this.myThread = new Thread();
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	@Override
	public String toString() {
		return "Person [firstName=" + firstName + ", lastName=" + lastName
				+ "]";
	}

}
