package org.gavin.rpn.persister;

import org.gavin.rpn.exception.InsucientParametersException;
import org.gavin.rpn.util.BigDecimalFormatter;
import org.gavin.rpn.util.Operation;

import java.math.BigDecimal;
import java.util.LinkedList;

public class CalculatorHistory {

    private final BigDecimalFormatter bigDecimalFormatter;
    private final LinkedList<CalculateItem<BigDecimal>> historyList = new LinkedList<>();

    public CalculatorHistory(BigDecimalFormatter bigDecimalFormatter) {
        this.bigDecimalFormatter = bigDecimalFormatter;
    }

    public void add(Operation operation, BigDecimal output){
        historyList.add(new CalculateItem<>(operation,
                bigDecimalFormatter.formatForStore(output)));
    }

    public void add(Operation operation, BigDecimal output, BigDecimal input){
        historyList.add(new CalculateItem<>(operation,
                bigDecimalFormatter.formatForStore(output),
                bigDecimalFormatter.formatForStore(input)));
    }
    public void add(Operation operation, BigDecimal output, BigDecimal input1, BigDecimal input2){
        historyList.add(new CalculateItem<>(operation,
                bigDecimalFormatter.formatForStore(output),
                bigDecimalFormatter.formatForStore(input1),
                bigDecimalFormatter.formatForStore(input2)));
    }

    public int getLastOutputSize(){
        return historyList.getLast().getOutputSize();
    }
    public CalculateItem<BigDecimal> removeLast(){
        if(historyList.isEmpty()) throw new InsucientParametersException();
        return historyList.removeLast();
    }



    public boolean isEmpty(){
        return this.historyList.isEmpty();
    }
    public void clear(){
        this.historyList.clear();
    }
    public int size(){ return this.historyList.size(); }
}
