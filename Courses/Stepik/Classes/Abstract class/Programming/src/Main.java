abstract class Shape {

  abstract double getPerimeter();

  abstract double getArea();
}

class Triangle extends Shape{

  private double a;
  private double b;
  private double c;

  Triangle(double a, double b, double c){
    this.a = a;
    this.b = b;
    this.c = c;
  }

  private double getSemiPerimeter(){
    return (a+b+c) / 2;
  }

  @Override
  double getPerimeter() {
    return a+b+c;
  }

  @Override
  double getArea() {
    double p = getSemiPerimeter();
    return Math.sqrt(p*(p-a)*(p-b)*(p-c));
  }
}

class Rectangle extends Shape{

  private double a;
  private double b;

  Rectangle(double a, double b){
    this.a = a;
    this.b = b;
  }

  @Override
  double getPerimeter() {
    return (2*a) + (2*b);
  }

  @Override
  double getArea() {
    return a*b;
  }
}

class Circle extends Shape{

  private double radius;

  Circle(double radius){
    this.radius = radius;
  }

  @Override
  double getPerimeter() {
    return 2 * Math.PI * radius;
  }

  @Override
  double getArea() {
    return Math.PI * (radius * radius);
  }
}
