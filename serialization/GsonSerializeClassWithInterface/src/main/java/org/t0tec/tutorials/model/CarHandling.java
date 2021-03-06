package org.t0tec.tutorials.model;

import java.awt.Point;

public class CarHandling implements ICarHandling {
	private double acceleration;
	private double braking;
	private double steeringInput;
	private static final double TIME = 0.04;
	
	public CarHandling() {
		 this.acceleration = 333;
		 this.braking = -999;
		 this.steeringInput = 0.5;
	}
	
	public CarHandling(double acceleration, double braking, double steeringInput) {
		 this.acceleration = acceleration;
		 this.braking = braking;
		 this.steeringInput = steeringInput;
	}
	
	public double getAcceleration() {
		return acceleration;
	}

	public void setAcceleration(double acceleration) {
		this.acceleration = acceleration;
	}

	public double getBraking() {
		return braking;
	}

	public void setBraking(double braking) {
		this.braking = braking;
	}

	public double getSteeringInput() {
		return steeringInput;
	}

	public void setSteeringInput(double steeringInput) {
		this.steeringInput = steeringInput;
	}

	public double calculateVelocity(double currentVelocity, double roadResistance, boolean isAccelerating, boolean isBraking) {
		double currentAcceleration = roadResistance * currentVelocity;

		if (isAccelerating) {
			currentAcceleration += acceleration;
		}

		if (isBraking) {
			currentAcceleration += braking;
		}

		currentVelocity += currentAcceleration;

		if (currentVelocity < 0) {
			currentVelocity = 0;
		}

		return currentVelocity;
	}
	
	public double calculateSteeringInput(boolean steeringLeft, boolean steeringRight) {
		if (steeringLeft) {
			return steeringInput;
		} else if (steeringRight) {
			return -steeringInput;
		} else {
			return 0;
		}
	}
	
	public Point[] calculateNewCarPosition(Point rearTyres, Point frontTyres, double wheelBase, double velocity, double carOrientation, double steeringInput) {
		rearTyres.x += velocity * TIME * Math.cos(carOrientation);
		rearTyres.y += velocity * TIME * Math.sin(carOrientation);
		double baseline = wheelBase;
		double vt = velocity * TIME;
		double b = (baseline - vt) * Math.cos(steeringInput);
		double c = vt * (2 * baseline - vt);
		double distance = Math.sqrt(b * b + c) - b;
		frontTyres.x += distance * Math.cos(carOrientation + steeringInput);
		frontTyres.y += distance * Math.sin(carOrientation + steeringInput);

		Point result[] = { rearTyres, frontTyres };

		return result;
	}
	
	public double calculateNewOrientation(Point rearTyres, Point frontTyres) {
		// calculate orientation between two points
		return Math.atan2(frontTyres.y - rearTyres.y, frontTyres.x - rearTyres.x); 
	}

}