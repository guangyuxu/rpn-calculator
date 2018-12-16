package org.gavin.rpn.data;

public class Element {
    private final String currentElement;
    private final int position;

    public Element(String currentElement, int position) {
        this.currentElement = currentElement;
        this.position = position;
    }

    public String getCurrentElement() {
        return currentElement;
    }

    public int getPosition() {
        return position;
    }
}