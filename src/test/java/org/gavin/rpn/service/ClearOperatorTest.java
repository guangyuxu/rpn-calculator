package org.gavin.rpn.service;


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

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("/spring/applicationContext.xml")
public class ClearOperatorTest {
    @Autowired
    private ClearOperator clearOperator;
    @Autowired
    private NumberOperator numberOperator;
    @Autowired
    private CalculatorStack calculatorStack;
    @Autowired
    private CalculatorHistory calculatorHistory;

    @Test
    public void clearNonEmpty(){
        calculatorStack.clear();
        calculatorHistory.clear();

        numberOperator.save(new BigDecimal("15.9"));
        numberOperator.save(new BigDecimal("15.9"));
        numberOperator.save(new BigDecimal("22.111"));
        numberOperator.save(new BigDecimal("-22.5"));
        numberOperator.save(new BigDecimal("-555.0000001"));
        numberOperator.save(new BigDecimal("4"));
        assertEquals(6, calculatorStack.size());
        assertEquals(6, calculatorHistory.size());
        clearOperator.execute(Operation.CLEAR);
        assertEquals(0, calculatorStack.size());
        assertEquals(0, calculatorHistory.size());

    }

    @Test
    public void clearEmpty(){
        calculatorStack.clear();
        calculatorHistory.clear();
        assertEquals(0, calculatorStack.size());
        assertEquals(0, calculatorHistory.size());
        clearOperator.execute(Operation.CLEAR);
        assertEquals(0, calculatorStack.size());
        assertEquals(0, calculatorHistory.size());
    }
}
