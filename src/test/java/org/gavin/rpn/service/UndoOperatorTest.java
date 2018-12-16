package org.gavin.rpn.service;


import org.gavin.rpn.exception.InsucientParametersException;
import org.gavin.rpn.persister.CalculatorHistory;
import org.gavin.rpn.persister.CalculatorStack;
import org.gavin.rpn.util.Operation;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.math.BigDecimal;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.*;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("/spring/applicationContext.xml")
public class UndoOperatorTest {
    @Autowired
    private UndoOperator undoOperator;
    @Autowired
    private UnaryOperator unaryOperator;
    @Autowired
    private BinaryOperator binaryOperator;
    @Autowired
    private NumberOperator numberOperator;
    @Autowired
    private CalculatorStack calculatorStack;
    @Autowired
    private CalculatorHistory calculatorHistory;

    @Test
    public void undoNumber(){
        calculatorStack.clear();
        calculatorHistory.clear();
        numberOperator.save(new BigDecimal("15.9"));
        numberOperator.save(new BigDecimal("15.9"));
        numberOperator.save(new BigDecimal("22.111"));
        numberOperator.save(new BigDecimal("-22.5"));
        numberOperator.save(new BigDecimal("-555.0000001"));
        numberOperator.save(new BigDecimal("4"));
        assertEquals("stack: 15.9 15.9 22.111 -22.5 -555.0000001 4", calculatorStack.toString());
        undoOperator.execute(Operation.UNDO);
        assertEquals("stack: 15.9 15.9 22.111 -22.5 -555.0000001", calculatorStack.toString());
        undoOperator.execute(Operation.UNDO);
        assertEquals("stack: 15.9 15.9 22.111 -22.5", calculatorStack.toString());
        undoOperator.execute(Operation.UNDO);
        assertEquals("stack: 15.9 15.9 22.111", calculatorStack.toString());
        undoOperator.execute(Operation.UNDO);
        assertEquals("stack: 15.9 15.9", calculatorStack.toString());
        undoOperator.execute(Operation.UNDO);
        assertEquals("stack: 15.9", calculatorStack.toString());
        undoOperator.execute(Operation.UNDO);
        assertEquals("stack:", calculatorStack.toString());
        //undoOperator.execute(Operation.UNDO);
    }

    @Test
    public void undoUnaryOperation(){
        calculatorStack.clear();
        calculatorHistory.clear();
        numberOperator.save(new BigDecimal("3"));
        numberOperator.save(new BigDecimal("9"));
        numberOperator.save(new BigDecimal("256"));
        unaryOperator.execute(Operation.SQRT);
        unaryOperator.execute(Operation.SQRT);
        unaryOperator.execute(Operation.SQRT);
        assertEquals("stack: 3 9 2", calculatorStack.toString());
        undoOperator.execute(Operation.UNDO);
        assertEquals("stack: 3 9 4", calculatorStack.toString());
        undoOperator.execute(Operation.UNDO);
        assertEquals("stack: 3 9 16", calculatorStack.toString());
        undoOperator.execute(Operation.UNDO);
        assertEquals("stack: 3 9 256", calculatorStack.toString());
    }

    @Test
    public void undoMultiOperation(){
        calculatorStack.clear();
        calculatorHistory.clear();
        numberOperator.save(new BigDecimal("1"));
        numberOperator.save(new BigDecimal("2"));
        numberOperator.save(new BigDecimal("3"));
        numberOperator.save(new BigDecimal("4"));
        numberOperator.save(new BigDecimal("5"));
        numberOperator.save(new BigDecimal("6"));
        binaryOperator.execute(Operation.ADD);
        binaryOperator.execute(Operation.MULTIPLY);
        binaryOperator.execute(Operation.SUBTRACT);
        binaryOperator.execute(Operation.MULTIPLY);
        binaryOperator.execute(Operation.DIVIDE);
        undoOperator.execute(Operation.UNDO);
        assertEquals("stack: 1 -82", calculatorStack.toString());
        undoOperator.execute(Operation.UNDO);
        assertEquals("stack: 1 2 -41", calculatorStack.toString());
        undoOperator.execute(Operation.UNDO);
        assertEquals("stack: 1 2 3 44", calculatorStack.toString());
        undoOperator.execute(Operation.UNDO);
        assertEquals("stack: 1 2 3 4 11", calculatorStack.toString());
        undoOperator.execute(Operation.UNDO);
        assertEquals("stack: 1 2 3 4 5 6", calculatorStack.toString());
    }


    @Test(expected = InsucientParametersException.class)
    public void historyCannotBeEmpty(){
        CalculatorStack stack = mock(CalculatorStack.class);
        CalculatorHistory history = mock(CalculatorHistory.class);
        UndoOperator operator = new UndoOperator(stack, history);
        when(history.isEmpty()).thenReturn(true);
        operator.execute(Operation.UNDO);
    }

    @Test(expected = InsucientParametersException.class)
    public void undoRequiredGreaterThanStackDeep(){
        CalculatorStack stack = mock(CalculatorStack.class);
        CalculatorHistory history = mock(CalculatorHistory.class);
        UndoOperator operator = new UndoOperator(stack, history);
        when(history.isEmpty()).thenReturn(true);
        when(history.getLastOutputSize()).thenReturn(1);
        when(stack.lessThan(1)).thenReturn(true);
        operator.execute(Operation.UNDO);
    }
}
