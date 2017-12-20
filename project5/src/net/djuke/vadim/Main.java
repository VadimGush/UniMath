package net.djuke.vadim;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.ActionEvent;

public class Main {

    /*
    Изначально я хотел написать программу с более приятным интерфейсом, используя
    JavaFX, но проблема в том, что это бы заняло у меня немного больше времени чем обычно, так как
    я бы как минимум увлёкся созданием красивого шаблона в JavaFX Scene Builder.

    Поэтому было решено использовать старый добрый Swing. Хотя и выглядит он плохо
     */
    private static JFrame window;
    private static Matrix matrix;
    private static boolean forwardDone = false;

    public static void main(String[] args) {
        matrix = new Matrix(5);
        matrix.createBasicMatrix();
        //matrix.createAnotherMatrix();
        /*
        matrix.print();
        while (matrix.forwardStep()) {
            matrix.print();
        }
        // Обратный порядок
        while (matrix.backwardStep()) {
            matrix.print();
        }
        System.out.println(matrix.verify());
        */
        createWindow();
    }

    private static void createWindow() {
        window = new JFrame("Main window");
        window.setDefaultCloseOperation(3);
        window.setSize(500, 400);
        window.setResizable(false);

        // Окно должно быть в центре экрана
        Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
        window.setLocation(dim.width/2-window.getSize().width/2, dim.height/2-window.getSize().height/2);

        JPanel tablesPanel = new JPanel();
        tablesPanel.setMinimumSize(new Dimension(500,400));
        tablesPanel.setLayout(new BoxLayout(tablesPanel, BoxLayout.Y_AXIS));
        tablesPanel.setBorder(new EmptyBorder(10, 10, 10, 10));

        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.X_AXIS));
        mainPanel.add(tablesPanel);
        mainPanel.add(new JSeparator(1));
        mainPanel.add(new JPanel());
        mainPanel.add(Box.createRigidArea(new Dimension(400,1)));

        // Здесь добавляем основные компоненты на панель
        MatrixModel matrixModel = new MatrixModel(matrix);
        JTable matrixTable = new JTable(matrixModel);
        matrixTable.setAutoResizeMode(JTable.AUTO_RESIZE_ALL_COLUMNS);
        matrixTable.setPreferredScrollableViewportSize(new Dimension(400,400));

        //DefaultTableCellRenderer centerRenderer = new DefaultTableCellRenderer();
        //centerRenderer.setHorizontalAlignment( JLabel.CENTER );
        //matrixTable.setDefaultRenderer(Double.class, centerRenderer);

        // matrixTable.setFillsViewportHeight(true);
        tablesPanel.add(new JLabel("Расширенная матрица коэффициентов:"));
        tablesPanel.add(Box.createRigidArea(new Dimension(0, 5)));
        tablesPanel.add(new JScrollPane(matrixTable, JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED, JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED));

        tablesPanel.add(Box.createRigidArea(new Dimension(0, 10)));

        tablesPanel.add(new JLabel("Результат:"));
        tablesPanel.add(Box.createRigidArea(new Dimension(0, 5)));
        JLabel status = new JLabel(" >> выполняется прямой ход");
        tablesPanel.add(status);

        tablesPanel.add(Box.createRigidArea(new Dimension(0, 10)));
        JButton var9 = new JButton("Выполнить шаг");
        var9.addActionListener((ActionEvent var1) -> {
            if (!forwardDone) {
                if (matrix.forwardStep()) matrixTable.repaint();
                else forwardDone = true;
            } else {
                matrix.backwardStep();
                StringBuilder string = new StringBuilder();
                string.append("<html>");
                for (int i=0;i<matrix.getSize();i++) {
                    string.append("x<sub>" + i + "</sub> = " + String.format("%6.3f%n", matrix.getResult()[i]) + "<br>");
                }
                string.append("</html>");
                status.setText(string.toString());
            }
        });
        tablesPanel.add(var9);

        window.getContentPane().add(mainPanel);
        window.setVisible(true);
    }


}
