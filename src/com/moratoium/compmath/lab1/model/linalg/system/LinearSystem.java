package com.moratoium.compmath.lab1.model.linalg.system;

import com.moratoium.compmath.lab1.model.linalg.matrix.DoubleMatrix;
import com.moratoium.compmath.lab1.model.linalg.matrix.exceptions.InvalidMatrixSizeException;
import com.moratoium.compmath.lab1.model.linalg.matrix.MathSquareMatrix;

import java.util.Map;
import java.util.TreeMap;

public class LinearSystem extends DoubleMatrix {

    public LinearSystem(Double[][] matrix) {
        super(matrix);
        if(getColumns()<=1) {
            throw new InvalidMatrixSizeException();
        }
    }

    public LinearSystem(int rows, int columns) {
        super(rows, columns);
    }

    public boolean transformToDiagonalDominance() {
        final MathSquareMatrix ratios = getRatios();
        final Map<Integer, Integer> indexMap = new TreeMap<>();
        boolean oneStrict = false;
        for (int row = 0; row < ratios.getSize(); row++) {
            for (int column = 0; column < ratios.getSize(); column++) {
                final int elemDominance = ratios.elemDominance(row, column);
                if (elemDominance >= 0 && !indexMap.containsKey(column)) {
                    indexMap.put(column, row);
                    if (elemDominance == 1) oneStrict = true;
                }
            }
        }

        if(!oneStrict || indexMap.size()!=rows) {
            return false;
        }

        DoubleMatrix clone = new DoubleMatrix(getRows(), getColumns());
        for(int row = 0; row < rows; row++) {
            clone.setRow(row, getRow(row).clone());
        }

        for(int toRow : indexMap.keySet()) {
            int fromRow = indexMap.get(toRow);
            setRow(toRow, clone.getRow(fromRow));
        }

        return true;
    }

    public MathSquareMatrix getRatios() {
        MathSquareMatrix ratios = new MathSquareMatrix(getColumns()-1);
        for(int row = 0; row < ratios.getSize(); row++) {
            for(int column = 0; column < ratios.getSize(); column++) {
                ratios.set(row, column, get(row, column));
            }
        }
        return ratios;
    }

    public Double[] getFreeRatios() {
        return getColumn(getColumns()-1);
    }



}
