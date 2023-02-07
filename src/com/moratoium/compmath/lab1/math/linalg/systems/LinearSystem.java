package com.moratoium.compmath.lab1.math.linalg.systems;
import com.moratoium.compmath.lab1.math.linalg.DoubleMatrix;

import java.util.Arrays;

public class LinearSystem extends DoubleMatrix {

    public LinearSystem(int rows, int columns) {
        super(new Double[rows][columns]);
    }

    public LinearSystem(Double[][] matrix) {
        super(matrix);
    }

    private int isDiagDominance(int row, int column) {
        double diagElem = get(row, column);
        double sum = Arrays.stream(getRow(row)).reduce(-Math.abs(diagElem), (acc, iter) -> acc + Math.abs(iter));
        return Double.compare(diagElem, sum);
    }

}
