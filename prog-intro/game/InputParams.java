package game;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class InputParams {
    private final List<InputParameterFormatHandler> parameters;
    private boolean paramsEntered;

    public InputParams(final int paramCount) {
        parameters = new ArrayList<>();
        for (int i = 0; i < paramCount; i++) {
            parameters.add(new InputParameterFormatHandler());
        }
        paramsEntered = false;
    }

    public int get(final int i) {
        if (!paramsEntered)
            throw new IllegalStateException("No data received");
        return parameters.get(i).getNumber();
    }

    public void getInputParamsFromUser(final Scanner sc) {
        paramsEntered = false;
        outermost:
        while (true) {
            for (InputParameterFormatHandler p : parameters) {
                p.getParameterFromUser(sc);
            }
            for (InputParameterFormatHandler p : parameters) {
                if (p.getInvalidInput() != null) {
                    System.out.print("Wrong input format. Expected " + parameters.size() + " numbers, got");
                    for (InputParameterFormatHandler p1 : parameters) {
                        System.out.print(" " + p1.getData());
                    }
                    System.out.println();
                    continue outermost;
                }
            }
            break;
        }
        paramsEntered = true;
    }

    public int getParamCount() {
        return parameters.size();
    }

    public boolean paramsEntered() {
        return paramsEntered;
    }

    private class InputParameterFormatHandler {
        private int number;
        private String invalidInput = null;

        private String getInvalidInput() {
            return invalidInput;
        }


        private int getNumber() {
            return number;
        }


        private String getData() {
            return invalidInput != null ? invalidInput : Integer.toString(number);
        }

        private void getParameterFromUser(final Scanner sc) {
            invalidInput = null;
            if (sc.hasNext()) {
                if (sc.hasNextInt()) {
                    number = sc.nextInt();
                } else {
                    invalidInput = sc.next();
                }
            }
        }
    }
}
