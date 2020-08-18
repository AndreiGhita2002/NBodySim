package com.company;

public class Vector {
    Double X;
    Double Y;

    Vector add(Vector otherVector) {
        Double nX = X + otherVector.X;
        Double nY = Y + otherVector.Y;

        return new Vector(nX, nY);
    }

    Vector invert() {
        return new Vector(-this.X, -this.Y);
    }

    Double getAngle() {
        return Math.atan(Y / X);
    }

    Vector(Double X, Double Y) {
        this.X = X;
        this.Y = Y;
    }

    Vector() {
        this(0.0, 0.0);
    }
}
