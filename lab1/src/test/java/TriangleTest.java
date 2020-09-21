import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.io.*;

import static org.junit.Assert.*;

public class TriangleTest {
    final String NOT_A_TRIANGLE = "It's not a triangle.";
    final String EQUILATERAL_TRIANGLE = "It's an equilateral triangle.";
    final String ISOSCELES_TRIANGLE = "It's an isosceles triangle.";
    final String ORDINARY_TRIANGLE = "It's an ordinary triangle.";
    Triangle triangle = new Triangle();
    ByteArrayOutputStream stream = new ByteArrayOutputStream();
    PrintStream out = new PrintStream(stream);
    PrintStream originalOutputStream = System.out;

    @Before
    public void changeOutputStream() {
        System.setOut(out);
    }

    @After
    public void returnStandardOutputStream() {
        System.setOut(originalOutputStream);
    }

    @Test
    public void main_EnterEmptyLine_ShouldReturnExit(){
        ByteArrayInputStream in = new ByteArrayInputStream(("").getBytes());
        System.setIn(in);
        triangle.readTriangle();
        assertEquals("", stream.toString());
    }

    @Test
    public void main_EnterSpace_ShouldReturnInvalidDataEntered(){
        ByteArrayInputStream in = new ByteArrayInputStream((" ").getBytes());
        System.setIn(in);
        triangle.readTriangle();
        assertEquals(triangle.INVALID_TRIANGLE_DATA + "\r\n", stream.toString());
    }

    @Test
    public void main_EnterOneSide_ShouldReturnInvalidDataEntered(){
        ByteArrayInputStream in = new ByteArrayInputStream(("1").getBytes());
        System.setIn(in);
        triangle.readTriangle();
        assertEquals(triangle.INVALID_TRIANGLE_DATA + "\r\n", stream.toString());
    }

    @Test
    public void main_EnterTwoSides_ShouldReturnInvalidDataEntered(){
        ByteArrayInputStream in = new ByteArrayInputStream(("1 2").getBytes());
        System.setIn(in);
        triangle.readTriangle();
        assertEquals(triangle.INVALID_TRIANGLE_DATA + "\r\n", stream.toString());
    }

    @Test
    public void main_EnterThreeSides_ShouldReturnThatTriangleIsNotExists(){
        ByteArrayInputStream in = new ByteArrayInputStream(("1 2 3").getBytes());
        System.setIn(in);
        triangle.readTriangle();
        assertEquals(NOT_A_TRIANGLE + "\r\n", stream.toString());
    }

    @Test
    public void main_EnterMoreSides_ShouldReturnInvalidDataEntered(){
        ByteArrayInputStream in = new ByteArrayInputStream(("1 2 3 4").getBytes());
        System.setIn(in);
        triangle.readTriangle();
        assertEquals(triangle.INVALID_TRIANGLE_DATA + "\r\n", stream.toString());
    }

    @Test
    public void main_EnterDataWithSpaces_ShouldReturnOrdinaryTriangle(){
        ByteArrayInputStream in = new ByteArrayInputStream(("3      4         5").getBytes());
        System.setIn(in);
        triangle.readTriangle();
        assertEquals(ORDINARY_TRIANGLE + "\r\n", stream.toString());
    }

    @Test
    public void main_EnterDataWithSpacesInStart_ShouldReturnOrdinaryTriangle(){
        ByteArrayInputStream in = new ByteArrayInputStream(("           3      4         5").getBytes());
        System.setIn(in);
        triangle.readTriangle();
        assertEquals(ORDINARY_TRIANGLE + "\r\n", stream.toString());
    }

    @Test
    public void main_EnterNonValidData_ShouldReturnInvalidData(){
        ByteArrayInputStream in = new ByteArrayInputStream(("   A  1  2").getBytes());
        System.setIn(in);
        triangle.readTriangle();
        assertEquals(triangle.INVALID_TRIANGLE_DATA + "\r\n", stream.toString());
    }

    @Test
    public void main_EnterNegativeData_ShouldReturnInvalidData(){
        ByteArrayInputStream in = new ByteArrayInputStream(("-1  1  2").getBytes());
        System.setIn(in);
        triangle.readTriangle();
        assertEquals(triangle.INVALID_TRIANGLE_DATA + "\r\n", stream.toString());
    }

    @Test
    public void main_EnterNegativeDataWithSpaces_ShouldReturnInvalidData(){
        ByteArrayInputStream in = new ByteArrayInputStream(("      -12     -12      -12    ").getBytes());
        System.setIn(in);
        triangle.readTriangle();
        assertEquals(triangle.INVALID_TRIANGLE_DATA + "\r\n", stream.toString());
    }

    @Test
    public void main_EnterValidDataWithSpaces_ShouldReturnEquilateralTriangle(){
        ByteArrayInputStream in = new ByteArrayInputStream(("      12     12      12    ").getBytes());
        System.setIn(in);
        triangle.readTriangle();
        assertEquals(EQUILATERAL_TRIANGLE + "\r\n", stream.toString());
    }

    @Test
    public void main_EnterValidData_ShouldReturnEquilateralTriangle(){
        ByteArrayInputStream in = new ByteArrayInputStream(("12 12 12").getBytes());
        System.setIn(in);
        triangle.readTriangle();
        assertEquals(EQUILATERAL_TRIANGLE + "\r\n", stream.toString());
    }

    @Test
    public void main_EnterZeroSide_ShouldReturnInvalidData(){
        ByteArrayInputStream in = new ByteArrayInputStream(("0 0 3").getBytes());
        System.setIn(in);
        triangle.readTriangle();
        assertEquals(triangle.INVALID_TRIANGLE_DATA + "\r\n", stream.toString());
    }

    @Test
    public void main_EnterNonDigitData_ShouldReturnInvalidData(){
        ByteArrayInputStream in = new ByteArrayInputStream(("a b c").getBytes());
        System.setIn(in);
        triangle.readTriangle();
        assertEquals(triangle.INVALID_TRIANGLE_DATA + "\r\n", stream.toString());
    }

    @Test
    public void main_EnterIsoscelesData_ShouldReturnIsoscelesTriangle(){
        ByteArrayInputStream in = new ByteArrayInputStream(("4 4 6").getBytes());
        System.setIn(in);
        triangle.readTriangle();
        assertEquals(ISOSCELES_TRIANGLE + "\r\n", stream.toString());
    }

    @Test
    public void main_EnterDoubleIsoscelesData_ShouldReturnIsoscelesTriangle(){
        ByteArrayInputStream in = new ByteArrayInputStream(("3.5 3.5 3").getBytes());
        System.setIn(in);
        triangle.readTriangle();
        assertEquals(ISOSCELES_TRIANGLE + "\r\n", stream.toString());
    }

    @Test
    public void main_EnterMaxInt_ShouldReturnEquilateralTriangle(){
        ByteArrayInputStream in = new ByteArrayInputStream(("2147483647 2147483647 2147483647").getBytes());
        System.setIn(in);
        triangle.readTriangle();
        assertEquals(EQUILATERAL_TRIANGLE + "\r\n", stream.toString());
    }

}