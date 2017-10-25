package net.djuke.vadim.data;

import javax.swing.event.TableModelListener;
import javax.swing.table.TableModel;
import java.util.List;

public class DifTableModel implements TableModel {

    private List<List<Double>> columns;

    public DifTableModel(Data data) {
        columns = data.getDifTable().getColumns();
        System.out.println(getRowCount());
    }

    @Override
    public int getRowCount() {
        if (columns.size() != 0) {
            return columns.get(0).size();
        } else return 0;
    }

    @Override
    public int getColumnCount() {
        return columns.size();
    }

    @Override
    public String getColumnName(int columnIndex) {
        return String.valueOf(columnIndex);
    }

    @Override
    public Class<?> getColumnClass(int columnIndex) {
        return String.class;
    }

    @Override
    public boolean isCellEditable(int rowIndex, int columnIndex) {
        return false;
    }

    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        if (rowIndex > columns.get(columnIndex).size() - 1) return null;
        else return columns.get(columnIndex).get(rowIndex);
    }

    @Override
    public void setValueAt(Object aValue, int rowIndex, int columnIndex) {
        // just
    }

    @Override
    public void addTableModelListener(TableModelListener l) {

    }

    @Override
    public void removeTableModelListener(TableModelListener l) {

    }
}
