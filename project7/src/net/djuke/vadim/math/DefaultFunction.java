package net.djuke.vadim.math;

public class DefaultFunction {

    private static final double V = 6;
    private static final double EPSILON = 0.0001;

    /**
     * Вычисляем стандартную функцию
     * @param n количество точек
     * @param a начала отрезка
     * @param b конец отрезка
     * @return значения функции
     */
    public static FunctionData calculate(int n, double a, double b) {
        FunctionData data = new FunctionData();
        double delta = (b - a) / n;
        for (int i = 0; i <= n; i++) {
            double argument = a + delta * i;
            data.putValue(argument, function(argument));
        }
        return data;
    }

    private static double function(double x) {
        double sum = 0;
        for (int i = 1; true; i++) {
            double element = Math.pow(-1,i+1) * (Math.pow(V*x, i+(i-1)) / fact(i+(i-1)));
            if (Math.abs(element) < EPSILON) break;
            else {
                sum += element;
            }
        }
        return sum;
    }

    private static double fact(int i) {
        double result = 1;
        for (int counter = 1; counter <= i; counter++) {
            result *= counter;
        }
        return result;
    }


}
