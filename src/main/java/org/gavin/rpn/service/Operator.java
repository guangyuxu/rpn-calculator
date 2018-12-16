package org.gavin.rpn.service;

import org.gavin.rpn.util.Operation;

public interface Operator {
    void execute(Operation operator);
    void validate(Operation operation);
}
