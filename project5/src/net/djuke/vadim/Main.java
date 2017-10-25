package net.djuke.vadim;

import net.djuke.vadim.data.Data;
import net.djuke.vadim.data.DifTableModel;
import net.djuke.vadim.data.FunctionTableModel;
import net.djuke.vadim.graphics.Graphic;
import net.djuke.vadim.math.DefaultFunction;
import net.djuke.vadim.math.Function;
import net.djuke.vadim.math.Interpolate;
import net.djuke.vadim.math.NewtonInterpolate;
import net.djuke.vadim.util.Timer;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.*;
import javax.swing.border.EmptyBorder;

public class Main {
    private static JFrame settings;
    private static JFrame window;

    private static Data data;

    public Main() {
    }

    public static void main(String... var0) {
        createSettingsWindow();
    }

    private static void createSettingsWindow() {
        // Create window for settings
        settings = new JFrame("");
        settings.setDefaultCloseOperation(3);
        settings.setSize(380, 410);
        settings.setResizable(false);

        Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
        settings.setLocation(dim.width/2-settings.getSize().width/2, dim.height/2-settings.getSize().height/2);

        JPanel var0 = new JPanel();
        var0.setBorder(new EmptyBorder(10, 10, 10, 10));
        var0.setLayout(new BoxLayout(var0, 1));
        JLabel var1 = new JLabel();
        var1.setText("<html><center><font color=blue size=+1>VINTER</font><br>Программа для расчёта функцонального ряда и построения интерполянты<br>Автор: Вадим Гуш</center></html>");
        var0.add(Box.createRigidArea(new Dimension(0, 10)));
        var0.add(var1);
        var0.add(Box.createRigidArea(new Dimension(0, 10)));
        var0.add(new JSeparator(0));
        var0.add(Box.createRigidArea(new Dimension(0, 10)));
        var0.add(new JLabel("Точность"));
        var0.add(Box.createRigidArea(new Dimension(0, 5)));
        final JTextField var2 = new JTextField("0.0001");
        var0.add(var2);
        var0.add(Box.createRigidArea(new Dimension(0, 10)));
        JLabel var3 = new JLabel("Количество сегментов:");
        final JTextField var4 = new JTextField("15");
        var0.add(var3);
        var0.add(Box.createRigidArea(new Dimension(0, 5)));
        var0.add(var4);
        var0.add(Box.createRigidArea(new Dimension(0, 10)));
        JPanel var5 = new JPanel();
        var5.setLayout(new BoxLayout(var5, 0));
        final JTextField var6 = new JTextField("-2");
        final JTextField var7 = new JTextField("2");
        var5.add(var6);
        var5.add(var7);
        var0.add(new JLabel("Границы отрезка:"));
        var0.add(Box.createRigidArea(new Dimension(0, 5)));
        var0.add(var5);
        var0.add(Box.createRigidArea(new Dimension(0, 10)));
        var0.add(new JLabel("Количество точек интерполянты на сегмент:"));
        var0.add(Box.createRigidArea(new Dimension(0, 5)));
        final JTextField var8 = new JTextField("5");
        var0.add(var8);
        JButton var9 = new JButton("Выполнить");
        var9.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent var1) {
                try {
                    data = new Data();
                    data.setEpsilon(Double.parseDouble(var2.getText()));
                    data.setSegmentsCount(Integer.parseInt(var4.getText()));
                    data.setStart(Double.parseDouble(var6.getText()));
                    data.setEnd(Double.parseDouble(var7.getText()));
                    data.setInterpolatePoints(Integer.parseInt(var8.getText()));
                    Main.settings.dispose();
                    createMainWindow();
                } catch (NumberFormatException var3) {
                    var2.setText("");
                    System.out.println("Неправильный формат чисел");
                }

            }
        });
        var0.add(Box.createRigidArea(new Dimension(0, 10)));
        var0.add(new JSeparator(0));
        var0.add(Box.createRigidArea(new Dimension(0, 10)));
        var0.add(var9);
        settings.getContentPane().add(var0);
        settings.setVisible(true);
    }

    private static void createMainWindow() {
        // Create main window
        window = new JFrame("Vinter");
        window.setDefaultCloseOperation(3);
        window.setSize(800,600);
        window.setResizable(false);

        Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
        window.setLocation(dim.width/2-window.getSize().width/2, dim.height/2-window.getSize().height/2);

        JPanel tablesPanel = new JPanel();
        tablesPanel.setMinimumSize(new Dimension(400,600));
        tablesPanel.setLayout(new BoxLayout(tablesPanel, BoxLayout.Y_AXIS));
        tablesPanel.setBorder(new EmptyBorder(10, 10, 10, 10));

        JPanel renderPanel = new JPanel();
        //renderPanel.setMinimumSize(new Dimension(400,600));
        renderPanel.setLayout(new BoxLayout(renderPanel, BoxLayout.Y_AXIS));
        renderPanel.setBorder(new EmptyBorder(10, 10, 10, 10));
        // renderPanel.setBackground(Color.BLUE);

        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.X_AXIS));
        mainPanel.add(tablesPanel);
        mainPanel.add(new JSeparator(1));
        mainPanel.add(renderPanel);
        mainPanel.add(Box.createRigidArea(new Dimension(400,1)));


        // Calculations
        Timer timer = new Timer();
        timer.start();

        Function function = new DefaultFunction();
        function.calculate(data);
        Interpolate interpolate = new NewtonInterpolate();
        interpolate.calculate(data);

        timer.end();

        // Get values of dif table and create table data
        DifTableModel difTableModel = new DifTableModel(data);
        // Create table of dif table
        tablesPanel.add(new JLabel("Таблица разделённых разностей:"));
        tablesPanel.add(Box.createRigidArea(new Dimension(0, 5)));
        JTable diftable = new JTable(difTableModel);
        diftable.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
        diftable.setPreferredScrollableViewportSize(new Dimension(400,100));
        diftable.setFillsViewportHeight(true);
        tablesPanel.add(new JScrollPane(diftable, JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED, JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED));

        tablesPanel.add(Box.createRigidArea(new Dimension(0, 10)));

        // Get values of function and create table data
        FunctionTableModel tableModel = new FunctionTableModel(data);
        // Create table of function values
        tablesPanel.add(new JLabel("Значения функции:"));
        tablesPanel.add(Box.createRigidArea(new Dimension(0, 5)));
        JTable table = new JTable(tableModel);
        table.setPreferredScrollableViewportSize(new Dimension(400,100));
        table.setFillsViewportHeight(true);
        tablesPanel.add(new JScrollPane(table));

        // Print information about time
        tablesPanel.add(Box.createRigidArea(new Dimension(0, 10)));
        tablesPanel.add(new JLabel("Время вычислений: ~" + timer.getTime() + " мс."));

        tablesPanel.add(Box.createRigidArea(new Dimension(0, 10)));
        tablesPanel.add(new JButton("Добавить новый узел"));

        //renderPanel.add(new JLabel("<html>Конечный график</html>"));
        //renderPanel.add(Box.createRigidArea(new Dimension(0, 10)));

        Graphic graphic = new Graphic(data);
        //JPanel panel = new JPanel();
        //panel.setBackground(Color.BLACK);
        graphic.setMaximumSize(new Dimension(380,380));
        graphic.setMinimumSize(new Dimension(380,380));
        //panel.setSize(300,300);
        renderPanel.add(graphic);

        window.getContentPane().add(mainPanel);
        window.setVisible(true);
    }
}
