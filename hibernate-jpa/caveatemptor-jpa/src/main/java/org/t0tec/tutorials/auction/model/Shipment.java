package org.t0tec.tutorials.auction.model;

import java.util.Date;

public class Shipment {
	private int inspectionPeriodDays;
	private ShipmentState state;
	private Date created;
	
	public int getInspectionPeriodDays() {
		return inspectionPeriodDays;
	}
	
	public ShipmentState getState() {
		return state;
	}
	
	public Date getCreated() {
		return created;
	}
	
}
