package org.t0tec.tutorials.pojo;

import java.awt.Point;
import java.io.Serializable;

public class SubObject implements Serializable {
	private int id;
	private Point point;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public Point getPoint() {
		return point;
	}

	public void setPoint(Point point) {
		this.point = point;
	}

	@Override
	public String toString() {
		return "[(" + point.x + ", " + point.y + ")]";
	}
}
