#include <stdio.h>
#include <math.h>
#include <string.h>
#include <malloc.h>

#define MAXCHARPERNAME 255
//################################################ Vtable = list of function pointers = is pointed by vPointer
typedef double (*MethodPointerType) (void *); //VTableType simply is type of the pointer to the method

//################################################ Class Shape 

struct Shape {
  MethodPointerType *vPointer;
  char name[MAXCHARPERNAME];
};

double Shape_area(struct Shape *this) {
  return (double) 0.0;
}

void Shape_draw(struct Shape *this) {
   printf("Shape draw \n");
}

void Shape_printShape(struct Shape *this) {
   printf("Shape name %s\n", this->name);
}

//Implement Vtable as global (instead of inside struct) because instances share/use the same functions list
//does not need to generate one for each
MethodPointerType Shape_Vtable[] = { \
  (MethodPointerType) &Shape_area ,  
  (MethodPointerType) &Shape_draw ,  \
  (MethodPointerType) &Shape_printShape };

struct Shape * Shape_Shape(struct Shape *this, const char* name) {
   strcpy(this->name, name);
   this->vPointer = Shape_Vtable;
   return this;
}

//################################################ Class Triangle extends Shape
struct Triangle {
   MethodPointerType *vPointer;
   char name[MAXCHARPERNAME];
   double myHeight;
   double myBase;
};

double Triangle_area(struct Triangle *this) {
   return (this->myHeight * this->myBase)/2;
}
void Triangle_draw(struct Triangle *this) {
  int mid = (int) (this->myBase/2);
  int i = 0, j=0;
  for (i=0; i < this->myHeight; i++) {
     for (j=0; j < this->myBase; j++) {
       if (j == 0)
         printf("*");
       else if (i == this->myHeight -1)
         printf("*");
       else if (i == j)
         printf("*");
       else
         printf(" ");
     }
     printf("\n");
  }
 
}
void Triangle_printShape(struct Triangle *this) {
   printf("%s: height=%.f, base=%.f \n", this->name, this->myHeight, this->myBase);
}

MethodPointerType Triangle_Vtable[3] = {\
  (MethodPointerType) &Triangle_area,   \
  (MethodPointerType) &Triangle_draw,   \
  (MethodPointerType) &Triangle_printShape } ;

struct Triangle *Triangle_Triangle(struct Triangle *this, const char *name, double height, double base) {
   Shape_Shape((struct Shape*) this, name);
   this->vPointer = Triangle_Vtable;
   this->myHeight = height;
   this->myBase   = base;
   return this;
}

//################################################ Class Circle extends Shape
struct Circle {
  MethodPointerType *vPointer;
  char name[MAXCHARPERNAME];
  double myRadius;

};

double Circle_area(struct Circle *this) {
  return this->myRadius * this->myRadius * 3.14159;
}
void Circle_draw(struct Circle *this) {
 double r = this->myRadius;
 double d = 2*r;
 double dis = 0;
 double i =  0, j = 0;
 for (i=0; i <= d; i++) {
    for (j=0; j <= d; j++) {
       if ( (i==0 && j==r) || (i==r && j==0) || (i==r && j==2*r) || (i==2*r && j==r ) ) //(0,r), (r,0), (r,2r), (2r,r)
             printf("*");
       else {
          dis =  (i-r)*(i - r) + (j-r)*(j-r);
          dis = sqrt(dis);
          if ( fabs(dis - r) < 0.5)
            printf("*");
          else
            printf(" ");
       }
    }
    printf("\n");
 }

}
void Circle_printShape(struct Circle *this) {
   printf("%s: radius=%.f\n",this->name, this->myRadius);
}

MethodPointerType Circle_Vtable[3] = { \
  (MethodPointerType) &Circle_area,    \
  (MethodPointerType) &Circle_draw,    \
  (MethodPointerType) &Circle_printShape};

struct Circle * Circle_Circle (struct Circle *this, const char *name, double radius) {
   Shape_Shape((struct Shape *) this, name);
   this->vPointer = Circle_Vtable;
   this->myRadius = radius;
}

//################################################ Class Square extends Shape
struct Square {
   MethodPointerType *vPointer;
   char name[MAXCHARPERNAME];
   double myLength;
};

double Square_area(struct Square *this) {
  return this->myLength * this->myLength;   
}

void Square_draw(struct Square *this) {
  if (this->myLength <= 0)
    return ;
  else if (this->myLength == 1)
    printf ("*\n");
  else { 
    double i=0, j=0;
    for (i=0; i< this->myLength; i++) { //imaging has a matrix of [X,Y] positions
       for (j=0; j < this->myLength; j++) {
          if (j==0 || (j== (this->myLength -1)) ) //left & right 
             printf("*");
          else if ( (i==0 || (i== (this->myLength -1))) && (j>0) && (j < (this->myLength -1)) ) //top & bottom 
             printf("*");
          else
             printf(" ");
       }
       printf("\n");
    }
  }

}
void Square_printShape(struct Square *this){
   printf("%s: side=%.f\n", this->name, this->myLength);
}

MethodPointerType Square_Vtable[3] = { \
  (MethodPointerType) &Square_area, \
  (MethodPointerType) &Square_draw, \
  (MethodPointerType) &Square_printShape};

struct Square *Square_Square(struct Square *this, const char *name, double length) {
   Shape_Shape((struct Shape *) this, name);
   this->vPointer = Square_Vtable;
   this->myLength = length;
}


//################################################ Class Rectangle extends Square
struct Rectangle {
   MethodPointerType *vPointer; 
   char name[MAXCHARPERNAME];
   double myLength;
   double myWidth;
};

double Rectangle_area(struct Rectangle *this) {
  return this->myLength * this->myWidth;
}
void Rectangle_draw(struct Rectangle *this) {
  if (this->myLength <= 0 || this->myWidth <= 0)
    return ;
  else if (this->myLength == 1 || this->myWidth == 1)
    printf("*\n");
  else
  { int i=0, j=0;
    for (i=0; i< this->myWidth; i++) {
       for (j=0; j < this->myLength; j++) {
          if (j==0 || (j== (this->myLength -1)) ) //left & right 
             printf("*");
          else if ( (i==0 || (i== (this->myWidth -1))) && (j>0) && (j < (this->myLength -1)) ) //top & bottom 
             printf("*");
          else
             printf(" ");
       }
       printf("\n");
    }
  }

}
void Rectangle_printShape(struct Rectangle *this) {
   printf("%s: height=%.f, width=%.f\n", this->name, this->myLength, this->myWidth);
}

MethodPointerType Rectangle_Vtable[3] = { \
  (MethodPointerType) &Rectangle_area, \
  (MethodPointerType) &Rectangle_draw, \
  (MethodPointerType) &Rectangle_printShape}; 

struct Rectangle *Rectangle_Rectangle(struct Rectangle *this, const char *name, double length, double width) {
   Square_Square( (struct Square *)this, name, length);
   this->vPointer = Rectangle_Vtable;
   this->myWidth  = width;
}

//################################################ Main
int main (void) {
   struct Shape *picture[8]; //array of pointers, each pointing to a Shape bc the class constructor returns pointer
   picture[0] = (struct Shape *) Triangle_Triangle( (struct Triangle*) malloc(sizeof (struct Triangle)), "FirstTriangle", 5, 5);
   picture[1] = (struct Shape *) Triangle_Triangle( (struct Triangle*) malloc(sizeof (struct Triangle)), "SecondTriangle", 4, 3);
   picture[2] = (struct Shape *) Circle_Circle( (struct Circle*) malloc(sizeof (struct Circle)), "FirstCircle", 5);
   picture[3] = (struct Shape *) Circle_Circle( (struct Circle*) malloc(sizeof (struct Circle)), "SecondCircle", 10);
   picture[4] = (struct Shape *) Square_Square( (struct Square*) malloc(sizeof (struct Square)), "FirstSquare", 5);
   picture[5] = (struct Shape *) Square_Square( (struct Square*) malloc(sizeof (struct Square)), "SecondSquare", 10);
   picture[6] = (struct Shape *) Rectangle_Rectangle ( (struct Rectangle*) malloc(sizeof (struct Rectangle)), "FirstRectangle", 4, 8);
   picture[7] = (struct Shape *) Rectangle_Rectangle ( (struct Rectangle*) malloc(sizeof (struct Rectangle)), "SecondRectangle", 8, 4);
   double total = 0;
   int i = 0;
   for (i = 0; i < 8; i++) {
      total += (*(picture[i]->vPointer[0])) (picture[i]); //func 0 is area()
      (*(picture[i]->vPointer[2])) (picture[i]); //eventhough picture[i] is shape but its real block is Triangle, or Square, etc.
   }
   printf("\n");
   for (i = 0; i < 8; i++) {
      (*(picture[i]->vPointer[1])) (picture[i]); //draw()
      free(picture[i]); //free all the memory block of the class instances, which is called by malloc()
   }  
   printf("The total area of the shapes on this picture is %.3f\n", total);
   
}
