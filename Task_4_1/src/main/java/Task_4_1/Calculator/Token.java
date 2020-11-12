package Task_4_1.Calculator;

import java.util.Objects;

public class Token {
    private static final double PI = 3.14159265359;

    double real;
    double imaginary;
    String operation;

    /**
     * Initializer for complex number (or real, if "imaginary" = 0)
     * @param real - real part
     * @param imaginary - imaginary part
     */
    Token(double real, double imaginary) {
        this.real = real;
        this.imaginary = imaginary;
        this.operation = "";
    }

    /**
     * initializer for radians from degrees
     * @param degrees - degrees
     */
    Token(double degrees) {
        this.real = degrees * PI / 180;
        this.imaginary = 0;
        this.operation = "";
    }

    /**
     * Initializer for operation token
     * @param operation - type of operation
     */
    Token(String operation) {
        this.real = 0;
        this.imaginary = 0;
        this.operation = operation;
    }

    public static Token add(Token token1, Token token2) {
        return new Token(token1.real + token2.real, token1.imaginary + token2.imaginary);
    }

    public static Token substract(Token token1, Token token2) {
        return new Token(token1.real - token2.real, token1.imaginary - token2.imaginary);
    }

    public static Token multiply(Token token1, Token token2) {
        return new Token(
                token1.real * token2.real - token1.imaginary * token2.imaginary,
                token1.real * token2.imaginary + token2.real * token1.imaginary);
    }

    public static Token divide(Token token1, Token token2) {
        double denominator = token2.real * token2.real + token2.imaginary * token2.imaginary;
        return new Token(
                (token1.real * token2.real + token1.imaginary * token2.imaginary) / denominator,
                (token1.imaginary * token2.real - token1.real * token2.imaginary) / denominator);
    }

    public static Token pow(Token token1, Token token2) {
        if (token1.imaginary != 0 || token2.imaginary != 0) {
            throw new IllegalArgumentException("This operation does not support by complex number");
        }
        return new Token(Math.pow(token1.real, token2.real), 0);
    }

    public static Token sqrt(Token token) {
        if (token.imaginary != 0) {
            throw new IllegalArgumentException("This operation does not support by complex number");
        }
        return new Token(Math.sqrt(token.real), 0);
    }

    public static Token log(Token token) {
        if (token.imaginary != 0) {
            throw new IllegalArgumentException("This operation does not support by complex number");
        }
        return new Token(Math.log(token.real), 0);
    }

    public static Token cos(Token token) {
        if (token.imaginary != 0) {
            throw new IllegalArgumentException("This operation does not support by complex number");
        }
        return new Token(Math.cos(token.real), 0);
    }

    public static Token sin(Token token) {
        if (token.imaginary != 0) {
            throw new IllegalArgumentException("This operation does not support by complex number");
        }
        return new Token(Math.sin(token.real), 0);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Token token = (Token) o;
        return Double.compare(token.real, real) == 0 &&
                Double.compare(token.imaginary, imaginary) == 0 &&
                Objects.equals(operation, token.operation);
    }

    @Override
    public int hashCode() {
        return Objects.hash(real, imaginary, operation);
    }
}
