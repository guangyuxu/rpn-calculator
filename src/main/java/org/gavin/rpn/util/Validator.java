package org.gavin.rpn.util;

import org.gavin.rpn.exception.UnsupportedCommandException;

import java.util.HashSet;
import java.util.Set;
import java.util.regex.Pattern;

public class Validator {
    private static final Set<String> operationSet = new HashSet<String>(){{
        for(Operation operatorEnum : Operation.values()){
            add(operatorEnum.getSymbol());
        }
    }};

    private static final Pattern numberPattern = Pattern.compile("-?[0-9]+(\\.[0-9]+)?");

    public static boolean isOperation(String element){
        return operationSet.contains(element);
    }

    public static boolean isNumber(String element){
        return numberPattern.matcher(element).matches();
    }

    public static void acceptableValidate(String element){
        if(!isOperation(element) && !isNumber(element))
            throw new UnsupportedCommandException(element);
    }
}
