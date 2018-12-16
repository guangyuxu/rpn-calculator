package org.gavin.rpn.service;

import org.gavin.rpn.exception.InsucientParametersException;
import org.gavin.rpn.exception.UnsupportedCommandException;
import org.gavin.rpn.persister.CalculatorHistory;
import org.gavin.rpn.persister.CalculatorStack;
import org.gavin.rpn.util.Operation;

import java.math.BigDecimal;

public class UnaryOperator implements Operator {

    private final CalculatorStack calculatorStack;
    private final CalculatorHistory calculatorHistory;

    public UnaryOperator(CalculatorStack calculatorStack, CalculatorHistory calculatorHistory) {
        this.calculatorStack = calculatorStack;
        this.calculatorHistory = calculatorHistory;
    }

    @Override
    public void execute(Operation operation) {
        validate(operation);

        switch (operation){
            case SQRT:
                sqrt(operation);
                break;
            default: throw new UnsupportedCommandException(operation.getSymbol());
        }
    }

    @Override
    public void validate(Operation operation){
        if(calculatorStack.lessThan(1)) throw new InsucientParametersException(operation.getSymbol());
    }
    private void sqrt(Operation operation){
        BigDecimal input = calculatorStack.pop();
        BigDecimal output = new BigDecimal(StrictMath.sqrt(input.doubleValue()));
        calculatorStack.push(output);
        calculatorHistory.add(operation, output, input);
    }
}