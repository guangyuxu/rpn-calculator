package org.gavin.rpn.persister;

import org.gavin.rpn.util.Operation;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.math.BigDecimal;
import static org.junit.Assert.*;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("/spring/applicationContext.xml")
public class CalculatorHistoryTest {
    @Autowired
    private CalculatorHistory calculatorHistory;

    @Test
    public void testAddAndRemove() {
        calculatorHistory.clear();
        calculatorHistory.add(Operation.ADD, new BigDecimal(32));
        calculatorHistory.add(Operation.ADD, new BigDecimal(32), new BigDecimal("-0.4"));
        calculatorHistory.add(Operation.ADD, new BigDecimal(32), new BigDecimal("-0.4"), new BigDecimal(4013298.10002));
        assertFalse(calculatorHistory.isEmpty());
        calculatorHistory.removeLast();
        assertFalse(calculatorHistory.isEmpty());
        calculatorHistory.removeLast();
        assertFalse(calculatorHistory.isEmpty());
        calculatorHistory.removeLast();
        assertTrue(calculatorHistory.isEmpty());
    }
}
