CXXFLAGS=-std=c++11 -Wpedantic -Wall -Wextra -Werror -Wzero-as-null-pointer-constant
CFLAGS=-std=c99 -pedantic -Wall -Wextra -Werror -ansi -Wwrite-strings

main: main.cpp
	echo ----------compiling main.cpp to create executable program main-------------
	g++ -Weffc++ -ggdb main.cpp -o main

clean:
	echo ----------removing the executable program main-----------------------------
	/bin/rm main

test:
	create_script main.cpp main
