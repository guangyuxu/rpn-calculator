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
import java.math.RoundingMode;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.*;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("/spring/applicationContext.xml")
public class BinaryOperatorTest {
    @Autowired
    private BinaryOperator binaryOperator;
    @Autowired
    private CalculatorStack calculatorStack;
    @Autowired
    private CalculatorHistory calculatorHistory;

    @Test
    public void testAddDecimal(){
        calculatorStack.clear();
        calculatorStack.push(new BigDecimal("2.7"));
        calculatorStack.push(new BigDecimal("-59.004"));
        calculatorStack.push(new BigDecimal("11100.12345123451234512345"));
        calculatorStack.push(new BigDecimal("11100.12345123451234512345"));
        binaryOperator.execute(Operation.ADD);
        String stackToString = calculatorStack.toString();
        assertEquals("stack: 2.7 -59.004 22200.2469024690", stackToString);
    }

    @Test
    public void testSubDecimal(){
        calculatorStack.clear();
        calculatorStack.push(new BigDecimal("2.7"));
        calculatorStack.push(new BigDecimal("-59.004"));
        calculatorStack.push(new BigDecimal("11100.123445123451234512345"));
        calculatorStack.push(new BigDecimal("999999999999999999999999999999.12345123451234512345"));
        binaryOperator.execute(Operation.SUBTRACT);
        String stackToString = calculatorStack.toString();
        assertEquals("stack: 2.7 -59.004 -999999999999999999999999988899.0000061110", stackToString);
    }

    @Test
    public void testMultiDecimal(){
        calculatorStack.clear();
        calculatorStack.push(new BigDecimal("0.19999"));
        calculatorStack.push(new BigDecimal("-59.004"));
        calculatorStack.push(new BigDecimal("11100.123445123451234512345"));
        calculatorStack.push(new BigDecimal("999999999999999999999999999999.12345123451234512345"));
        binaryOperator.execute(Operation.MULTIPLY);
        String stackToString = calculatorStack.toString();
        assertEquals("stack: 0.19999 -59.004 11100123445123451233999999999990270.2004974164", stackToString);
    }

    @Test
    public void testDivideDecimal(){
        calculatorStack.clear();
        calculatorStack.push(new BigDecimal("0.19999"));
        calculatorStack.push(new BigDecimal("-59.003"));
        calculatorStack.push(new BigDecimal("33"));
        calculatorStack.push(new BigDecimal("12"));
        binaryOperator.execute(Operation.DIVIDE);
        String stackToString = calculatorStack.toString();
        assertEquals("stack: 0.19999 -59.003 2.75", stackToString);
        binaryOperator.execute(Operation.DIVIDE);
        stackToString = calculatorStack.toString();
        assertEquals("stack: 0.19999 -21.4556363636", stackToString);
    }

    @Test(expected = InsucientParametersException.class)
    public void emptyStackCauseException(){
        CalculatorStack stack = mock(CalculatorStack.class);
        CalculatorHistory history = mock(CalculatorHistory.class);
        BinaryOperator operator = new BinaryOperator(stack, history, 15, RoundingMode.DOWN);
        when(stack.lessThan(anyInt())).thenReturn(true);
        operator.execute(Operation.ADD);
    }

    @Test(expected = UnsupportedCommandException.class)
    public void nonBinaryOperationCauseException(){
        CalculatorStack stack = mock(CalculatorStack.class);
        CalculatorHistory history = mock(CalculatorHistory.class);
        BinaryOperator operator = new BinaryOperator(stack, history, 15, RoundingMode.DOWN);
        when(stack.lessThan(anyInt())).thenReturn(false);
        when(stack.pop()).thenReturn(new BigDecimal(2));
        operator.execute(Operation.CLEAR);
    }

    @Test(expected = InsucientParametersException.class)
    public void dividedByZeroCauseException(){
        CalculatorStack stack = mock(CalculatorStack.class);
        CalculatorHistory history = mock(CalculatorHistory.class);
        BinaryOperator operator = new BinaryOperator(stack, history, 15, RoundingMode.DOWN);
        when(stack.lessThan(anyInt())).thenReturn(false);
        when(stack.pop()).thenReturn(new BigDecimal(0));
        operator.execute(Operation.DIVIDE);
    }


}
