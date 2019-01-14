#include <stdlib.h>
#include <stdio.h>
int i = 3;
int a[4] = {0, 3, 1, 2};
void Put(int v[]) {
  printf("a = (");
  for (i = 1; i <= 3; i++) {
    printf("%d", v[i]);
    if (i<3) putchar(' ');
  }
  printf(")\n");
}

void Do_Something( int * (*t)() ) {
        i  =   i  - 1;
        *((*t)())  =   i  + 1;
        a[*((*t)())] = a[i] + 1;  
        a[i] = a[*((*t)())] + 1;
        i  =   i  - 1;
        a[*((*t)())] =   *((*t)())  - 1;
        a[i] = a[*((*t)())] - 1;
}
int *thunk()
{
        return &( a[i] );
}

int main()
{
        Do_Something( thunk );
        Put( a );
        return 0;
}

