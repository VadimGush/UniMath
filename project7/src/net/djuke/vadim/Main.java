package net.djuke.vadim;

import net.djuke.vadim.math.Matrix;

public class Main {

    public static void main(String... args) {
        calculate();
    }

    private static void calculate() {
        Matrix matrix = new Matrix(3);
        double[][] matrixData = {
                {2, 2, 3},
                {2, 1, 1},
                {4, 2, 0}
        };
        matrix.setMatrix(matrixData);
        matrix.print();

        matrix.invert();
    }
}
