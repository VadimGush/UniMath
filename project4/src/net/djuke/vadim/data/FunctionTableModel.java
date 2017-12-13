package net.djuke.vadim.data;

import javax.swing.event.TableModelListener;
import javax.swing.table.TableModel;

public class FunctionTableModel implements TableModel {

    private String[] columns = {"Аргумент", "Значение"};
    private FunctionData function;

    public FunctionTableModel(Data data) {
        function = data.getFunction();
    }

    @Override
    public int getRowCount() {
        return function.getArgumentsList().size();
    }

    @Override
    public int getColumnCount() {
        return columns.length;
    }

    @Override
    public String getColumnName(int columnIndex) {
        return columns[columnIndex];
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
        return columnIndex == 0 ? function.getArgumentsList().get(rowIndex) : function.getValuesList().get(rowIndex);
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
