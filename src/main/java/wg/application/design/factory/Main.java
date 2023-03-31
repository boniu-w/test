package wg.application.design.factory;

public class Main {
    public static void main(String[] args) {
        ShapeFactory shapeFactory = new ShapeFactory();

        Shape circle = shapeFactory.createShape("CIRCLE");
        circle.draw();

        Shape square = shapeFactory.createShape("SQUARE");
        square.draw();

        Shape rectangle = shapeFactory.createShape("RECTANGLE");
        rectangle.draw();
    }
}
