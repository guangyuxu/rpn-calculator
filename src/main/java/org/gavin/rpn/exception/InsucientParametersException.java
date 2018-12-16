package org.gavin.rpn.exception;

public class InsucientParametersException extends RuntimeException{

    private String operator;
    private int position;

    public String getOperator() {
        return operator;
    }

    public int getPosition() {
        return position;
    }

    public InsucientParametersException() {}

    public InsucientParametersException(String operator) {
        this.operator = operator;
    }

    public InsucientParametersException(String operator, int position) {
        this.operator = operator;
        this.position = position;
    }

    public void setOperator(String operator) {
        this.operator = operator;
    }

    public void setPosition(int position) {
        this.position = position;
    }

    @Override
    public String getMessage() {
        return String.format("operator %s (position: %d): insucient parameters", operator, position) ;
    }

    @Override
    public String toString() {
        return getMessage();
    }
}
