package org.t0tec.tutorials.model;

import java.awt.Point;

public interface ICarHandling {
    
    public double calculateVelocity(double velocity, double roadResistance, boolean isAccelerating, boolean isBraking);
    
    public double calculateSteeringInput(boolean steeringLeft, boolean steeringRight);
    
    public Point[] calculateNewCarPosition(Point rearTyres, Point frontTyres, double wheelBase, double velocity, double carOrientation, double steeringInput);
    
    public double calculateNewOrientation(Point rearTyres, Point frontTyres);
}
