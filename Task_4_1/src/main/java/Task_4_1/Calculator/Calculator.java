package Task_4_1.Calculator;

import java.util.ArrayList;
import java.util.Arrays;

/**
 * Calculator class supports operations {"+", "-", "*", "/", "pow", "sqrt", "log", "sin", "cos"} for real numbers,
 * working with degrees, but complex numbers supports only simple operations. Moreover complex numbers must be written in
 * strictly defined form "(a+ib)" or "(c-id)"
 */
public class Calculator {
    private static final String[] BINARY_OPS = new String[] {"+", "-", "*", "/", "pow"};
    private static final String[] UNARY_OPS = new String[] {"sqrt", "log", "sin", "cos"};

    private void makeBinaryOperation(String op, ArrayList<Token> tokens, int element) {
        tokens.remove(element);

        Token firstAtom = tokens.remove(element);
        Token secondAtom = tokens.remove(element);

        Token newAtom;

        switch (op) {
            case "+" -> newAtom = Token.add(firstAtom, secondAtom);
            case "-" -> newAtom = Token.substract(firstAtom, secondAtom);
            case "*" -> newAtom = Token.multiply(firstAtom, secondAtom);
            case "/" -> newAtom = Token.divide(firstAtom, secondAtom);
            case "pow" -> newAtom = Token.pow(firstAtom, secondAtom);
            default -> throw new IllegalArgumentException("Illegal operation");
        }

        tokens.add(element, newAtom);
    }

    private void makeUnaryOperation(String op, ArrayList<Token> tokens, int element) {
        tokens.remove(element);

        Token atom = tokens.remove(element);

        Token newAtom;

        switch (op) {
            case "sqrt" -> newAtom = Token.sqrt(atom);
            case "log" -> newAtom = Token.log(atom);
            case "sin" -> newAtom = Token.sin(atom);
            case "cos" -> newAtom = Token.cos(atom);
            default ->  throw new IllegalArgumentException("Illegal operation");
        }

        tokens.add(element, newAtom);
    }

    /**
     * The function calculates the value of the expression in prefix form
     * @param str - expression in prefix form
     * @return - Token - result of calculation
     */
    public Token calculate(String str) {
        String[] atoms = str.split("[ ]+");
        ArrayList<Token> tokens = new ArrayList<>();

        try {
            for (String atom : atoms) {
                if (atom.charAt(0) == '(' && atom.charAt(atom.length() - 1) == ')') {
                    atom = atom.replace("(", "");
                    atom = atom.replace(")", "");

                    String[] complexNum = atom.split("\\b[+]");

                    double imaginary = 1;
                    if (complexNum.length == 1) {
                        complexNum = atom.split("\\b[-]");
                        imaginary = -1;
                    }

                    complexNum[1] = complexNum[1].replace('i', ' ');
                    double real = Double.parseDouble(complexNum[0]);
                    imaginary *= Double.parseDouble(complexNum[1]);
                    tokens.add(new Token(real, imaginary));
                }
                else if (isNumeric(atom)) {
                    double real = Double.parseDouble(atom);
                    tokens.add(new Token(real, 0));
                }
                else if (Arrays.asList(UNARY_OPS).contains(atom) || Arrays.asList(BINARY_OPS).contains(atom)) {
                    tokens.add(new Token(atom));
                }
                else if (atom.contains("°")) {
                    atom = atom.replace("°", "");
                    double degree = Double.parseDouble(atom);
                    tokens.add(new Token(degree));
                }
                else {
                    throw new IllegalArgumentException("Unexpected atom");
                }
            }
        }
        catch (NumberFormatException error) {
            throw new IllegalStateException("Unexpected atom");
        }

        int elementsCount = tokens.size();

        int currentElement = elementsCount - 1;
        while (elementsCount > 1) {
            if (currentElement < 0) {
                throw new IllegalStateException("Unexpected argument count");
            }
            Token token = tokens.get(currentElement);
            switch (token.operation) {
                case "+", "-", "*", "/", "pow" -> {
                    if (elementsCount - currentElement > 2) {
                        makeBinaryOperation(token.operation, tokens, currentElement);
                        elementsCount -= 2;
                    } else {
                        throw new IllegalStateException("Not enough arguments");
                    }
                }
                case "sqrt", "log", "sin", "cos" -> {
                    if (elementsCount - currentElement > 1) {
                        makeUnaryOperation(token.operation, tokens, currentElement);
                        elementsCount -= 1;
                    } else {
                        throw new IllegalStateException("Not enough arguments");
                    }
                }
                default -> {
                    if (!token.operation.equals("")) {
                        throw new IllegalStateException("Unexpected argument");
                    }
                }
            }
            currentElement--;
        }
        return tokens.get(0);
    }

    private static boolean isNumeric(String strNum) {
        if (strNum == null) {
            return false;
        }
        try {
            double d = Double.parseDouble(strNum);
        } catch (NumberFormatException err) {
            return false;
        }
        return true;
    }
}
