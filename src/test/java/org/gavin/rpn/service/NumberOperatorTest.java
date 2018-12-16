package org.gavin.rpn.service;


import org.gavin.rpn.persister.CalculatorHistory;
import org.gavin.rpn.persister.CalculatorStack;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.math.BigDecimal;

import static org.junit.Assert.assertEquals;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("/spring/applicationContext.xml")
public class NumberOperatorTest {
    @Autowired
    private  NumberOperator numberOperator;
    @Autowired
    private CalculatorStack calculatorStack;
    @Autowired
    private CalculatorHistory calculatorHistory;

    @Test
    public void testAddNumber(){
        calculatorStack.clear();
        calculatorHistory.clear();
        numberOperator.save(new BigDecimal("34.1"));
        numberOperator.save(new BigDecimal("-0.00001"));
        numberOperator.save(new BigDecimal("999999999999999999999999999999999999.88888888888888888888888888888888"));
        numberOperator.save(new BigDecimal("-999999999999999999999999999999999999999.7777777777777777777777777777777"));
        numberOperator.save(new BigDecimal("33333333333333333333333333333333333333333.3333333333333333333333333"));
        assertEquals(5, calculatorStack.size());
        assertEquals(5, calculatorHistory.size());
        String s = "stack: 34.1 -0.00001 999999999999999999999999999999999999.8888888888 -999999999999999999999999999999999999999.7777777777 33333333333333333333333333333333333333333.3333333333" ;
        assertEquals(s, calculatorStack.toString());
    }
}
