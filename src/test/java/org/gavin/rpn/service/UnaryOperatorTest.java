package org.gavin.rpn.service;


import org.gavin.rpn.exception.InsucientParametersException;
import org.gavin.rpn.exception.UnsupportedCommandException;
import org.gavin.rpn.persister.CalculatorHistory;
import org.gavin.rpn.persister.CalculatorStack;
import org.gavin.rpn.util.Operation;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.math.BigDecimal;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("/spring/applicationContext.xml")
public class UnaryOperatorTest {
    @Autowired
    private UnaryOperator unaryOperator;
    @Autowired
    private CalculatorStack calculatorStack;
    @Autowired
    private CalculatorHistory calculatorHistory;

    @Test(expected = InsucientParametersException.class)
    public void emptyStackCauseException(){
        CalculatorStack stack = mock(CalculatorStack.class);
        CalculatorHistory history = mock(CalculatorHistory.class);
        UnaryOperator operator = new UnaryOperator(stack, history);
        when(stack.lessThan(anyInt())).thenReturn(true);
        operator.execute(Operation.ADD);
    }

    @Test(expected = UnsupportedCommandException.class)
    public void nonUnitaryOperationCauseException(){
        CalculatorStack stack = mock(CalculatorStack.class);
        CalculatorHistory history = mock(CalculatorHistory.class);
        UnaryOperator operator = new UnaryOperator(stack, history);
        when(stack.lessThan(anyInt())).thenReturn(false);
        operator.execute(Operation.DIVIDE);
    }

    @Test
    public void testSqrt(){
        calculatorStack.clear();
        calculatorHistory.clear();
        calculatorStack.push(new BigDecimal(2));
        calculatorHistory.add(null, new BigDecimal(2));
        calculatorStack.push(new BigDecimal(9));
        calculatorHistory.add(null, new BigDecimal(9));
        calculatorStack.push(new BigDecimal(25));
        calculatorHistory.add(null, new BigDecimal(25));

        unaryOperator.execute(Operation.SQRT);
        assertEquals(new BigDecimal(5), calculatorStack.pop());
        unaryOperator.execute(Operation.SQRT);
        assertEquals(new BigDecimal(3), calculatorStack.pop());
        unaryOperator.execute(Operation.SQRT);
        assertEquals("stack: 1.4142135623", calculatorStack.toString());
        BigDecimal popValue = calculatorStack.pop();
        assertEquals("1.414213562373095", popValue.toPlainString());
    }
}
