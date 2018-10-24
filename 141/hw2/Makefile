CXXFLAGS=-std=c++11 -Wpedantic -Wall -Wextra -Werror -Wzero-as-null-pointer-constant
CFLAGS=-std=c99 -pedantic -Wall -Wextra -Werror -ansi -Wwrite-strings

main: ShapeTest.c
	echo ----------compiling main.cpp to create executable program main-------------
	gcc ShapeTest.c -o ShapeTest -lm
#g++ -Weffc++ -ggdb ShapeTest.c -o ShapeTest

clean:
	echo ----------removing the executable program main-----------------------------
	/bin/rm ShapeTest

test:
	create_script ShapeTest.c ShapeTest
