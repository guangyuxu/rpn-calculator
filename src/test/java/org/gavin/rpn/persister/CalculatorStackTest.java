package org.gavin.rpn.persister;

import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;

import java.lang.reflect.Field;
import java.math.BigDecimal;
import java.util.Stack;
import org.junit.Test;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import static org.junit.Assert.*;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("/spring/applicationContext.xml")
public class CalculatorStackTest {

    @Autowired
    private CalculatorStack calculatorStack;

    @Test
    public void testPushRound() throws NoSuchFieldException, IllegalAccessException {
        calculatorStack.clear();
        calculatorStack.push(new BigDecimal("1000.1234512345123452"));
        calculatorStack.push(new BigDecimal("13"));
        calculatorStack.push(new BigDecimal("-1000"));
        calculatorStack.push(new BigDecimal("-62430.1234512345123456"));
        Stack<BigDecimal> stack = getPrivateInnerStack();
        assertEquals(4, stack.size());
        assertEquals(new BigDecimal("-62430.123451234512345"), stack.peek());

        assertEquals("stack: 1000.1234512345 13 -1000 -62430.1234512345", calculatorStack.toString());
    }

    @Test
    public void testPushAndPop(){
        calculatorStack.clear();
        calculatorStack.push(new BigDecimal("42341.7"));
        calculatorStack.push(new BigDecimal("42341.7"));
        calculatorStack.push(new BigDecimal("0.7"));
        calculatorStack.push(new BigDecimal("-1.7"));
        calculatorStack.push(new BigDecimal("99999999.22"));

        assertFalse(calculatorStack.lessThan(5));
        calculatorStack.pop();
        assertTrue(calculatorStack.lessThan(5));

    }


    private Stack<BigDecimal> getPrivateInnerStack() throws NoSuchFieldException, IllegalAccessException {
        Field field = calculatorStack.getClass().getDeclaredField("stack");
        field.setAccessible(true);

        return (Stack<BigDecimal>)field.get(calculatorStack);
    }
}
