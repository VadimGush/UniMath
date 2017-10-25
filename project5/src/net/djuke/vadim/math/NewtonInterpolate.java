package net.djuke.vadim.math;

import net.djuke.vadim.data.Data;
import net.djuke.vadim.data.DifTable;
import net.djuke.vadim.data.FunctionData;

import java.util.List;

public class NewtonInterpolate implements Interpolate {

    private FunctionData function;
    private List<Double> arguments;
    private DifTable table;

    @Override
    public void calculate(Data data) {
        // Получаем значения функции из данных
        function = data.getFunction();
        // Отдельно достаём аргументы функции
        arguments = data.getFunction().getArgumentsList();
        table = data.getDifTable(); // получаем пустую таблицу разделённых разностей
        // Создаём таблицу для хранения разделённых разностей
        table.init(function.getValuesList().size());
        // Берём разделённую разность самого высшего порядка, чтобы она запустила рекурсивные вычесления
        function(0,function.getValuesList().size() - 1);
    }

    private double function(int s, int e) {
        // Если разделённой разности нет в табличке, то мы просто считаем её
        if (table.getColumns().get(e - s).get(s) == null) {
            if (s != e) {
                // Если разделённая разность не первого порядка, то уходим в рекурсию
                double value1 = function(s + 1, e);
                double value2 = function(s, e - 1);
                int level = e - s;
                double result = (value1 + value2) / (arguments.get(e) - arguments.get(s));
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
