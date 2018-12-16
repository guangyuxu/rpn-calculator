package org.gavin.rpn.persister;

import org.gavin.rpn.util.BigDecimalFormatter;

import java.math.BigDecimal;
import java.util.Iterator;
import java.util.Stack;

public class CalculatorStack {

    private final BigDecimalFormatter bigDecimalFormatter;

    public CalculatorStack(BigDecimalFormatter bigDecimalFormatter) {
        this.bigDecimalFormatter = bigDecimalFormatter;
    }

    private final Stack<BigDecimal> stack = new Stack<BigDecimal>() {
        @Override
        public String toString() {
            StringBuilder sb = new StringBuilder("stack:");
            Iterator<BigDecimal> it = iterator();
            //noinspection WhileLoopReplaceableByForEach
            while (it.hasNext()) {
                sb.append(" ").append(bigDecimalFormatter.formatForPresent(it.next()).toPlainString());
            }
            return sb.toString();
        }
    };

    public void push(BigDecimal b){
        stack.push(bigDecimalFormatter.formatForStore(b));
    }

    public BigDecimal pop(){
        return stack.pop();
    }

    public boolean lessThan(int i){
        return stack.size() < i;
    }

    public void clear(){
        stack.clear();
    }

    public int size(){ return stack.size(); }

    @Override
    public String toString(){
        return stack.toString();
    }
}
