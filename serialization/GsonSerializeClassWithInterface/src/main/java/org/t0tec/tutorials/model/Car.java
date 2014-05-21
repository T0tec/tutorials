package org.t0tec.tutorials.model;

import java.awt.Point;
import java.awt.event.KeyEvent;

public class Car {    
    private static final int SPEEDCOEFFICIENT = 100;
    private static final double WHEELBASE = 3000;
    
    private double carVelocity;
    private double carOrientation;
    private double steeringInput;
    private boolean isAccelerating;
    private boolean isBraking;
    private boolean isSteeringLeft;
    private boolean isSteeringRight;
    
    private ICarHandling carHandling;   
    private Point rearTyres;
	private Point frontTyres;    
    private double roadResistance;
    
    private String carImageLocation;
    
	public String getCarImageLocation() {
		return carImageLocation;
	}
    
    public Car(ICarHandling carHandling, String carImageLocation) {
		this.carVelocity = 0;
		this.carOrientation = 0;
		this.steeringInput = 0;
		this.isAccelerating = false;
		this.isBraking = false;
		this.isSteeringLeft = false;
		this.isSteeringRight = false;
		this.carHandling = carHandling;
    	this.roadResistance = -0.01;
    	this.carImageLocation = carImageLocation;
    }
    
    private void changeVelocity() {
        this.carVelocity = this.carHandling.calculateVelocity(this.carVelocity, this.roadResistance, this.isAccelerating, this.isBraking);
    }
    
    public void updatePosition() {
        calculateSteeringInput();      
        changeVelocity();
        
        Point[] newPosition = this.carHandling.calculateNewCarPosition(this.rearTyres, this.frontTyres, WHEELBASE, this.carVelocity, this.carOrientation, this.steeringInput);

        this.rearTyres = newPosition[0];
        this.frontTyres = newPosition[1];
        
        this.carOrientation = this.carHandling.calculateNewOrientation(this.rearTyres, this.frontTyres);
    }

    public void accelerate(boolean isAccelerating) {
        this.isAccelerating = isAccelerating;
    }

    public void brake(boolean isBraking) {
        this.isBraking = isBraking;
    }

    public void steerLeft(boolean isSteeringLeft) {
        this.isSteeringLeft = isSteeringLeft;
    }

    public void steerRight(boolean isSteeringRight) {
        this.isSteeringRight = isSteeringRight;
    }
    
    private void calculateSteeringInput() {
		this.steeringInput = this.carHandling.calculateSteeringInput(this.isSteeringLeft, this.isSteeringRight);
	}
    
    public void handleKeyInput(int key, boolean isKeyDown) { // 38, 40, 39, 37
		if (key == KeyEvent.VK_UP) {
			accelerate(isKeyDown);
		} else if (key == KeyEvent.VK_DOWN) {
			brake(isKeyDown);
		} else if (key == KeyEvent.VK_RIGHT) {
			steerRight(isKeyDown);
		} else if (key == KeyEvent.VK_LEFT) {
			steerLeft(isKeyDown);
		}
	}

	public ICarHandling getCarHandling() {
		return this.carHandling;
	}
    
    public void setCarHandling(ICarHandling carHandling) {
		this.carHandling = carHandling;
	}
    
    public double getOrientation() {
		return this.carOrientation;
	}
    
    public void setOrientation(double carOrientation) {
		this.carOrientation = carOrientation;
        
		Point position = new Point(getCenterPosition());
        
		this.frontTyres = new Point((int) (position.x * SPEEDCOEFFICIENT + WHEELBASE / 2 * Math.cos(this.carOrientation)), 
                (int) (position.y * SPEEDCOEFFICIENT + WHEELBASE / 2 * Math.sin(this.carOrientation)));
		this.rearTyres = new Point((int) (position.x * SPEEDCOEFFICIENT - WHEELBASE / 2 * Math.cos(this.carOrientation)), 
                (int) (position.y * SPEEDCOEFFICIENT - WHEELBASE / 2 * Math.sin(this.carOrientation)));
	}
    
    public Point getCenterPosition() {
		return new Point((this.frontTyres.x + this.rearTyres.x) / 100 / 2, (this.frontTyres.y + this.rearTyres.y) / 100 / 2);
	}

    public void setCenterPosition(Point position) {
        this.frontTyres = new Point((int) (position.x * SPEEDCOEFFICIENT + WHEELBASE / 2 * Math.cos(this.carOrientation)),
                (int) (position.y * SPEEDCOEFFICIENT + WHEELBASE / 2 * Math.sin(this.carOrientation)));
        this.rearTyres = new Point((int) (position.x * SPEEDCOEFFICIENT - WHEELBASE / 2 * Math.cos(this.carOrientation)),
                (int) (position.y * SPEEDCOEFFICIENT - WHEELBASE / 2 * Math.sin(this.carOrientation)));
    }

	public void resetCar() {
		this.isBraking = false;
		this.isAccelerating = false;
		this.isSteeringLeft = false;
		this.isSteeringRight = false;

		this.carVelocity = 0;
	}

	public void changeRoadResistance() {
		this.roadResistance = -0.1;
		
	}

	public void resetRoadResitance() {
		this.roadResistance = -0.01;		
	}
}
