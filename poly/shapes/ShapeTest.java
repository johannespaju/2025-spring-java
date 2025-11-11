package poly.shapes;

import org.junit.jupiter.api.Test;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.within;

public class ShapeTest {

    @Test
    public void computesTotalArea() {

        List<Shape> objects = List.of(
                new Circle(5), new Rectangle(2, 4), new Square(3));

        double totalArea = 0.0;
        for (Shape shape : objects) {
            totalArea += shape.getArea();
        }

        assertThat(totalArea).isCloseTo(95.5, within(0.1));
    }

    private double getArea(Circle circle) {
        return Math.PI * Math.pow(circle.getRadius(), 2);
    }

    private double getArea(Rectangle rectangle) {
        return rectangle.getHeight() * rectangle.getWidth();
    }

    private double getArea(Square square) {
        return Math.pow(square.getSide(), 2);
    }
}
