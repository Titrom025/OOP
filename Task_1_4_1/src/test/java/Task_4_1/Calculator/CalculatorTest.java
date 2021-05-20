package Task_4_1.Calculator;

import junit.framework.TestCase;
import org.junit.Assert;
import org.junit.jupiter.api.Test;


public class CalculatorTest extends TestCase {

    @Test
    public void calculatorTestSimpleOperations() {
        Calculator calc = new Calculator();
        Assert.assertEquals(new Token(6, 0), calc.calculate("+ 2 4"));
        Assert.assertEquals(new Token(3, 0), calc.calculate("- 10 7"));
        Assert.assertEquals(new Token(8, 0), calc.calculate("* 2 4"));
        Assert.assertEquals(new Token(2, 0), calc.calculate("/ 10 5"));
    }

    @Test
    public void calculatorTestMoreComplexOperations() {
        Calculator calc = new Calculator();

        Assert.assertEquals(new Token(7, 0), calc.calculate("sqrt 49"));
        Assert.assertEquals(new Token(16, 0), calc.calculate("pow 2 4"));
        Assert.assertTrue(calc.calculate("log 10").getReal() > 2.302 && 2.303 > calc.calculate("log 10").getReal());
        Assert.assertTrue(calc.calculate("sin 30°").getReal() > 0.499 && 0.501 > calc.calculate("sin 30°").getReal());
        Assert.assertTrue(calc.calculate("cos 60°").getReal() > 0.499 && 0.501 > calc.calculate("cos 60°").getReal());
    }

    @Test
    public void calculatorTestSimpleOperationsWithComplexNumbers() {
        Calculator calc = new Calculator();

        Assert.assertEquals(new Token(4, 2), calc.calculate("+ (-5+4i) (9-2i)"));
        Assert.assertEquals(new Token(-14, 6), calc.calculate("- (-5+4i) (9-2i)"));
        Assert.assertEquals(new Token(-37, 46), calc.calculate("* (-5+4i) (9-2i)"));
        Assert.assertEquals(new Token(-0.6, 0.2), calc.calculate("/ (-5+3i) (9-2i)"));
    }

    @Test
    public void calculatorTestTriginimetry() {
        Calculator calc = new Calculator();

        Assert.assertTrue(calc.calculate("+ * sin 57° sin 57° * cos 57° cos 57°").getReal() > 0.999 && 1.001 > calc.calculate("+ * sin 57° sin 57° * cos 57° cos 57°").getReal());
        Assert.assertTrue(calc.calculate("+ sin 30° cos 60°").getReal() > 0.999 && 1.001 > calc.calculate("+ sin 30° cos 60°").getReal());
        Assert.assertTrue(calc.calculate("+ sin 390° cos 420°").getReal() > 0.999 && 1.001 > calc.calculate("+ sin 390° cos 4200°").getReal());
    }
}