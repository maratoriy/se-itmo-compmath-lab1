package com.moratoium.compmath.lab1.math.linalg;

import java.util.Arrays;
import java.util.stream.IntStream;

public class Matrix<T> {
    protected final T[][] matrix;
    private final int rows;
    private final int columns;

    public Matrix(T[][] matrix) {
        this.matrix = matrix;
        if (matrix.length > 0) {
            rows = matrix.length;
            columns = matrix[0].length;
        } else {
            rows = 0;
            columns = 0;
        }
    }



    public T[] getRow(int row) {
        if (row > rows) {
            throw new IndexOutOfBoundsException();
        }
        return matrix[row];
    }

    public void setRow(int row, T[] newRow) {
        if (row > rows || newRow.length != columns) {
            throw new IndexOutOfBoundsException();
        }
        matrix[row] = newRow;
    }

    public void setColumn(int column, T[] newColumn) {
        if (column > columns || newColumn.length != rows) {
            throw new IndexOutOfBoundsException();
        }
        IntStream.range(0, newColumn.length)
                .forEach(index ->
                        matrix[index][column] = newColumn[index]
                );
    }


    @SuppressWarnings("unchecked")
    public T[] getColumn(int column) {
        return (T[]) Arrays.stream(matrix).map(row -> row[column]).toArray(Object[]::new);
    }

    public void swapRows(int row1, int row2) {
        if (row1 > rows || row2 > rows) {
            throw new IndexOutOfBoundsException();
        }

        final T[] buf = getRow(row1);
        setRow(row1, getRow(row2));
        setRow(row2, buf);
    }

    public void swapColumns(int column1, int column2) {
        if (column1 > columns || column2 > columns) {
            throw new IndexOutOfBoundsException();
        }

        final T[] buf = getColumn(column1);
        setColumn(column1, getColumn(column2));
        setColumn(column2, buf);
    }

    public T get(int row, int column) {
        if (row > rows || column > columns) {
            throw new IndexOutOfBoundsException();
        }
        return matrix[row][column];
    }

    public void set(int row, int column, T value) {
        if (row > rows || column > columns) {
            throw new IndexOutOfBoundsException();
        }
        matrix[row][column] = value;
    }


    public void fill(MatrixFiller<T> filler) {
        for (int row = 0; row < rows; row++) {
            for (int column = 0; column < columns; column++) {
                set(row, column, filler.generate(row, column));
            }
        }
    }

    public void fill(T value) {
        fill((row, column) -> value);
    }


    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();
        for (int row = 0; row < rows; row++) {
            for (int column = 0; column < columns; column++) {
                builder.append(get(row, column));
                builder.append(" ");
            }
            builder.append("\n");
        }
        return builder.toString();
    }

    public int getColumns() {
        return columns;
    }

    public int getRows() {
        return rows;
    }

    public interface MatrixFiller<T> {
        T generate(int row, int column);
    }
}
