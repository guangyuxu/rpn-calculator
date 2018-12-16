package org.gavin.rpn.service;

import org.gavin.rpn.exception.InsucientParametersException;
import org.gavin.rpn.exception.UnsupportedCommandException;
import org.gavin.rpn.persister.CalculatorHistory;
import org.gavin.rpn.persister.CalculatorStack;
import org.gavin.rpn.util.Operation;

import java.math.BigDecimal;
import java.math.RoundingMode;

public class BinaryOperator implements Operator {
    private final CalculatorStack calculatorStack;
    private final CalculatorHistory calculatorHistory;
    private final int storeScale;
    private final RoundingMode roundingMode;

    public BinaryOperator(CalculatorStack calculatorStack, CalculatorHistory calculatorHistory, int storeScale, RoundingMode roundingMode) {
        this.calculatorStack = calculatorStack;
        this.calculatorHistory = calculatorHistory;
        this.storeScale = storeScale;
        this.roundingMode = roundingMode;
    }

    @Override
    public void execute(Operation operation) {
        validate(operation);

        BigDecimal input2 = calculatorStack.pop();
        BigDecimal input1 = calculatorStack.pop();
        try{
            BigDecimal output = execute(input1, input2, operation);
            calculatorStack.push(output);
            calculatorHistory.add(operation, output, input1, input2);
        }catch(InsucientParametersException ex){
            calculatorStack.push(input1);
            calculatorStack.push(input2);
            throw ex;
        }
    }

    @Override
    public void validate(Operation operation){
        if(calculatorStack.lessThan(2)) throw new InsucientParametersException(operation.getSymbol());

    }


    private BigDecimal execute(BigDecimal a, BigDecimal b, Operation operation){
        switch (operation){
            case ADD:
                return a.add(b);
            case SUBTRACT:
                return a.subtract(b);
            case MULTIPLY:
                return a.multiply(b);
            case DIVIDE:
                if(b.compareTo(BigDecimal.ZERO) == 0) throw new InsucientParametersException(operation.getSymbol());
                return a.divide(b, storeScale, roundingMode).stripTrailingZeros();
            default: throw new UnsupportedCommandException(operation.getSymbol());
        }
    }
}
