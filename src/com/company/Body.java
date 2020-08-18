package com.company;

import javafx.scene.paint.Color;

class Body {
    protected static final double G = 6.673e-11;              // gravitational constant
    protected static final double sizeFactor = 1.0;           // for on screen size
    private static final double testMass = 6.687E9;           // for testing; should be removed

    static Integer nextID = 0;

    Integer ID;
    Double X;
    Double Y;
    Vector velocity;
    Vector force;
    Double mass;
    Double radius;
    Color color;

    // using newtonian gravitation
    void addForce(Body otherBody) {

        double distX = X - otherBody.X;
        double distY = Y - otherBody.Y;
        double dist = Math.sqrt(distX * distX + distY * distY);

        if (dist == 0) return;

        double gravFieldStrength = G * otherBody.mass / dist;

        double weight = gravFieldStrength * mass;

        double ratioIX = distX / dist;
        double ratioIY = distY / dist;

        force.X -= weight * ratioIX;
        force.Y -= weight * ratioIY;
    }

    void update(double time) {
        velocity.X += time * force.X / mass;
        velocity.Y += time * force.Y / mass;

        X += time * velocity.X;
        Y += time * velocity.Y;

        resetForce();
    }

    private void resetForce() {
        force.X = 0.0;
        force.Y = 0.0;
    }

    double getDistanceBetween(Body otherBody) {
        double distX = X - otherBody.X;
        double distY = Y - otherBody.Y;
        return Math.sqrt(distX * distX + distY * distY);
    }

    private Double getRadius() {
        //TODO make a good one for kg body
//        return mass * sizeFactor;
        return sizeFactor * (mass / testMass);
    }

    Body(Double initX, Double initY, Double massInKg, Color color) {
        this.X = initX;
        this.Y = initY;
//        this.mass = massInKg;
        this.mass = massInKg * testMass;
        this.color = color;
        this.radius = getRadius();

        ID = nextID;
        nextID++;

        velocity = new Vector();
        force = new Vector();
    }

    public String toString() {
        return "" + ID + ": " + X + ", " + Y + "; " + velocity.X + ", " + velocity.Y + "; " + mass + " kg";
    }

    public String printCoords() {
        return "" + Math.round(X) + ", " + Math.round(Y);

    }
}
