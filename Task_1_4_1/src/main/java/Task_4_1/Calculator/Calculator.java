package Task_4_1.Calculator;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

/**
 * Calculator class supports operations {"+", "-", "*", "/", "pow", "sqrt", "log", "sin", "cos"} for real numbers,
 * working with degrees, but complex numbers supports only simple operations. Moreover complex numbers must be written in
 * strictly defined form "(a+ib)" or "(c-id)"
 * To stop programm enter "stop"
 */
public class Calculator {
    private static final String[] BINARY_OPS = new String[] {"+", "-", "*", "/", "pow"};
    private static final String[] UNARY_OPS = new String[] {"sqrt", "log", "sin", "cos"};

    public static void main(String[] args) {
        Calculator calc = new Calculator();
        calc.startCalculation();
    }
    
    /**
     * Main cycle of console program
     */
    public void startCalculation() {
        Scanner scan= new Scanner(System.in);
        String input = "";
        System.out.print("Enter expression in prefix form: ");
        while (!(input = scan.nextLine()).equals("stop")) {
            Token answer = calculate(input);
            System.out.printf("%f ", answer.getReal());
            if (answer.getImaginary() != 0) {
                if (answer.getImaginary() > 0) {
                    System.out.printf("+ %fi", answer.getImaginary());
                } else {
                    System.out.printf("%fi", answer.getImaginary());
                }
            }
            System.out.print("\nEnter expression in prefix form: ");
        }
    }

    /**
     * The function calculates the value of the expression in prefix form
     * @param str - expression in prefix form
     * @return - Token - result of calculation
     */
    public Token calculate(String str) {
        String[] atoms = str.split("[ ]+");
        List<Token> tokens = new ArrayList<>();

        convertAtomsToToken(atoms, tokens);

        int elementsCount = tokens.size();

        int currentElement = elementsCount - 1;
        while (elementsCount > 1) {
            if (currentElement < 0) {
                throw new IllegalStateException("Unexpected argument count");
            }
            Token token = tokens.get(currentElement);
            switch (token.getOperation()) {
                case "+", "-", "*", "/", "pow" -> {
                    makeBinaryOperation(token.getOperation(), tokens, currentElement);
                    elementsCount -= 2;
                }
                case "sqrt", "log", "sin", "cos" -> {
                    makeUnaryOperation(token.getOperation(), tokens, currentElement);
                    elementsCount -= 1;
                }
                default -> {
                    if (!token.getOperation().equals("")) {
                        throw new IllegalStateException("Unexpected argument");
                    }
                }
            }
            currentElement--;
        }
        return tokens.get(0);
    }

    private void convertAtomsToToken(String[] atoms, List<Token> tokens) {
        try {
            for (String atom : atoms) {
                if (atom.charAt(0) == '(' && atom.charAt(atom.length() - 1) == ')') {
                    convertAtomToComplex(atom, tokens);
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
    }

    private void makeUnaryOperation(String op, List<Token> tokens, int currentElement) {
        if (tokens.size() - currentElement <= 1) {
            throw new IllegalStateException("Not enough arguments");
        }

        tokens.remove(currentElement);
        Token token = tokens.remove(currentElement);
        Token newToken;

        switch (op) {
            case "sqrt" -> newToken = Token.sqrt(token);
            case "log" -> newToken = Token.log(token);
            case "sin" -> newToken = Token.sin(token);
            case "cos" -> newToken = Token.cos(token);
            default ->  throw new IllegalArgumentException("Illegal operation");
        }

        tokens.add(currentElement, newToken);
    }

    private void makeBinaryOperation(String op, List<Token> tokens, int currentElement) {
        if (tokens.size() - currentElement <= 2) {
            throw new IllegalStateException("Not enough arguments");
        }

        tokens.remove(currentElement);

        Token firstToken = tokens.remove(currentElement);
        Token secondToken = tokens.remove(currentElement);

        Token newAtom;

        switch (op) {
            case "+" -> newAtom = Token.add(firstToken, secondToken);
            case "-" -> newAtom = Token.substract(firstToken, secondToken);
            case "*" -> newAtom = Token.multiply(firstToken, secondToken);
            case "/" -> newAtom = Token.divide(firstToken, secondToken);
            case "pow" -> newAtom = Token.pow(firstToken, secondToken);
            default -> throw new IllegalArgumentException("Illegal operation");
        }

        tokens.add(currentElement, newAtom);
    }

    private void convertAtomToComplex(String atom, List<Token> tokens) {
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

