package com.moratoium.compmath.lab1.math.linalg.systems;

public class SimpleIterationLinearSolver implements LinearSystemSolver {
    private final double accuracy;

    public SimpleIterationLinearSolver(String accuracy) {
        this.accuracy = Double.parseDouble(accuracy);
    }

    @Override
    public LinearSystemAnswer solve(LinearSystem system) {


        return new LinearSystemAnswer(new Double[]{1D, 2D, 3D, 4D});
    }
}
