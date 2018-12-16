package org.gavin.rpn;


import org.gavin.rpn.controller.CalculateController;
import org.gavin.rpn.exception.InsucientParametersException;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import java.util.Scanner;

public class CalculatorMain {
    private static final String applicationContextFile = "spring/applicationContext.xml";
    private static final String QUIT="q";
    private final CalculateController calculateController;

    private CalculatorMain(CalculateController calculateController) {
        this.calculateController = calculateController;
    }

    public static void main(String[] args) {
        BeanFactory factory = new ClassPathXmlApplicationContext(applicationContextFile);
        CalculatorMain calculatorMain = factory.getBean(CalculatorMain.class);
        calculatorMain.start();
    }

    private void start() {

        Scanner lineScanner = new Scanner(System.in);
        System.out.println("Welcome to reverse polish notation mode calculator, developed by Gavin Xu");
        System.out.println("Number, +, -, *, /, undo or clear are acceptable");
        System.out.println("Input q + enter to quit.");
        while (true) {
            String line = lineScanner.nextLine();
            if(QUIT.equals(line.trim())) break;
            try {
                calculateController.execute(line);
            } catch (InsucientParametersException ex) {
                System.out.println(ex.getMessage());
            }
            System.out.println(calculateController.stackToString());
        }
    }
}
