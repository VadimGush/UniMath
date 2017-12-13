package net.djuke.vadim.graphics;

import net.djuke.vadim.data.Data;

import javax.swing.*;
import java.awt.*;

/**
 * Graphic - выполняет отрисовку графика функции
 */
public class Graphic extends JPanel {

    private Data data;

    private double minArgument = Double.MAX_VALUE;
    private double maxArgument = Double.MIN_VALUE;
    private double oneArgument = 0;

    /**
     * Создаём новый график по готовым данным
     * @param data данные содержащие в себе значения функции и значения интерполянты
     */
    public Graphic(Data data) {
        setBackground(Color.BLACK);
        this.data = data;

        // Находим минимальные и максимальные аргументы функции
        for (double argument : data.getFunction().getArgumentsList()) {
            if (argument < minArgument) minArgument = argument;
            if (argument > maxArgument) maxArgument = argument;
        }
        oneArgument = 380f / (maxArgument - minArgument);
    }

    /**
     * Отрисовка графика
     * @param g
     */
    public void paint(Graphics g) {
        super.paint(g);

        g.setColor(Color.darkGray);
        g.drawLine(0,190,380,190);

        g.setColor(Color.darkGray);
        g.drawLine(190,0,190,380);

        /*
         * Я заметил что график вышел инвертированным по оси X
         * и поэтому соотвтственно инвертировал все точки
         *
         * Но по всей видимости где-то ранее я допустил небольшую ошибку в расчётах
         * из за которой сменился знак
         */
        // Отрисовка графика интерполянты
        for (double argument : data.getInterpolate().getArgumentsList()) {
            // На оси X точки, в которых интерполянта определенна
            int x = (int)((argument - minArgument) * oneArgument);
            g.setColor(Color.darkGray);
            g.drawLine(380 - x,190 + 2, 380 - x,190 - 2);

            // И соотвественно точки самого графика интерполянты
            g.setColor(Color.MAGENTA);
            g.fillOval(380 - x, 190 + (int)(data.getInterpolate().getValue(argument) * 200 * 0.75f),2,2);
        }


        /*
         * Рисуем график функции
         */
        for (double argument : data.getFunction().getArgumentsList()) {
            int x = (int)((argument - minArgument) * oneArgument);
            g.setColor(Color.darkGray);
            g.drawLine(380 - x,190 + 5,380 - x,190 - 5);

            g.setColor(Color.CYAN);
            g.fillOval(380 - x, 190 + (int)(data.getFunction().getValue(argument) * 200 * 0.75f),4,4);
        }

        g.setColor(Color.darkGray);
        g.drawString(String.valueOf(minArgument), 5, 210);
        g.drawString(String.valueOf(maxArgument), 355, 210);
    }

}
