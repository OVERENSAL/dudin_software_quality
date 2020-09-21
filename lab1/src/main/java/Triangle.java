import java.io.*;

public class Triangle {
    public final String INVALID_TRIANGLE_DATA = "Invalid data entered.";
    public final String INVALID_INPUT = "Invalid input.";
    public double a;
    public double b;
    public double c;

    public Triangle() { }

    public Triangle(double a, double b, double c) {
        this.a = a;
        this.b = b;
        this.c = c;
    }

    public static void main(String[] args) {
        Triangle triangle = new Triangle();
        triangle.readTriangle();
    }

    public void readTriangle() {
        try (BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(System.in))) {
            String line;
            while ((line = bufferedReader.readLine()) != null && !line.isEmpty()) {
                Triangle triangle = analysisTriangleData(line);
                if (triangle != null) {
                    System.out.println(analysisTriangleView(triangle));
                }
            }
        } catch (IOException e) {
            System.out.println(INVALID_INPUT);
        }
    }

    public Triangle analysisTriangleData(String line) throws IOException {
        try {
            String[] info = line.trim().split("\\s+");
            if (info.length != 3) {
                throw new IOException();
            }
            double a = Double.parseDouble(info[0]);
            double b = Double.parseDouble(info[1]);
            double c = Double.parseDouble(info[2]);
            if (a > 0 && b > 0 && c > 0) {
                return new Triangle(a, b, c);//triangle is valid
            }
            throw new IOException();
        } catch (IOException | NumberFormatException e) {
            System.out.println(INVALID_TRIANGLE_DATA);
            return null;//exit if triangle is not valid
        }
    }

    public String analysisTriangleView(Triangle triangle) {
        final String NOT_A_TRIANGLE = "It's not a triangle.";
        final String EQUILATERAL_TRIANGLE = "It's an equilateral triangle.";
        final String ISOSCELES_TRIANGLE = "It's an isosceles triangle.";
        final String ORDINARY_TRIANGLE = "It's an ordinary triangle.";
        if (isTriangle(triangle)) {
            if (isEquilateralTriangle(triangle)) {
                return EQUILATERAL_TRIANGLE;
            }
            if (isIsoscelesTriangle(triangle)) {
                return ISOSCELES_TRIANGLE;
            }
            return ORDINARY_TRIANGLE;
        }
        return NOT_A_TRIANGLE;
    }

    public boolean isTriangle(Triangle triangle) {
        return triangle.a < triangle.b + triangle.c &&
                triangle.b < triangle.a + triangle.c &&
                triangle.c < triangle.a + triangle.b;
    }

    public boolean isEquilateralTriangle(Triangle triangle) {
        return triangle.a == triangle.b && triangle.b == triangle.c;
    }

    public boolean isIsoscelesTriangle(Triangle triangle) {
        return triangle.a == triangle.b || triangle.b == triangle.c || triangle.a == triangle.c;
    }
}
