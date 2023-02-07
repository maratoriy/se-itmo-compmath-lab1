package com.moratoium.compmath.lab1.math.linalg;

public class DoubleMatrix extends Matrix<Double> {

    public DoubleMatrix(int rows, int columns) {
        super(new Double[rows][columns]);
    }

    public DoubleMatrix(Double[][] matrix) {
        super(matrix);
    }
}

