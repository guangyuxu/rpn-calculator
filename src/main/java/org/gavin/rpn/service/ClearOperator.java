package org.gavin.rpn.service;

import org.gavin.rpn.persister.CalculatorHistory;
import org.gavin.rpn.persister.CalculatorStack;
import org.gavin.rpn.util.Operation;

public class ClearOperator implements Operator {
    private final CalculatorStack calculatorStack;
    private final CalculatorHistory calculatorHistory;

    public ClearOperator(CalculatorStack calculatorStack, CalculatorHistory calculatorHistory) {
        this.calculatorStack = calculatorStack;
        this.calculatorHistory = calculatorHistory;
    }

    @Override
    public void execute(Operation operation) {
        validate(operation);
        calculatorStack.clear();
        calculatorHistory.clear();
    }

    @Override
    public void validate(Operation operation){}
}
