package org.gavin.rpn.service;

import org.gavin.rpn.persister.CalculatorHistory;
import org.gavin.rpn.persister.CalculatorStack;

import java.math.BigDecimal;

public class NumberOperator {
    private final CalculatorStack calculatorStack;
    private final CalculatorHistory calculatorHistory;

    public NumberOperator(CalculatorStack calculatorStack, CalculatorHistory calculatorHistory) {
        this.calculatorStack = calculatorStack;
        this.calculatorHistory = calculatorHistory;
    }

    public void save(BigDecimal b) {
        calculatorStack.push(b);
        calculatorHistory.add(null, b);
    }
}
