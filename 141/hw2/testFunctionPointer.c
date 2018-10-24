#include <stdio.h>

typedef int (*functionPointer)(void);
typedef functionPointer funcPointer;

typedef int aIntType;
struct people {
   int age;
   
};
int f1 () {
  return 100;
}

//functionPointer func = &f1;  //ok
//functionPointer func[] = { &f1, &f1}; //ok
//functionPointer func[2]; // ok
//func[1] = &f1; //not ok

int main (void) {
   struct people a;
   a.age = 10;
   char *ptr;
   char s[]="adsf"; 
   ptr = s;
   
   functionPointer fp;
   int (*fp2)(void) = &f1;
   fp = &f1;
   aIntType x;
   x=1000;
   printf("x is %d\n", x);
   printf ("return %d\n", (*fp)() );
   return 0;
}
