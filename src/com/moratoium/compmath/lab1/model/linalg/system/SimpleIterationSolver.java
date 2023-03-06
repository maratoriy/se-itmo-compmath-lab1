package com.moratoium.compmath.lab1.model.linalg.system;

import com.moratoium.compmath.lab1.model.linalg.matrix.DoubleMatrix;
import com.moratoium.compmath.lab1.model.linalg.matrix.MathSquareMatrix;
import com.moratoium.compmath.lab1.view.ConsoleTable;
import com.moratoium.compmath.lab1.view.StringTable;

import java.util.*;

public class SimpleIterationSolver implements LinearSystemSolver {
    private final double accuracy;

    public SimpleIterationSolver(double accuracy) {
        this.accuracy = accuracy;
    }

    @Override
    public Double[] solve(LinearSystem system) {
        System.err.printf("Starting solving system of size %dx%d with accuracy = %s\n", system.getRows(), system.getColumns(), String.valueOf(accuracy));
        if (system.getRatios().isDiagonalDominance() != MathSquareMatrix.DiagonalDominanceType.DOMINANCE) {
            System.err.println("Ratios aren't with diagonal dominance. Trying to transform");
            if (!system.transformToDiagonalDominance()) {
                System.err.println("Couldn't transform system to diagonal dominance");
                return null;
            }
            System.err.println("Transformed to diagonal dominance");
        }
        System.err.println("Starting iterations");
        DoubleMatrix initIteration = nextIteration(system);

        return iterate(initIteration, initIteration.getColumn(system.getColumns()-1));
    }

    private Double[] accuracyVector(Double[] prevApprox, Double[] nextApprox) {
        Double[] accuracyVector = new Double[prevApprox.length];
        for(int row = 0; row < accuracyVector.length; row++) {
            accuracyVector[row] = Math.abs(prevApprox[row] - nextApprox[row]);
        }
        return accuracyVector;
    }

    private Double maxAccuracy(Double[] accuracyVector) {
        double max = 0D;
        for (int row = 0; row < accuracyVector.length; row++) {
            max = Math.max(max, accuracyVector[row]);
        }
        return max;
    }


    private DoubleMatrix nextIteration(DoubleMatrix prevIteration) {
        DoubleMatrix nextIteration = new DoubleMatrix(prevIteration.getRows(), prevIteration.getColumns());

        for (int row = 0; row < prevIteration.getRows(); row++) {
            for (int column = 0; column < prevIteration.getColumns() - 1; column++) {
                nextIteration.set(row, column,
                        (row != column) ? -prevIteration.get(row, column) / prevIteration.get(row, row)
                                : 0F);
            }
            nextIteration.set(row, prevIteration.getColumns() - 1, prevIteration.get(row, prevIteration.getColumns() - 1) / prevIteration.get(row, row));
        }

        return nextIteration;
    }

    private Double[] getApprox(DoubleMatrix iteration, Double[] prevApprox) {
        Double[] nextApprox = new Double[prevApprox.length];
        for(int row = 0; row < iteration.getRows(); row++) {
            double newApprox = 0D;
            for(int column = 0; column < iteration.getColumns()-1; column++) {
                newApprox += iteration.get(row, column) * prevApprox[column];
            }
            nextApprox[row] = newApprox + iteration.get(row, iteration.getColumns()-1);
        }
        return nextApprox;
    }

    private Double[] iterate(DoubleMatrix initState, Double[] prevApprox) {
        final AnswerTable table = new AnswerTable(initState.getColumns()-1, prevApprox);

        Double[] nextApprox;
        Double[] accuracyVector;
        int iterationsCount = 1;
        for (; ; ) {
            nextApprox = getApprox(initState, prevApprox);
            accuracyVector = accuracyVector(prevApprox, nextApprox);
            Double maxAccuracy = maxAccuracy(accuracyVector);

            table.addRow(iterationsCount, nextApprox, maxAccuracy);

            if (this.accuracy >= maxAccuracy) {
                break;
            }

            iterationsCount++;
            prevApprox = nextApprox;
        }
        System.err.printf("Done in %d iteration\n", iterationsCount);
        System.out.println(new ConsoleTable(table));
        System.out.print(new ConsoleTable(new AccuracyTable(iterationsCount, accuracyVector)));

        return nextApprox;
    }

    private class AccuracyTable implements StringTable {
        static private final String x_format = "x_%d^k";
        private final List<String> titles;
        private final List<Map<String, String>> table;
        private final String accuracy_format;

        private final String second_col;

        {
            titles = new ArrayList<>();
            table = new ArrayList<>();
        }

        public AccuracyTable(int k, Double[] accuracyVector) {
            second_col = String.format("|x_i^(%d)-x_i^(%d)|", k, k-1);
            int number_of_zeroes = 0;
            double curAccur = accuracy;
            while(curAccur <= 1) {
                curAccur *= 10;
                number_of_zeroes++;
            }
            accuracy_format = "%."+number_of_zeroes+"f";

            titles.add("i");
            titles.add(second_col);

            for(int i = 0; i < accuracyVector.length; i++) {
                addRow(i, accuracyVector[i]);
            }
        }

        public void addRow(int i, Double acc) {
            Map<String, String> row = new HashMap<>();

            row.put("i", String.valueOf(i));
            row.put(second_col, String.format(accuracy_format, acc));

            table.add(row);
        }

        @Override
        public List<String> getTitles() {
            return titles;
        }

        @Override
        public List<Map<String, String>> getTable() {
            return table;
        }
    }

    private class AnswerTable implements StringTable {
        static private final String k_format = "k";
        static private final String x_format = "x_%d^k";
        static private final String max_col = "max|x_i^(k)-x_i^(k-1)|";

        private final int size;
        private final List<String> titles;
        private final List<Map<String, String>> table;
        private final String accuracy_format;

        {
            titles = new ArrayList<>();
            table = new ArrayList<>();
        }

        public AnswerTable(int size, Double[] initState) {
            this.size = size;
            int number_of_zeroes = 0;
            double curAccur = accuracy;
            while(curAccur <= 1) {
                curAccur *= 10;
                number_of_zeroes++;
            }
            accuracy_format = "%."+number_of_zeroes+"f";

            titles.add(k_format);
            for(int i = 0; i < this.size; i++) {
                titles.add(String.format(x_format, i+1));
            }
            titles.add(max_col);

            addRow(0, initState, null);

        }

        public void addRow(int iter, Double[] state, Double max) {
            Map<String, String> row = new HashMap<>();
            row.put(k_format, String.valueOf(iter));
            for(int i = 0; i < this.size; i++) {
                row.put(String.format(x_format, i+1), String.format(accuracy_format, state[i]));
            }
            row.put(max_col, (max==null) ? "-" : String.format(accuracy_format, max));
            table.add(row);
        }

        @Override
        public List<String> getTitles() {
            return titles;
        }

        @Override
        public List<Map<String, String>> getTable() {
            return table;
        }
    }
}