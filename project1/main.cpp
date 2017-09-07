#include <iostream>
#include <cmath>
#include <iomanip>
using namespace std;

const float v = 6;
// Входные данные
float epsilon = pow(10,-4);
float a = 0;
float b = 1;
int segments = 5;

// Прототипы используемых функций
float sum(float x, int* count);
double fact(int n);
void input();
void printLine();

int main() {
	input(); // Читаем входные данные 

	// Рисуем линию сверху
	printLine();
	// Выводим значения x на экран
	float delta = (b - a) / segments;
	for (int i = 0; i <= segments; i++) {
		cout << setw(20) << left << a + delta * i;
	}
	cout << endl;
	// Выводим значения суммы на экран
	int counts[segments+1];
	for (int i = 0; i <= segments; i++) {
		cout << setw(20) << left << sum(a + delta * i, &counts[i]);
	}
	cout << endl;
	// Выводим массив количества слагаемых
	for (int i = 0; i <= segments; i++) {
		cout << setw(20) << left << counts[i];
	}
	cout << endl;
	// Рисуем линию снизу
	printLine();

	return 0;
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
		double element = pow(-1,i) * (pow(v*x,i+(i-1)) / fact(i+(i-1)));

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

void printLine() {
	// Рисуем таблицу
	for (int i = 0; i < 20 * (segments+1); i++) {
		cout << "-";
	}
	cout << endl;
}
