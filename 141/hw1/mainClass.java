//Name: VU Q TRAN
//ID:   48894667
//CS 141, HW 01

import java.util.* ;
import java.lang.Math.* ;

class Shape {
   String name;
   Shape (String newName) {
      name = newName;
   }
   double area() {
     return 0.0;
   }
   void draw() {
      System.out.println("Shape.draw() You should never see this.");
   }
   void printShape() {
      System.out.println("Shape name: " + this.name); 
   }
}

class Triangle extends Shape {
   protected int myHeight = 0, myBase = 0;
   Triangle (String name, int height, int base) {
      super(name);
      this.myHeight = height;
      this.myBase   = base;

   } 
   void printShape() {
      System.out.println(this.name + ": " + "height=" + myHeight + ", base=" + myBase);
   }
   void draw() {
      int mid = (int) (myBase/2);   
      for (int i=0; i < myHeight; i++) {
         for (int j=0; j < myBase; j++) {
           if (j == 0) 
             System.out.print("*");
           else if (i == myHeight -1)
             System.out.print("*");
           else if (i == j)
             System.out.print("*");
           else 
             System.out.print(" ");
         }
         System.out.println();
      }
   }
   double area() {
      return  ( (((double) myHeight) * ((double) myBase))/2);
   }

}

class Circle extends Shape {
   int myRadius = 0;
   Circle (String name, int radius) {
      super(name);
      this.myRadius = radius;

   } 
   void printShape() {
      System.out.println(this.name + ": " + "radius=" + myRadius);
   }
   void draw() {
     int r = myRadius;
     int d = 2*r;
     double dis = 0;
     for (int i=0; i <= d; i++) {
        for (int j=0; j <= d; j++) {
           if ( (i==0 && j==r) || (i==r && j==0) || (i==r && j==2*r) || (i==2*r && j==r ) ) //(0,r), (r,0), (r,2r), (2r,r)
                 System.out.print("*");
           else {
              dis = Math.sqrt( (i-r)* (i - r) + (j-r)*(j-r) );
              if ( Math.abs(dis - r) < 0.5)
                System.out.print("*");
              else
                System.out.print(" ");
           }
        }
        System.out.println();
     }
      
   }
   double area() {
      return  ( (((double) myRadius) * ((double) myRadius)) * Math.PI);

   }

}

class Square extends Shape {
   protected int myLength = 0;
   Square (String name, int length) {
      super(name);
      this.myLength = length;
   }
   void printShape() {
      System.out.println(this.name + ": " + "side=" + myLength);
   }
   void draw() {
     if (myLength <= 0)
       return ;
     else if (myLength == 1) 
       System.out.println ("*");
     else 
     {
       for (int i=0; i< myLength; i++) { //imaging has a matrix of [X,Y] positions
          for (int j=0; j < myLength; j++) {
             if (j==0 || (j== (myLength -1)) ) //left & right 
                System.out.print("*");
             else if ( (i==0 || (i== (myLength -1))) && (j>0) && (j < (myLength -1)) ) //top & bottom 
                System.out.print("*"); 
             else
                System.out.print(" ");
          }
          System.out.println();
       }
     }
   }
   double area() {
      return  ( ((double) myLength) * ((double) myLength));
   }

}

class Rectangle extends Square {
   int myWidth = 0;
   Rectangle (String name, int length, int width) {
      super(name, length);
      this.myWidth = width;
   }
   void printShape() {
      System.out.println(this.name + ": " + "height=" + myLength + ", width=" + myWidth);
   }
   void draw() {
      if (myLength <= 0 || myWidth <= 0)
       return ;
     else if (myLength == 1 || myWidth == 1) 
       System.out.println ("*");
     else 
     {
       for (int i=0; i< myWidth; i++) {
          for (int j=0; j < myLength; j++) {
             if (j==0 || (j== (myLength -1)) ) //left & right 
                System.out.print("*");
             else if ( (i==0 || (i== (myWidth -1))) && (j>0) && (j < (myLength -1)) ) //top & bottom 
                System.out.print("*"); 
             else
                System.out.print(" ");
          }
          System.out.println();
       }
     }
     
   }
   double area() {
      return  ( (((double) myLength) * ((double) myWidth)));
   }

}

class Picture {
  LinkedList<Shape> list ;
  String name;
  Picture () {
     this.name = "No name";
     this.list = new LinkedList<Shape>();
  }
  Picture (String name) {
    this.name = name;
    this.list = new LinkedList<Shape>(); 
  }
  void add(Shape sh) {
    list.add(sh); //https://docs.oracle.com/javase/6/docs/api/java/util/LinkedList.html
  }
  void drawAll() {
    int size = list.size();
    Shape temp;
    for (int i=0; i < size; i++ ) {
       temp = list.get(i);
       temp.draw();
    }
     
  }
  void printShapes() {
     int size = list.size();
     Shape temp;
     for (int i=0; i < size; i++) {
        temp = list.get(i);
        temp.printShape();
        System.out.println();
     }
  }
  double totalArea() {
    int size = list.size();
    double total = 0.0;
    Shape temp;
    for (int i=0; i < size; i++) {
       temp   = list.get(i);
       total += temp.area(); 
    }
     return total;
  }
}

public class mainClass {
   static void println(double d) {
      System.out.printf("The total area of the shapes is %.3f square units\n", d);
   }
   public static void main (String[] args) {
      Picture p = new Picture();
      p.add (new Triangle("FirstTriangle", 5, 5 ));
      p.add (new Triangle("SecondTriangle", 4, 3));
      p.add (new Circle("FirstCircle",5));
      p.add (new Circle("SecondCircle", 10));
      p.add (new Square("FirstSquare", 5));
      p.add (new Square("SecondSquare", 10));
      p.add (new Rectangle("FirstRectangle", 4,8));
      p.add (new Rectangle("SecondRectangle",8,4));
      p.printShapes();
      p.drawAll();
      println(p.totalArea()); //not sure why the question declare this function

   }
}



