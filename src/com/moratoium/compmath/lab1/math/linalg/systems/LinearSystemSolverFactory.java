package com.moratoium.compmath.lab1.math.linalg.systems;

public class LinearSystemSolverFactory {

    static public LinearSystemSolver getSolver(LinearSolverType type, String... args) {
        switch (type) {
            case SIMPLE_ITERATION: {
                return new SimpleIterationLinearSolver(args[0]);
            }
            default:
                throw new NoClassDefFoundError();
        }
    }

}
