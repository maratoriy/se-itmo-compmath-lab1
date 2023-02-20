package com.moratoium.compmath.lab1.model.linalg.matrix;

import com.moratoium.compmath.lab1.model.linalg.matrix.exceptions.InvalidMatrixSizeException;

import java.util.Arrays;

public class MathSquareMatrix extends DoubleMatrix {

    protected final int size;

    public MathSquareMatrix(Double[][] matrix) {
        super(matrix);
        if(rows != columns) {
            throw new InvalidMatrixSizeException();
        }
        this.size = rows;
    }

    public MathSquareMatrix(int size) {
        super(new Double[size][size]);
        if(rows != columns) {
            throw new InvalidMatrixSizeException();
        }
        this.size = rows;
    }

    @Override
    public Double[] getColumn(int column) {
        return getColumn(column, Double[]::new);
    }

    public int elemDominance(int row, int column) {
        Double elem = Math.abs(get(row, column));
        Double sum = Arrays.stream(getRow(row)).reduce(-elem, (a, b) -> a + Math.abs(b));
        return Double.compare(elem, sum);
    }

    public DiagonalDominanceType isDiagonalDominance() {
        boolean oneStrict = false;
        for (int i = 0; i < size; i++) {
            final int elemDominance = elemDominance(i, i);
            if (elemDominance == -1) return DiagonalDominanceType.NOT_DOMINANCE;
            if (elemDominance == 1) oneStrict = true;
        }
        if (!oneStrict) return DiagonalDominanceType.NO_ONE_STRICT_DOMINANCE;
        return DiagonalDominanceType.DOMINANCE;
    }

    public int getSize() {
        return size;
    }


    public enum DiagonalDominanceType {
        NOT_DOMINANCE,
        NO_ONE_STRICT_DOMINANCE,
        DOMINANCE
    }

}
