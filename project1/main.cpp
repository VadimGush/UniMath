#include <iostream>
#include <cmath>
using namespace std;

// Global variables
const float v = 6;
// Input data
float epsilon = pow(10,-4);
float a = 0;
float b = 1;

// Prototypes of functions
float sum(float x, int* count);
int fact(int n);
void input();

int main() {
	// Read input data
	input();

	int i_val = 7;
	cout << "Sum: " << sum(0.01f, &i_val) << endl;
	cout << "Summands: " << i_val << endl;
	return 0;
}

/*
 * Calculates the sum
 * x - variable
 * count - pointer to count of summands 
 */
float sum(float x, int* count) {
	float sum = 0;

	int i = 1;
	while (true) {
		float element = 
			pow(-1,i) * // sign of element
			(pow(v*x,i+(i-1)) / (float)fact(i+(i-1)));

		cout << i << ": " << pow(v*x,i+(i-1)) << " , " << fact(i+(i-1)) << endl;

		if (i > 20) break;
		if (abs(element) < epsilon) break;
		else {
			sum += element;
			i++;
		}
	}
	*count = i-1; // set number of summands

	return sum;
}

/*
 * Calculates the factorial
 * n - input number
 */
int fact(int n) {
	int result = 1;
	for (int counter = 1; counter <= n; counter++) {
		result *= counter;
	}
	return result;
}

/**
 * Reads input data
 */
void input() {
	float input = 0;
	cout << "Write (0 for default value): ";
	cin >> input;
	if (input != 0) epsilon = input;
	cout << "Write a: ";
	cin >> input;
	a = input;
	cout << "Write b: ";
	cin >> input;
	b = input;
}
