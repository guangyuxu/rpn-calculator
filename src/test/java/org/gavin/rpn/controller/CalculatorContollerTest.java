package org.gavin.rpn.controller;


import org.gavin.rpn.exception.InsucientParametersException;
import org.gavin.rpn.persister.CalculatorHistory;
import org.gavin.rpn.persister.CalculatorStack;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import static org.junit.Assert.assertEquals;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("/spring/applicationContext.xml")
public class CalculatorContollerTest {
    @Autowired
    private CalculateController calculateController;
    @Autowired
    private CalculatorStack calculatorStack;
    @Autowired
    private  CalculatorHistory calculatorHistory;

    @Test
    public void testAddNumber(){
        calculatorStack.clear();
        calculatorHistory.clear();
        calculateController.execute("5 2");
        assertEquals("stack: 5 2", calculateController.stackToString());
    }

    @Test
    public void testSqrt(){
        calculatorStack.clear();
        calculatorHistory.clear();
        calculateController.execute("2 sqrt");
        assertEquals("stack: 1.4142135623", calculateController.stackToString());
        calculateController.execute("clear 9 sqrt");
        assertEquals("stack: 3", calculateController.stackToString());
    }

    @Test
    public void testSubtract(){
        calculatorStack.clear();
        calculatorHistory.clear();
        calculateController.execute("5 2 -");
        assertEquals("stack: 3", calculateController.stackToString());
        calculateController.execute("3 -");
        assertEquals("stack: 0", calculateController.stackToString());
        calculateController.execute("clear");
        assertEquals("stack:", calculateController.stackToString());
    }

    @Test
    public void testUndo(){
        calculatorStack.clear();
        calculatorHistory.clear();
        calculateController.execute("5 4 3 2");
        assertEquals("stack: 5 4 3 2", calculateController.stackToString());
        calculateController.execute("undo undo *");
        assertEquals("stack: 20", calculateController.stackToString());
        calculateController.execute("5 *");
        assertEquals("stack: 100", calculateController.stackToString());
        calculateController.execute("undo");
        assertEquals("stack: 20 5", calculateController.stackToString());
    }

    @Test
    public void testDivide(){
        calculatorStack.clear();
        calculatorHistory.clear();
        calculateController.execute("7 12 2 /");
        assertEquals("stack: 7 6", calculateController.stackToString());
        calculateController.execute("*");
        assertEquals("stack: 42", calculateController.stackToString());
        calculateController.execute("4 /");
        assertEquals("stack: 10.5", calculateController.stackToString());
    }

    @Test
    public void testNegative(){
        calculatorStack.clear();
        calculatorHistory.clear();
        calculateController.execute("1 2 3 4 5");
        assertEquals("stack: 1 2 3 4 5", calculateController.stackToString());
        calculateController.execute("*");
        assertEquals("stack: 1 2 3 20", calculateController.stackToString());
        calculateController.execute("clear 3 4 -");
        assertEquals("stack: -1", calculateController.stackToString());
    }

    @Test
    public void testConnectedMultiply(){
        calculatorStack.clear();
        calculatorHistory.clear();
        calculateController.execute("1 2 3 4 5");
        assertEquals("stack: 1 2 3 4 5", calculateController.stackToString());
        calculateController.execute("* * * *");
        assertEquals("stack: 120", calculateController.stackToString());
    }
    @Rule
    public ExpectedException thrown = ExpectedException.none();

    @Test(expected = InsucientParametersException.class)
    public void testInsufficient(){
        calculatorStack.clear();
        calculatorHistory.clear();
        calculateController.execute("1 2 3 * 5 + * * 6 5");
        assertEquals("11", calculateController.stackToString());
    }
}
