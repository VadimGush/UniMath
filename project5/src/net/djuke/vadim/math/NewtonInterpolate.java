package net.djuke.vadim.math;

import net.djuke.vadim.data.Data;
import net.djuke.vadim.data.DifTable;
import net.djuke.vadim.data.FunctionData;

import java.util.List;

public class NewtonInterpolate implements Interpolate {

    private FunctionData function;
    private List<Double> arguments;
    private DifTable table;
    private Data data;

    @Override
    public void calculate(Data data) {
        this.data = data;
        // Получаем значения функции из данных
        function = data.getFunction();
        // Отдельно достаём аргументы функции
        arguments = data.getFunction().getArgumentsList();
        table = data.getDifTable(); // получаем пустую таблицу разделённых разностей
        // Создаём таблицу для хранения разделённых разностей
        table.init(function.getValuesList().size());
        // Берём разделённую разность самого высшего порядка, чтобы она запустила рекурсивные вычесления
        function(0,function.getValuesList().size() - 1);

        // После того как мы посчитали таблицу разделённых разностей, нам необходимо построить саму
        // интерполянту
        interpolate();
    }

    private void interpolate() {
        // Считаем количество точек интерполянты
        int count = (data.getSegmentsCount()+1) + (data.getSegmentsCount() * data.getInterpolatePoints());
        for (int i = 0; i<data.getSegmentsCount(); i++) {
            // расчитываем расстояние между точками
            double delta = (function.getArgumentsList().get(i+1) - function.getArgumentsList().get(i)) / (data.getInterpolatePoints() + 1);
            // записываем аргумент функции и значение функции
            data.getInterpolate().putValue(function.getArgumentsList().get(i), function.getValuesList().get(i));
            // Далее расчитываем саму интерполянту
            for (int c=1; c<=data.getInterpolatePoints(); c++) {
                double step = delta * c;
                // Записываем значение интерполянты в данной точке
                double value = newtonInterpolation(function.getArgumentsList().get(i) + step);
                // Записываем значение
                data.getInterpolate().putValue(function.getArgumentsList().get(i) + step, value);
            }
        }
        // Запись последнего элемента
        data.getInterpolate().putValue(function.getArgumentsList().get(data.getSegmentsCount()), function.getValuesList().get(data.getSegmentsCount()));
    }

    private double newtonInterpolation(double x) {
        double result = 0;
        for (int i = 0; i < function.getArgumentsList().size(); i++) {
            double value1 = data.getDifTable().getColumns().get(i).get(0);
            double value2 = 1;
            for (int c = 0; c < i; c++) {
                value2 *= (x-function.getArgumentsList().get(c));
            }
            result += value1 * value2;
        }
        return result;
    }

    private double linearInterpolation(double x, double xk, double xk1, double fk, double fk1) {
        return ((x - xk) / (xk1 - xk)) * (fk1 - fk) + fk;
    }

    private double function(int s, int e) {
        // Если разделённой разности нет в табличке, то мы просто считаем её
        if (table.getColumns().get(e - s).get(s) == null) {
            if (s != e) {
                // Если разделённая разность не первого порядка, то уходим в рекурсию
                double value1 = function(s + 1, e);
                double value2 = function(s, e - 1);
                int level = e - s;
                double result = (value1 - value2) / (arguments.get(e) - arguments.get(s));
                table.putValue(level, s, result);
                return result;
            } else {
                // Если это разделённая разность первого порядка, то возвращаем значение функции
                double result = function.getValue(arguments.get(s));
                table.putValue(0, s, result);
                return result;
            }
        } else {
            // Если она там есть, то достаём значение из таблицы
            // (это сокращает время вычеслений как минимум в 15 раз!)
            return table.getColumns().get(e-s).get(s);
        }
    }
}
