package org.gavin.rpn.util;

import org.gavin.rpn.data.Element;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import static org.junit.Assert.*;


import java.util.List;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("/spring/applicationContext.xml")
public class LineParserTest {

    @Test
    public void singleStringToList(){
        String s = "  undo     ";
        List<Element> list = LineParser.parseLine(s);
        assertEquals(1, list.size());
        assertEquals(s.trim(), list.get(0).getCurrentElement());
        assertEquals(1, list.get(0).getPosition());
    }

    @Test
    public void doubleElements(){
        String s = " 123.100 30  ";
        List<Element> list = LineParser.parseLine(s);
        assertEquals(2, list.size());
        assertEquals("30", list.get(1).getCurrentElement());
        assertEquals(9, list.get(1).getPosition());
    }

    @Test
    public void multiElements(){
        String s = " 123.100 30 +    undo ";
        List<Element> list = LineParser.parseLine(s);
        assertEquals(4, list.size());
        assertEquals("undo", list.get(3).getCurrentElement());
        assertEquals(14, list.get(3).getPosition());
    }
}
