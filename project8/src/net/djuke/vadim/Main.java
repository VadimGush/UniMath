package net.djuke.vadim;

import net.djuke.vadim.math.Matrix;

public class Main {

    public static void main(String[] args) {
        Matrix matrix = new Matrix(3);
        double[][] matrixData = {
                {2, 1, 0},
                {1, 3, 1},
                {2, 1, 5}
        };
        double[] vector = {
                7,
                16,
                32
        };
        matrix.setMatrix(matrixData, vector);
        matrix.print();
        matrix.iteration(3);
    }
}
