#include <iostream>
#include <cmath>
#include <iomanip>
#include <fstream>
using namespace std;

// Структура для записи значения функций
struct functionValue {
	double argument;
	double value;
	int termsCount;
};
// Основная константа
const float v = 6;
// Входные данные
float epsilon = pow(10,-4);
float a = 0;
float b = 1;
int segments = 5;
int pointPerSegment = 1;

// Прототипы используемых функций
float sum(float, int*);
double fact(int);
void input();
void printLine(int);
double interpolation(double, double, double, double, double);

int main() {
	// Читаем входные данные
	input();

	// Рисуем линию сверху
	printLine(segments+1);
	// Выводим значения x на экран
	float delta = (b - a) / segments;
	for (int i = 0; i <= segments; i++)
		cout << setw(20) << left << a + delta * i;
	cout << endl;

	// Подсчитываем значение функции и выводим её на экран
	functionValue function[segments+1];
	for (int i = 0; i <= segments; i++) {
		// Запоминаем аргумент функции, значение функции и количество слагаемых
		function[i].argument = a + delta * i;
		function[i].value = sum(function[i].argument, &function[i].termsCount);
		// Выводим значение
		cout << setw(20) << left << function[i].value;
	}
	cout << endl;

	// Выводим массив количества слагаемых
	for (int i = 0; i <= segments; i++) 
		cout << setw(20) << left << function[i].termsCount;
	cout << endl;
	printLine(segments+1);

	// Создадим файл для вывода данных
	ofstream ofs("data.log");
	
	// Получаем значения 
	cout << "Write count of points per segment: ";
	cin >> pointPerSegment; // вводим количество точек на сегмент функции
	int count = (segments) * (pointPerSegment+1); // количество точек в интерполянте
	double interpolator[count]; // массив для хранения значений интерполянты
	printLine(count); // рисуем разделяющую линию
	int id = 0; // индекс текущего значения интерполянты
	for (int i = 0; i<segments; i++) {
		double delta = (function[i+1].argument - function[i].argument) / (pointPerSegment+1);
		// выводим текущий аргумент функции
		cout << setw(20) << left << function[i].argument;
		ofs << function[i].argument << " " << function[i].value << " ?0" << endl;
		interpolator[id] = function[i].value;
		id++;
		for (int c=1; c<=pointPerSegment; c++) {
			double step = delta * c;
			// Выводим текущий аргмент интерполянты
			cout << setw(20) << left << function[i].argument + step;
			// Расчитываем значение интерполянты
			interpolator[id] = interpolation(function[i].argument + step, function[i].argument, function[i+1].argument, function[i].value, function[i+1].value);
			// Значение интерполянты записываем в файл
			ofs << function[i].argument + step << " ?0 " << interpolator[id] << endl;
			// обновляем индекс интерполянты
			id++;
		}
	}
	cout << endl;

	// Закрываем запись в файл
	ofs.close();

	// Выводим значения интерполянты на экран
	for (int i=0; i<count; i++) {
		cout << setw(20) << left << interpolator[i];
	}
	cout << endl;
	printLine(count);

	return 0;
}

/*
 * Функция линейной интерполяции
 */
double interpolation(double x, double xk, double xk1, double fk, double fk1) {
	return ((x-xk) / (xk1 - xk)) * (fk1 - fk) + fk;
}

/*
 * Вычесляем сумму, передавая аргумент x и ссылку на переменную 
 * хранящую количество слагаемых для данной функции
 */
float sum(float x, int* count) {
	double sum = 0;
	int i = 1;
	// Считаем сумму до тех пор, пока один из элементов не станет меньше epsilon
	while (true) {
		double element = pow(-1,i+1) * (pow(v*x,i+(i-1)) / fact(i+(i-1)));

		if (abs(element) < epsilon) break;
		else {
			sum += element; 
			i++;
		}
	}
	*count = i-1;

	return sum;
}

/**
 * Считаем факториал
 */
double fact(int n) {
	double result = 1;
	for (int counter = 1; counter <= n; counter++) {
		result *= counter;
	}
	return result;
}

/**
 * Читаем входные данные
 */
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

void printLine(int c) {
	// Рисуем таблицу
	for (int i = 0; i < 20 * c; i++) {
		cout << "-";
	}
	cout << endl;
}
