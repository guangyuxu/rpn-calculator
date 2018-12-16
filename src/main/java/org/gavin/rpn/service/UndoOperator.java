package org.gavin.rpn.service;

import org.gavin.rpn.exception.InsucientParametersException;
import org.gavin.rpn.persister.CalculateItem;
import org.gavin.rpn.persister.CalculatorHistory;
import org.gavin.rpn.persister.CalculatorStack;
import org.gavin.rpn.util.Operation;

import java.math.BigDecimal;

public class UndoOperator implements Operator  {
    private final CalculatorStack calculatorStack;
    private final CalculatorHistory calculatorHistory;

    public UndoOperator(CalculatorStack calculatorStack, CalculatorHistory calculatorHistory) {
        this.calculatorStack = calculatorStack;
        this.calculatorHistory = calculatorHistory;
    }

    @Override
    public void execute(Operation operation) {
        validate(operation);

        CalculateItem<BigDecimal> calculateItem = calculatorHistory.removeLast();
        for(int i = 0; i< calculateItem.getOutputSize(); i++){
            calculatorStack.pop();
        }
        for(BigDecimal b : calculateItem.getInputParams()){
            calculatorStack.push(b);
        }
    }

    @Override
    public void validate(Operation operation){
        if(calculatorHistory.isEmpty()) throw new InsucientParametersException(operation.getSymbol());

        // check is the stack data is enough for undo
        if(calculatorStack.lessThan(calculatorHistory.getLastOutputSize())){
            throw new InsucientParametersException(operation.getSymbol());
        }
    }
}