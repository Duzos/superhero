package com.duzo.superhero.util.spiderman;

public class PendulumCalculations {
    // gravity
    public double gravity = 1.0;

    // mass
    public double mass1 = 1.0;

    // angle
    public double angle1 = 0.0;
    public double angle2 = 0.0;

    // angular velocity
    public double velocity1 = 0.0;
    public double velocity2 = 0.0;

    public double pendulumCalc(double length, double angle) {

        double g = gravity;
        double m1 = mass1;
        double m2 = mass1;
        double a1 = angle;
        double a2 = angle;
        double l1 = length;
        double l2 = length;
        double v1 = velocity1;
        double v2 = velocity2;

        double acc1T1 = -g * (2 * m1 + m2) * Math.sin(a1);
        double acc1T2 = -m2 * g * Math.sin(a1 - 2 * a2);
        double acc1T3 = -2 * Math.sin(a1 - a2) * m2;
        double acc1T4 = Math.sqrt(v2) * l2 + v1 * v1 * l1 * Math.cos(a1 - a2);
        double acc1B = l1 * (2 * m1 + m2 - m2 * Math.cos(2 * a1 - 2 * a2));
        double acc1 = (acc1T1 + acc1T2 + acc1T3 * acc1T4) / acc1B;
        double acc2T1 = 2 * Math.sin(a1 - a2);
        double acc2T2 = Math.sqrt(v1) * l1 * (m1 + m2);
        double acc2T3 = g * (m1 + m2) * Math.cos(a1);
        double acc2T4 = Math.sqrt(v2) * l2 * m2 * Math.cos(a1 - a2);
        double acc2B = l2 * (2 * m1 + m2 - m2 * Math.cos(2 * a1 - 2 * a2));
        double acc2 = acc2T1 * (acc2T2 + acc2T3 + acc2T4) / acc2B;

        // velocity += acceleration
        velocity1 += acc1;
        velocity2 += acc2;

        // angle += velocity
        angle1 += velocity1;
        angle2 += velocity2;

        return angle1;
    }
}