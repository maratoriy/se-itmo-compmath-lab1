package com.moratoium.compmath.lab1.model.linalg.matrix.exceptions;

import com.moratoium.compmath.lab1.model.linalg.exceptions.LinearAlgebraException;

public class InvalidMatrixSizeException extends LinearAlgebraException {
    public InvalidMatrixSizeException() {
        super("invalid matrix size");
    }
}
