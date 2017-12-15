#include <math.h>
#include <stdlib.h>
#include <stdio.h>
#include <stdlib.h>
#include "gmath.h"

// Расчёт факториала
double fact(int n) {
        double result = 1;
        for (int counter = 1; counter <= n; counter++) {
                result *= counter;
        }
        return result;
}

// Расчёт функции
struct function_value function(double x) {
        struct function_value result;
        result.argument = x;
        for (int i=1; 1; i++) {
                double element = pow(-1.0,i+1) * (pow(VAR*x,i+(i-1)) / fact(i+(i-1)));
                if (fabs(element) < EPSILON) break; 
                else result.value += element;
        }
        return result;
}

// Линейная интерполяция
double linear_interpolation(double x, struct function_value point1, struct function_value point2) {
        return ((x - point1.argument) / (point2.argument - point1.argument)) * (point2.value - point1.value) + point1.value;
}

// Интерполяционный многочлен в форме Лагранджа
double lan_interpolation(double x, struct function_value function[], int count) {
        double sum = 0;
        for (int i=0; i<count; i++) {
                double element = function[i].value;
                double a = 1;
                double b = 1;
                for (int c=0;c<count;c++) {
                        if (i == c) continue;
                        a *= (x - function[c].argument);
                        b *= (function[i].argument - function[c].argument);
                }
                element *= a / b;
                sum += element;
        }
        return sum;
}

/*
 * Тут должен был быть реализован интерполяционный многочлен в форме Ньютона, но я 
 * во время написания решил забросить это дело (хотя у меня и есть реализация данного метода интерполяции
 * на Java).
 */

