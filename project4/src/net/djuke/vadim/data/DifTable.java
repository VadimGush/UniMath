package net.djuke.vadim.data;

import java.util.ArrayList;
import java.util.List;

public class DifTable {

    private List<List<Double>> columns = new ArrayList<>();

    public DifTable() {
    }

    public void init(int size) {
        for (int c = columns.size(); c < size; c++) {
            List<Double> list = new ArrayList<>();
            for (int i = 0; i < size-c; i++) {
                list.add(null);
            }
            columns.add(list);
        }
    }

    public void putValue(int columnIndex, int rowIndex, double value) {
        List<Double> column = columns.get(columnIndex);
        column.set(rowIndex, value);
    }

    public List<List<Double>> getColumns() {
        return columns;
    }
}
