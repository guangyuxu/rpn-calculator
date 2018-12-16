package org.gavin.rpn.util;

import org.gavin.rpn.exception.UnsupportedCommandException;

public enum Operation {

    ADD("+"),
    SUBTRACT("-"),
    MULTIPLY("*"),
    DIVIDE("/"),

    SQRT("sqrt", OperationType.UNITARY_OPERATOR),

    CLEAR("clear", OperationType.COMMAND_OPERATOR),
    UNDO("undo", OperationType.COMMAND_OPERATOR);

    private final OperationType operationType;
    private final String symbol;

    Operation(String symbol) {
        this.symbol = symbol;
        this.operationType = OperationType.BINARY_OPERATOR;
    }

    Operation(String symbol, OperationType operationType) {
        this.symbol = symbol;
        this.operationType = operationType;
    }

    public String getSymbol() {
        return symbol;
    }

    public OperationType getOperationType() {
        return operationType;
    }

    public enum OperationType {
        UNITARY_OPERATOR, BINARY_OPERATOR, COMMAND_OPERATOR
    }

    public static Operation apply(String e) {
        for(Operation operation: Operation.values()){
            if(operation.symbol.equals(e)) return operation;
        }
        throw new UnsupportedCommandException(e);
    }
}

