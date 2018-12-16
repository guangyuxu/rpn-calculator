package org.gavin.rpn.util;

import org.gavin.rpn.data.Element;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class LineParser {
    public static List<Element> parseLine(String line){
        List<Element> list = new ArrayList<>();
        Scanner elements = new Scanner(line);
        int position = 1;
        while(elements.hasNext()){
            String currentElement = elements.next();
            Element element = new Element(currentElement, position);
            list.add(element);
            position = position + currentElement.length() + 1;
        }
        return list;
    }
}
