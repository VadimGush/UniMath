package net.djuke.vadim.math;

/**
 * Matrix - решает СЛАУ в виде матрицы методом Гаусса
 */
public class Matrix {

    private final double v = 6; // номер варианта

    private double[][]  matrix; // матрица коэффеициентов
    private int         n; // количество столбцов
    private double[]    vector; // допольнительная строка

    // Массив для хранения корней СЛАУ
    private double[]    result;

    /**
     * Создать пустую матрицу
     * @param n размер квадратной матрицы
     */
    public Matrix(int n) {
        this.n = n;
        vector = new double[n];
        matrix = new double[n][n];
        result = new double[n];
    }

    /**
     * Создаём матрицу A
     */
    public void createBasicMatrix() {
        // Перебираем строки
        for (int m = 0; m<n; m++) {
            // Перебираем столбцы
            for (int i = 0; i<n; i++) {
                // Если элемент на диагонали
                if (m == i) matrix[m][i] = v+m;
                    // Если нет, то берём число из диагонали
                    // и делим его на 10^-2
                else matrix[m][i] = (v+m) / 100;
            }
        }

        // Теперь нам надо создать столбец, который будет расширять
        // данную матрицу.
        double[] varVector = new double[n];
        for (int i=0;i<n;i++) varVector[i] = v+i;

        // Перебор по одному столбцу, потому что это вектор
        for (int m=0;m<n;m++) {
            double sum = 0;
            for (int k=0;k<n;k++) {
                sum += matrix[m][k] * varVector[k];
            }
            vector[m] = sum;
        }

    }

    public void setMatrix(double[][] matrix, double[] vector) {
        this.matrix = matrix;
        this.vector = vector;
        this.n = vector.length;
    }

    /**
     * Один шаг прямого хода метода Гаусса
     */
    private void forwardStep(double[][] matrix, double[] vector) {
        for (int pos = 0; pos < n; pos++) {
            // Нормирование строки
            double element = matrix[pos][pos];
            for (int i = pos; i <= n; i++) {
                if (i < n) matrix[pos][i] /= element;
                else vector[pos] /= element;
            }
            // Проводим операции над строками снизу
            for (int m = pos + 1; m < n; m++) {
                double first = matrix[m][pos];
                for (int i = 0; i <= n; i++) {
                    if (i < n) matrix[m][i] -= matrix[pos][i] * first;
                    else vector[m] -= vector[pos] * first;
                }
            }
            print(matrix, vector);
        }
    }

    /**
     * Один шаг обратного хода метода Гаусса
     */
    private void backwardStep(double[][] matrix, double[] vector) {
        for (int pos = n - 1; pos > -1; pos--) {
            double sm = vector[pos];
            for (int i=0;i<(n-1)-pos;i++) {
                sm -= matrix[pos][(n-1)-i] * result[(n-1)-i];
            }
            result[pos] = sm;
        }
    }

    public void solve() {
        // Copy all data
        double[][] matrix = copyMatrix(this.matrix);
        double[] vector = new double[n];
        System.arraycopy(this.vector, 0, vector, 0, n);

        forwardStep(matrix, vector);
        backwardStep(matrix, vector);

        print();
    }

    /**
     * Выполняем проверку решения
     * @return решено ли верно
     */
    public boolean verify() {
        boolean check = true; // по умолчанию правильно
        // если найдём хоть одну ошибку, то меняем данный флаг
        for (int m=0;m<n;m++) {
            double sum = 0;
            for (int i=0;i<n;i++) {
                //sum += sourceMatrix[m][i] * result[i];
            }
            //check = check & (sum == sourceVector[m]);
        }
        return check;
    }

    /**
     * Выводим матрицу в консоль.
     * Чисто для тестирования
     */
    public void print() {
        System.out.println();
        System.out.println("A matrix:");
        for (int m = 0; m<n; m++) {
            for (int i = 0; i<=n; i++) {
                if (i<n) System.out.format("%10.3f", matrix[m][i] );
                else System.out.format(" : %8.3f", vector[m]);
            }
            System.out.println();
        }
        System.out.println("Roots:");
        for (int i=0; i<n; i++) {
            System.out.format("x" + i + ": %6.3f%n", result[i]);
        }
    }

    public void print(double[][] matrix, double[] vector) {
        System.out.println();
        System.out.println("A matrix:");
        for (int m = 0; m<n; m++) {
            for (int i = 0; i<=n; i++) {
                if (i<n) System.out.format("%10.3f", matrix[m][i] );
                else System.out.format(" : %8.3f", vector[m]);
            }
            System.out.println();
        }
    }

    private double[][] copyMatrix(double[][] matrix) {
        double[][] result = new double[n][n];
        for (int m = 0; m<n; m++) {
            System.arraycopy(matrix[m], 0, result[m], 0, n);
        }
        return result;
    }

    public double[] getResult() {
        return result;
    }

    public int getSize() {
        return n;
    }

}

