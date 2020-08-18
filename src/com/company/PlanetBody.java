package com.company;

import javafx.scene.paint.Color;

public class PlanetBody extends Body {
    private static final double earthMass = 5.9722e24;      // for constructor and printing (in kg)

    Double getRadius() {
        return mass / earthMass * sizeFactor;
    }

    PlanetBody(Double initX, Double initY, Double massInEarthMasses, Color color) {
        super(initX, initY, massInEarthMasses * earthMass, color);
    }
}
