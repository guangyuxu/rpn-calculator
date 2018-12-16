package org.gavin.rpn.controller;

import org.gavin.rpn.data.Element;
import org.gavin.rpn.exception.InsucientParametersException;
import org.gavin.rpn.exception.UnsupportedCommandException;
import org.gavin.rpn.persister.CalculatorStack;
import org.gavin.rpn.service.NumberOperator;
import org.gavin.rpn.service.Operator;
import org.gavin.rpn.service.OperatorFactory;
import org.gavin.rpn.util.LineParser;
import org.gavin.rpn.util.Operation;

import java.math.BigDecimal;
import java.util.List;

import static org.gavin.rpn.util.Validator.*;

public class CalculateController {

    private final OperatorFactory operatorFactory;
    private final CalculatorStack calculatorStack;

    public CalculateController(OperatorFactory operatorFactory, CalculatorStack calculatorStack) {
        this.operatorFactory = operatorFactory;
        this.calculatorStack = calculatorStack;
    }

    public void execute(String line){
        List<Element> list = LineParser.parseLine(line);
        for(Element element : list){
            try {
                acceptableValidate(element.getCurrentElement());
                executeOneElement(element.getCurrentElement());
            } catch (InsucientParametersException ex) {
                ex.setPosition(element.getPosition());
                throw ex;
            }
        }
    }

    private void executeOneElement(String element){
        if(isOperation(element)){
            Operation operation = Operation.apply(element);
            Operator operator = operatorFactory.getCalOperator(operation);
            operator.execute(operation);
        }else if(isNumber(element)){
            NumberOperator numberOperator = operatorFactory.getNumberOperator();
            numberOperator.save(new BigDecimal(element));
        }else{
            throw new UnsupportedCommandException(element);
        }
    }

    public String stackToString(){
        return calculatorStack.toString();
    }
}
