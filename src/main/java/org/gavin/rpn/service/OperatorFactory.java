package org.gavin.rpn.service;

import org.gavin.rpn.util.Operation;

public class OperatorFactory {

    private final UnaryOperator unaryOperator;
    private final BinaryOperator binaryOperator;
    private final UndoOperator undoOperator;
    private final ClearOperator clearOperator;
    private final NumberOperator numberOperator;

    public OperatorFactory(UnaryOperator unaryOperator, BinaryOperator binaryOperator, UndoOperator undoOperator, ClearOperator clearOperator, NumberOperator numberOperator) {
        this.unaryOperator = unaryOperator;
        this.binaryOperator = binaryOperator;
        this.undoOperator = undoOperator;
        this.clearOperator = clearOperator;
        this.numberOperator = numberOperator;
    }

    public Operator getCalOperator(Operation operation){
        switch (operation.getOperationType()){
            case UNITARY_OPERATOR:
                return unaryOperator;
            case BINARY_OPERATOR:
                return binaryOperator;
            case COMMAND_OPERATOR:
                return (Operation.UNDO.equals(operation))? undoOperator : clearOperator;
            default: return null;
        }
    }

    public NumberOperator getNumberOperator() {
        return numberOperator;
    }
}
