package net.djuke.vadim.graphics;

import net.djuke.vadim.data.Data;

import javax.swing.*;
import java.awt.*;

public class Graphic extends JPanel {

    private Data data;

    private double minArgument = Double.MAX_VALUE;
    private double maxArgument = Double.MIN_VALUE;
    private double oneArgument = 0;

    public Graphic(Data data) {
        setBackground(Color.BLACK);
        this.data = data;

        // Находим минимальные и максимальные аргументы функции
        for (double argument : data.getFunction().getArgumentsList()) {
            if (argument < minArgument) minArgument = argument;
            if (argument > maxArgument) maxArgument = argument;
        }
        oneArgument = 380f / (maxArgument - minArgument);
        System.out.println(oneArgument);
    }

    public void paint(Graphics g) {
        super.paint(g);

        g.setColor(Color.darkGray);
        g.drawLine(0,190,380,190);

        g.setColor(Color.darkGray);
        g.drawLine(190,0,190,380);

        // Рисуем значения функции
        for (double argument : data.getFunction().getArgumentsList()) {
            int x = (int)((argument - minArgument) * oneArgument);
            System.out.println(x);
            g.setColor(Color.darkGray);
            g.drawLine(x,190 + 10,x,190 - 10);

            g.setColor(Color.CYAN);
            g.fillOval(x, 190 + (int)(data.getFunction().getValue(argument) * 200),4,4);
        }
    }

    private void drawLine(Graphics g, int x1, int y1, int x2, int y2) {
        g.drawLine(x1,380-y1,x2,380-y2);
    }
}
