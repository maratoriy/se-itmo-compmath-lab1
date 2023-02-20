package com.moratoium.compmath.lab1.model.linalg.matrix;

public class DoubleMatrix extends Matrix<Double>{

    public DoubleMatrix(Double[][] matrix) {
        super(matrix);
    }

    public DoubleMatrix(int rows, int columns) {
        this(new Double[rows][columns]);
    }

    @Override
    public Double[] getColumn(int column) {
        return getColumn(column, Double[]::new);
    }
}
