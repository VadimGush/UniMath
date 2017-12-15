#define EPSILON 0.00001  // точность расчётов
#define VAR     6.0      // номер варианта

// Структура для хранения значения функции
struct function_value {
        double argument;        // аргумент функции в данной точке
        double value;           // значение функции в данной точке
};

struct function_value   function(double x);     // наша функция (функциональный ряд)
double                  fact(int n);            // фукнция для расчёта факториала

// Выполняет линейную интерполяцию между двумя точками point1 and point2
double                  linear_interpolation(double x, struct function_value point1, struct function_value point2);

// Интерполяционный многочлен в форме Гагранджа
double                  lan_interpolation(double x, struct function_value function[], int count);

double                  new_interpolation(double x, struct function_value function[], int count);
