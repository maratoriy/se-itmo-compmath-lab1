package com.moratoium.compmath.lab1.model.linalg.exceptions;

public class LinearAlgebraException extends RuntimeException {
    public LinearAlgebraException(String message) {
        super(String.format("Linear algebra exception: %s", message));
    }
}
