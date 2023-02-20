package com.moratoium.compmath.lab1.model.linalg.matrix;

import com.moratoium.compmath.lab1.model.linalg.matrix.exceptions.InvalidMatrixSizeException;

import java.util.Arrays;
import java.util.function.IntFunction;

abstract public class Matrix<T> {
    private final T matrix[][];
    protected final int rows;
    protected final int columns;

    public Matrix(T[][] matrix) {
        this.matrix = matrix;
        if (matrix.length == 0 || matrix[0].length == 0) {
            throw new InvalidMatrixSizeException();
        }
        this.rows = matrix.length;
        this.columns = matrix[0].length;
        for (int row = 0; row < rows; row++) {
            if (getRow(row).length != columns) {
                throw new InvalidMatrixSizeException();
            }
        }
    }

    public void swapRows(int row1, int row2) {
        if (row1 >= rows || row2 >= rows) {
            throw new IndexOutOfBoundsException();
        }
        T[] buf = getRow(row1).clone();
        setRow(row1, getRow(row2));
        setRow(row2, buf);
    }

    public void swapColumns(int column1, int column2) {
        if (column1 >= columns || column2 >= columns) {
            throw new IndexOutOfBoundsException();
        }
        for (int row = 0; row < rows; row++) {
            T buf = matrix[row][column1];
            matrix[row][column1] = matrix[row][column2];
            matrix[row][column2] = buf;
        }
    }

    public T[] getRow(int row) {
        if (row >= rows) {
            throw new IndexOutOfBoundsException();
        }
        return matrix[row];
    }

    public void setRow(int row, T[] newRow) {
        if (newRow.length != columns) {
            throw new IndexOutOfBoundsException();
        }
        System.arraycopy(newRow, 0, matrix[row], 0, columns);
    }

    abstract public T[] getColumn(int column);

    protected T[] getColumn(int column, IntFunction<T[]> constructor) {
        if (column > columns) {
            throw new IndexOutOfBoundsException();
        }
        return (T[]) Arrays.stream(matrix).map(x -> x[column]).toArray(constructor);
    }

    public void setColumn(int column, T[] newColumn) {
        if (column >= rows) {
            throw new IndexOutOfBoundsException();
        }
        for (int row = 0; row < rows; row++) {
            matrix[row][column] = newColumn[row];
        }
    }

    public T get(int row, int column) {
        if (row >= rows || column >= columns) {
            throw new IndexOutOfBoundsException();
        }
        return matrix[row][column];
    }

    public void set(int row, int column, T value) {
        if (row >= rows || column >= columns) {
            throw new IndexOutOfBoundsException();
        }
        matrix[row][column] = value;
    }

    public int getRows() {
        return rows;
    }

    public int getColumns() {
        return columns;
    }


    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();
        Arrays.stream(matrix).forEach(row -> {
            Arrays.stream(row).forEach(elem -> {
                builder.append(elem.toString());
                builder.append(" ");
            });
            builder.append("\n");
        });
        return builder.toString();
    }
}
