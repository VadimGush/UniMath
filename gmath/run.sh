clear;
(echo "Compiling..." ; gcc src/main.c src/gmath.c -lm -o bin/gmath) && 
(
        echo "Running...";
        ./bin/gmath;
        echo "Creating library...";
        gcc -c -Wall -Werror -fpic src/gmath.c -o lib/gmath.o &&
        (
                gcc -shared lib/gmath.o -o lib/gmathlib.so &&
                cp src/gmath.h lib/gmath.h
                rm lib/gmath.o
        )
)
