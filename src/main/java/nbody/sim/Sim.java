package nbody.sim;

import javafx.scene.paint.Color;

import java.util.ArrayList;
import java.util.List;

class Sim {

    private final Integer XAxisLength;    //TODO bigger resolution
    private final Integer YAxisLength;

    List<Body> bodyList = new ArrayList<>();
    Integer simCycleNum = 0;
    Boolean shouldRun = true;

    // debugging options:
    Boolean printBoundariesExits = false;
    Boolean printEachCycle = false;

    private void addBody(Double X, Double Y, Double mass, Color color) {
        bodyList.add(new Body(X, Y, mass, color));

    }

    void doForces() {
        // adding forces
        for (Body body : bodyList) {
            for (Body otherBody : bodyList) {
                if (! body.equals(otherBody)) {
                    body.addForce(otherBody);
                }
            }
        }
    }

    void checkCollisions() {
        for (Body body : bodyList) {
            // checking for boundary collision
            if (body.pos.X > XAxisLength / 2.0) {   //check for the right border
                body.velocity.X = -body.velocity.X;
                if (printBoundariesExits) {
                    System.out.println("Body " + body.ID + " passed the left border");
                }
            }
            if (body.pos.X < -XAxisLength / 2.0) {  //check for the left border
                body.velocity.X = -body.velocity.X;
                if (printBoundariesExits) {
                    System.out.println("Body " + body.ID + " passed the right border");
                }
            }
            if (body.pos.Y > YAxisLength / 2.0) {   //check for the top border
                body.velocity.Y = -body.velocity.Y;
                if (printBoundariesExits) {
                    System.out.println("Body " + body.ID + " passed the top border");
                }
            }
            if (body.pos.Y < -YAxisLength / 2.0) {  //check for the bottom border
                body.velocity.Y = -body.velocity.Y;
                if (printBoundariesExits) {
                    System.out.println("Body " + body.ID + " passed the bottom border");
                }
            }

            // checking for a collision with another body
            for (Body otherBody : bodyList) {
                if (!body.equals(otherBody) && !body.collided && !otherBody.collided) {
                    if (body.getDistanceBetween(otherBody) <= body.radius + otherBody.radius) {
                        System.out.println("collision between " + body.ID + " and " + otherBody.ID);
                        doCollision(body, otherBody);
                    }
                }
            }
        }
    }

    void doCollision(Body body1, Body body2) {
        // doing collision between 2 objects
        // calculate the unit vector for tangent and normal directions
        // use 1D formulas for normal direction
        // tangent velocities don't change
        // convert to x and y force change

        // calculating the normal vector
        Vector normal = body1.pos.getInvert().add(body2.pos);

        Vector normal1 = body1.velocity.getProjectedOn(normal);
        Vector normal2 = body2.velocity.getProjectedOn(normal);

        normal1.resize((normal1.getDistance() * (body1.mass - body2.mass) + 2 * body2.mass * normal2.getDistance()) / (body1.mass + body2.mass));
        normal2.resize((normal2.getDistance() * (body2.mass - body1.mass) + 2 * body1.mass * normal1.getDistance()) / (body1.mass + body2.mass));

        Vector tangent = normal.getRotated90();
        Vector tangent1 = tangent.copy().resize(body1.velocity.getProjectedOn(tangent).getDistance());
        Vector tangent2 = tangent.copy().resize(body2.velocity.getProjectedOn(tangent).getDistance());

        body1.velocity = normal1.add(tangent1);
        body2.velocity = normal2.add(tangent2);
        body1.collided = true;
        body2.collided = true;
    }

    void update(Double cyclePeriod) {
        // updating the bodies
        for (Body body : bodyList) {
            body.update(cyclePeriod);
        }
        if (printEachCycle) printBodies();
        simCycleNum++;
    }

    void printBodies() {
        System.out.println("Cycle " + simCycleNum + ": ");
        for (Body body : bodyList) {
            System.out.println(body.toString());
        }
        System.out.println();
    }

    Sim(int bodyNum, int W, int H) {
        XAxisLength = W;
        YAxisLength = H;

//        for (int i = 0; i < bodyNum; i++) {
//          // TODO add bodyNum thing
//        }

        addBody(-240.0, -120.0, 20.0, Color.BLUE);
        addBody(190.0, 170.0, 15.0, Color.RED);
        addBody(390.0, -220.0, 30.0, Color.YELLOW);
        addBody(0.0, 0.0, 60.0, Color.GREEN);
//        addBody(200.0, 31.0, 6.0, Color.PURPLE);
//        addBody(-201.0, 31.0, 6.0, Color.PURPLE);
//        addBody(-300.0, -32.0, 6.0, Color.PURPLE);
//        addBody(-21.0, 302.0, 6.0, Color.PURPLE);
//        addBody(-200.0, 303.0, 6.0, Color.PURPLE);
        addBody(200.0, -100.0, 7.0, Color.ORANGE);

    }
}
