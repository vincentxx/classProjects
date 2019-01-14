#Require java 1.8.0_111 or 1.8.0_181 (on openlab), using javafx to build GUI.
#Command: make main to compile and run it.
main: 	
	make clean 	
	java -version
	mkdir ./build &&	javac -d build src/FXOSSimmulation.java
	cd ./build &&  jar cfm FXOSSimmulation.jar ../src/MANIFEST.MF fxossimmulation/*
	java -jar build/FXOSSimmulation.jar

clean:
	rm -rf build
	rm -rf outputs/Printer*

