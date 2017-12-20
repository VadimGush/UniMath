package net.djuke.vadim;

/**
 * Matrix - класс матрицы, над которой мы будем проводить расчёты
 */
public class Matrix {

    private final double v = 6; // номер варианта
    private double[][] matrix; // матрица коэффеициентов
    private int n; // количество столбцов
    private double[] vector; // допольнительная строка

    // Для проверки сохраним исходную СЛАУ
    private double[][] sourceMatrix;
    private double[] sourceVector;

    // Переменные для реализации прямого ходя метода Гаусса
    private int pos = 0;

    private double[] result;

    /**
     * Создать пустую матрицу
     * @param n размер квадратной матрицы
     */
    public Matrix(int n) {
        this.n = n;
        vector = new double[n];
        matrix = new double[n][n];
        result = new double[n];
        sourceMatrix = new double[n][n];
        sourceVector = new double[n];
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

        saveMatrix();
    }

    /**
     * Один шаг прямого хода метода Гаусса
     */
    public boolean forwardStep() {
        if (pos<n) {
            // Нормирование строки
            double element = matrix[pos][pos];
            for (int i = pos; i <= n; i++) {
                if (i<n) matrix[pos][i] /= element;
                else vector[pos] /= element;
            }
            // Проводим операции над строками снизу
            for (int m = pos + 1; m < n; m++) {
                double first = matrix[m][pos];
                for (int i = 0; i <= n; i++) {
                    if (i<n) matrix[m][i] -= matrix[pos][i] * first;
                    else vector[m] -= vector[pos] * first;
                }
            }
            pos++;
            return true;
        } else {
            pos = n;
            return false;
        }
    }

    /**
     * Один шаг обратного хода метода Гаусса
     */
    public boolean backwardStep() {
        pos--;
        if (pos > -1) {
            // мы берём первый элемент из последнего стобца
            double sm = vector[pos];
            for (int i=0;i<(n-1)-pos;i++) {
                sm -= matrix[pos][(n-1)-i] * result[(n-1)-i];
            }
            result[pos] = sm;
            return true;
        } else {
            return false;
        }
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
                sum += sourceMatrix[m][i] * result[i];
            }
            check = check & (sum == sourceVector[m]);
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

    private void saveMatrix() {
        for (int m = 0; m<n; m++) {
            for (int i = 0; i<n; i++) {
                sourceMatrix[m][i] = matrix[m][i];
            }
        }
        System.arraycopy(vector, 0, sourceVector, 0, n);
    }

    public double[] getVector() {
        return vector;
    }

    public double[][] getMatrix() {
        return matrix;
    }

    public double[] getResult() {
        return result;
    }

    public int getSize() {
        return n;
    }

}

