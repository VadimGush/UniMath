#include <iostream>
#include <cmath>
#include <iomanip>
#include <fstream>
using namespace std;

// Структура для записи значений функции
struct functionValue {
	double argument;	// аргумент функции
	double value;		// значение функции
	int termsCount;		// количество слагаемых
};
// Основная константа
const float v 	= 5; 		// номер варианта
float epsilon 	= pow(10,-4); 	// заданная точность
int segments 	= 5;		// количество сегментов
// Точки задающие границы отрезка
float a;
float b;

// Прототипы функций
float	sum(float, int*);	// расчёт функции
double	fact(int);		// расчёт факториала
void	input();		// ввод глобальных данных 
void	printLine(int);		// вывод линии для таблицы
// Разнообразные интерполянты
double	interpolation(double, double, double, double, double);		// линейная интерполяция
double	interpolation2(double x, functionValue function[], int lenght);	// интерполяционный многочлен Лагранджа

int main() {
	input(); // читаем данные

	// Создаём массив для хранения аргмунтов и значений функции
	functionValue function[segments+1];

	// ПОДСЧЁТ И ВЫВОД АРГУМЕНТОВ ФУНКЦИИ
	printLine(segments+1);
	float delta = (b - a) / segments;
	for (int i = 0; i <= segments; i++) {
		double argument = a + delta * i;	// считаем аргумент
		function[i].argument = argument;	// записываем в массив
		cout << setw(20) << left << argument;	// выводим на экран
	}
	cout << endl;

	// ПОДСЧЁТ И ВЫВОД ЗНАЧЕНИЙ ФУНКЦИИ 
	for (int i = 0; i <= segments; i++) {
		function[i].value = sum(function[i].argument, &function[i].termsCount);
		cout << setw(20) << left << function[i].value;
	}
	cout << endl;

	// ВЫВОД КОЛИЧЕСТВА СЛАГАЕМЫХ
	for (int i = 0; i <= segments; i++) 
		cout << setw(20) << left << function[i].termsCount;
	cout << endl;
	printLine(segments+1);

	// Создадим файл для вывода данных
	ofstream ofs("data.log");
	
	// ПОДСЧЁТ И ВЫВОД ИНТЕРПОЛЯНТЫ 
	// Количество точек между узлами
	int pointPerSegment;
	cout << "Write count of points per segment: ";
	cin >> pointPerSegment;

	// Количество точек в интерполянте
	int count = (segments + 1) + (segments * pointPerSegment);
	// Массив для хранения значений интерполянты
	double interpolator[count];

	printLine(count);	// рисуем разделяющую линию
	int id = 0;		// индекс текущего значения интерполянты
	for (int i = 0; i<segments; i++) {
		// Расстояние между точками
		double delta = (function[i+1].argument - function[i].argument) / (pointPerSegment+1);
		// Вывод значения функции
		cout << setw(20) << left << function[i].argument;
		// Запись значения функции в файл
		ofs << function[i].argument << " " << function[i].value << " ?0" << endl;
		interpolator[id] = function[i].value;
		id++;
		for (int c=1; c<=pointPerSegment; c++) {
			// Расстояние между точками
			double step = delta * c;
			// Выводим текущий аргумент интерполянты
			cout << setw(20) << left << function[i].argument + step;
			// Расчитываем значение интерполянты
			interpolator[id] = interpolation2(function[i].argument + step, function, segments+1);
			// interpolator[id] = interpolation(function[i].argument + step, function[i].argument, function[i+1].argument, function[i].value, function[i+1].value);
			// Значение интерполянты записываем в файл
			ofs << function[i].argument + step << " ?0 " << interpolator[id] << endl;
			// Переходим к следующему значению интерполянты
			id++;
		}
	}
	// Запись последнего элемента
	ofs << function[segments].argument << " " << function[segments].value << " ?0" << endl;
	cout << setw(20) << left << function[segments].argument;
	interpolator[id] = function[segments].value;
	cout << endl;

	// Закрываем запись в файл
	ofs.close();

	// ВЫВОД ЗНАЧЕНИЙ ИНТЕРПОЛЯНТЫ
	for (int i=0; i<count; i++) {
		cout << setw(20) << left << interpolator[i];
	}
	cout << endl;
	printLine(count);

	return 0;
}

// Линейная интерполяция
double interpolation(double x, double xk, double xk1, double fk, double fk1) {
	return ((x-xk) / (xk1 - xk)) * (fk1 - fk) + fk;
}

// Интерполяционный многочлен Лагранджа
double interpolation2(double x, functionValue function[], int count) {
	double sum = 0;
	for (int i=0; i<count; i++) {
		double element = function[i].value;
		// Считаем числитель
		double a = 1;
		for (int c=0; c<count; c++) {
			if (i == c) continue;
			a *= (x - function[c].argument);
		}
		// Считаем знаменатель
		double b = 1;
		for (int c=0; c<count; c++) {
			if (i == c) continue;
			b *= (function[i].argument - function[c].argument);
		}
		// Считаем слагаемое
		element *= a / b;
		sum += element;
	}
	return sum;
}

// Вычесляем функциональный ряд
float sum(float x, int* count) {
	double sum = 0;
	*count = 0;
        cout << endl;
        cout << "------------------------------------" << endl;
	for (int i = 1; true; i++) {
		double element = pow(-1,i+1) * (pow(v*x,i+(i-1)) / fact(i+(i-1)));
                if (x == -2.0) cout << i << ": " << element << ": " << fact(i+(i-1)) << endl;
		if (abs(element) < epsilon) break;
		else {
			sum += element;
			*count = i-1;
		}
	}
        cout << "------------------------------------" << endl;
	return sum;
}

// Считаем факториал
double fact(int n) {
	double result = 1;
	for (int counter = 1; counter <= n; counter++) {
		result *= counter;
	}
	return result;
}

// Читаем входные данные 
void input() {
	float input = 0;
	cout << "Write epsilon (0 for default value): ";
	cin >> input;
	if (input != 0) epsilon = input;

	int seg = 0;
	cout << "Count of segments (0 for default value): ";
	cin >> seg;
	if (seg != 0 & seg > 0) segments = seg;

	cout << "Write a: ";
	cin >> input;
	a = input;

	cout << "Write b: ";
	cin >> input;
	b = input;
}

// Рисуем линию
void printLine(int c) {
	for (int i = 0; i < 20 * c; i++) cout << "-";
	cout << endl;
}
