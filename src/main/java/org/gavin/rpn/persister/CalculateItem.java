package org.gavin.rpn.persister;

import org.gavin.rpn.util.Operation;

import java.util.ArrayList;
import java.util.List;

public class CalculateItem<T> {
    private final List<T> inputParams = new ArrayList<>();
    private final List<T> outputResults = new ArrayList<>();
    private final Operation operation;

    public CalculateItem(Operation operation, T output) {
        this.operation = operation;
        this.outputResults.add(output);
    }

    public CalculateItem(Operation operation, T output, T input){
        this.operation = operation;
        this.outputResults.add(output);
        this.inputParams.add(input);
    }

    public CalculateItem(Operation operation, T output, T input1, T input2){
        this.operation = operation;
        this.outputResults.add(output);
        this.inputParams.add(input1);
        this.inputParams.add(input2);
    }


    public int getOutputSize() {
        return outputResults.size();
    }

    public List<T> getInputParams() {
        return inputParams;
    }
}
