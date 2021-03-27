package nbody.sim;

public class Vector {
    Double X;
    Double Y;

    Vector add(Vector otherVector) {
        Double nX = X + otherVector.X;
        Double nY = Y + otherVector.Y;

        return new Vector(nX, nY);
    }

    Vector getInvert() {
        // returns an inverted vector
        return new Vector(-this.X, -this.Y);
    }

    Vector getProjectedOn(Vector otherVector) {
        double angle = Math.abs(this.getAngle() - otherVector.getAngle());
        double length = Math.cos(angle) * this.getDistance();

        double x = length * Math.cos(angle);
        double y = length * Math.sin(angle);

        return new Vector(x, y);
    }

    Vector getRotated90() {
        double cx = X;
        double cy = Y;
        if (X > 0) {
            if (Y > 0) { // quad 1
                return new Vector(-cy, cx);
            } else { // quad 4
                return new Vector(cy, -cx);
            }
        } else {
            if (Y > 0) { // quad 2
                return new Vector(cy, -cx);
            } else { // quad 3
                return new Vector(-cy, cx);
            }
        }
    }

    Double getDistance() {
        // returns the absolute value of the vector
        return Math.sqrt(X * X + Y * Y);
    }

    Double getAngle() {
        // return the angle of the vector and the OX axis
        return Math.atan(Y / X);
    }

    Vector resize(double size) {
        // changes the length of the vector while keeping its angle
        // this * k = unit
        // k = unit / this
        Double k = size / getDistance();
        X = X * k;
        Y = Y * k;
        return this;
    }

    Vector copy() {
        return new Vector(X, Y);
    }

    Vector(Double X, Double Y) {
        this.X = X;
        this.Y = Y;
    }

    Vector() {
        this(0.0, 0.0);
    }
}
