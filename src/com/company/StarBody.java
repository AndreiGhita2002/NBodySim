package com.company;

import javafx.scene.paint.Color;

public class StarBody extends Body {
    private static final double solarMass = 1.98892e30;     // for constructor and printing (in kg)

    Double getRadius() {
        return mass / solarMass * sizeFactor;
    }

    StarBody(Double initX, Double initY, Double massInSolarMasses, Color color) {
        super(initX, initY, massInSolarMasses * solarMass, color);
    }
}
