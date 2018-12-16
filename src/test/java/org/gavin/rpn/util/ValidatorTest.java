package org.gavin.rpn.util;


import org.gavin.rpn.exception.UnsupportedCommandException;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.List;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("/spring/applicationContext.xml")
public class ValidatorTest{

    private static List<String> numberList;
    private static List<String> operationList;
    private static List<String> illegalList;

    @Before
    public void setup(){
        numberList = new ArrayList<String>(){{
            add("0");
            add("341");
            add("9999999999999999999999999999999999");
            add("9999999999999999999999999999999999.999999999999999999999999999999");
            add("-28");
            add("0.00000031");
            add("-15.123412");
            add("-999999999999999999999999999999999");
            add("-999999999999999999999999999999999.9999999999999999999999999999999");
            add("-0.00000012");
        }};
        operationList = new ArrayList<String>(){{
            add("+");
            add("-");
            add("*");
            add("/");
            add("sqrt");
            add("undo");
            add("clear");
        }};
        illegalList = new ArrayList<String>(){{
            add("+-*/");
            add("asbdfw");
            add("3..7697");
            add("a01b");
            add("!01293");
            add("4.3.0");
            add("cleard");
        }};
    }

    @Test
    public void numberIsNumber(){
        for(String s : numberList){
            assertTrue(Validator.isNumber(s));
        }
    }
    @Test
    public void operatorIsNotNumber(){
        for(String s : operationList){
            assertFalse(Validator.isNumber(s));
        }
    }
    @Test
    public void illegalIsNotNumber(){
        for(String s : illegalList){
            assertFalse(Validator.isNumber(s));
        }
    }

    @Test
    public void operationIsOperation(){
        for(String s : operationList){
            assertTrue(Validator.isOperation(s));
        }
    }
    @Test
    public void numberIsNotOperation(){
        for(String s : numberList){
            assertFalse(Validator.isOperation(s));
        }
    }
    @Test
    public void illegalIsNotOperation(){
        for(String s : illegalList){
            assertFalse(Validator.isOperation(s));
        }
    }

    @Test(expected = UnsupportedCommandException.class)
    public void illegalIsNotAcceptable(){
        for(String s : illegalList){
            Validator.acceptableValidate(s);
        }
    }

    @Test
    public void numberAndOperationIsAcceptable(){
        for (String s : numberList){
            Validator.acceptableValidate(s);
        }
        for (String s : operationList){
            Validator.acceptableValidate(s);
        }

    }
}
