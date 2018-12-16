package org.gavin.rpn.util;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.math.BigDecimal;

import static org.junit.Assert.*;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("/spring/applicationContext.xml")
public class BigDecimalFormatterTest {

    @Autowired
    private BigDecimalFormatter bigDecimalFormatter;

    @Test
    public void formatForStoreNormal(){
        BigDecimal b = bigDecimalFormatter.formatForStore(new BigDecimal("12.25"));
        assertEquals(2, b.scale());
    }
    @Test
    public void formatForStoreSameScale(){
        BigDecimal a = new BigDecimal("12.123456789012345");
        BigDecimal b = bigDecimalFormatter.formatForStore(a);
        assertEquals(15, b.scale());
        assertEquals(a, b);
    }

    @Test
    public void formatForStoreRoundUp(){
        BigDecimal a = new BigDecimal("12.1234567890123456");
        BigDecimal b = bigDecimalFormatter.formatForStore(a);
        assertEquals(new BigDecimal("12.123456789012345"), b);
    }

    @Test
    public void formatForStoreRoundDown(){
        BigDecimal a = new BigDecimal("12.1234567890123454");
        BigDecimal b = bigDecimalFormatter.formatForStore(a);
        assertEquals(new BigDecimal("12.123456789012345"), b);
    }

    @Test
    public void formatForPresentNormal(){
        BigDecimal b = bigDecimalFormatter.formatForPresent(new BigDecimal("12.25"));
        assertEquals(2, b.scale());
    }

    @Test
    public void formatForPresentSameScale(){
        BigDecimal a = new BigDecimal("12.0123456789");
        BigDecimal b = bigDecimalFormatter.formatForPresent(a);
        assertEquals(10, b.scale());
        assertEquals(a, b);
    }

    @Test
    public void formatForPresentRoundUp(){
        BigDecimal a = new BigDecimal("12.01234567837");
        BigDecimal b = bigDecimalFormatter.formatForPresent(a);
        assertEquals(new BigDecimal("12.0123456783"), b);
    }

    @Test
    public void formatForPresentRoundDown(){
        BigDecimal a = new BigDecimal("12.01234567833");
        BigDecimal b = bigDecimalFormatter.formatForPresent(a);
        assertEquals(new BigDecimal("12.0123456783"), b);
    }

}
