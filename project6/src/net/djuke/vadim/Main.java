package net.djuke.vadim;

import net.djuke.vadim.math.DefaultFunction;
import net.djuke.vadim.math.FunctionData;
import net.djuke.vadim.math.Matrix;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;

public class Main {

    private static JFrame window;

    public static void main(String[] args) {
        createWindow();
        calculate();
        window.setVisible(true);
    }

    private static void createWindow() {
        window = new JFrame("Result");
        window.setDefaultCloseOperation(3);
        window.setSize(800, 100);
        window.setResizable(false);

        Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
        window.setLocation(dim.width/2-window.getSize().width/2, dim.height/2-window.getSize().height/2);
    }

    private static void calculate() {
        // Вычисляем значения функции
        FunctionData function = DefaultFunction.calculate(6,0.5,2.5);
        System.out.println("Function: ");
        for (Double arg : function.getArguments()) {
            System.out.printf("%.3f : %6.3f\n", arg, function.getValue(arg));
        }
        // Создаём СЛАУ и преобразуем её в матрицу
        int n = function.getArguments().size();
        Matrix matrix = new Matrix(n);
        double[][] matrixData = new double[n][n];
        for (int r = 0; r < n; r++) {
            for (int c = n-1; c >= 0; c--) {
                matrixData[r][c] = Math.pow(function.getArguments().get(r), (n-1)-c);
            }
        }
        double[] vector = new double[n];
        for (int r = 0; r < n; r++) {
            vector[r] = function.getValues().get(r);
        }
        matrix.setMatrix(matrixData, vector);
        // Решаем СЛАУ
        matrix.solve();

        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.Y_AXIS));
        mainPanel.setBorder(new EmptyBorder(10, 10, 10, 10));

        // Отображаем таблицу значений на экране
        JLabel label = new JLabel();
        // Формируем многочлен в общем виде
        StringBuilder formula = new StringBuilder();
        formula.append("<html>Интерполяционный многочлен в общем виде:<br>P<sub>");
        formula.append(n-1);
        formula.append("</sub>(x) = ");
        for (int i = n-1; i >= 1; i--) {
            if (matrix.getResult()[i] != 0) {
                if (matrix.getResult()[i] < 0) formula.append("-"); else formula.append("+");
                formula.append(String.format("%.3f",Math.abs(matrix.getResult()[i])));
                formula.append("*x<sup>");
                formula.append(i);
                formula.append("</sup>");
            }
        }
        if (matrix.getResult()[0] < 0) formula.append("-"); else formula.append("+");
        formula.append(String.format("%.3f", Math.abs(matrix.getResult()[0])));
        formula.append("</html>");
        label.setText(formula.toString());
        mainPanel.add(label);

        window.getContentPane().add(mainPanel);

        // Строим СЛАУ и находим корни

        // Пишем на экране интерполяционный многочлен в общем виде
    }


}
