#include <cmath>
#include <iostream>
using namespace std;

// Функция для расчёта факториала
double fact(int n) {
		double result = 1;
		for (int counter = 1; counter <= n; counter++) {
			result *= counter;
		}
		return result;
}

// Наша основная функция
int main() {
	double v = 6; // номер варианта
	double epsilon = pow(10, -4); // бесконечно малое
	double sum = 0; // наша конечная сумма
	int i = 1; // номер элемента
	double x = 1; // аргумент функции

	// Ввод данных реализуейте сами, так как это просто

	// Считаем нашу сумму
	while (true) {
		double element = pow(-1,i+1) * (pow(v*x,i+(i-1)) / fact(i+(i-1)));

		if (abs(element) < epsilon) break;
		else {
			sum += element;
			i++;
		}
	}

	// Выводим значение суммы на экран
	cout << sum << endl;
	
	return 0;
}
