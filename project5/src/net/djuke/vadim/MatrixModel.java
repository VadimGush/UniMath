package net.djuke.vadim;

import javax.swing.event.TableModelListener;
import javax.swing.table.AbstractTableModel;
import javax.swing.table.TableModel;

public class MatrixModel extends AbstractTableModel {

    private Matrix matrix;

    public MatrixModel(Matrix matrix) {
        this.matrix = matrix;
    }

    @Override
    public int getRowCount() {
        return matrix.getSize();
    }

    @Override
    public int getColumnCount() {
        return matrix.getSize()+1;
    }

    @Override
    public String getColumnName(int i) {
        if (i < matrix.getSize()) return "a";
        else return "b";
    }

    @Override
    public Class<?> getColumnClass(int i) {
        return Double.class;
    }

    @Override
    public boolean isCellEditable(int i, int i1) {
        return false;
    }

    @Override
    public Object getValueAt(int row, int column) {
        if (column < matrix.getSize()) {
            return matrix.getMatrix()[row][column];
        } else {
            return matrix.getVector()[row];
        }
    }

    @Override
    public void setValueAt(Object o, int i, int i1) {

    }

    @Override
    public void addTableModelListener(TableModelListener tableModelListener) {

    }

    @Override
    public void removeTableModelListener(TableModelListener tableModelListener) {

    }
}
