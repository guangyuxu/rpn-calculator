package org.gavin.rpn.exception;

public class UnsupportedCommandException extends InsucientParametersException {

    public UnsupportedCommandException(String operator) {
        super(operator);
    }

//    public UnsupportedCommandException(String operator, int position) { super(operator, position);}

    @Override
    public String getMessage() {
        return String.format("input %s (position: %d): is unsupported.", getOperator(), getPosition()) ;
    }
}
