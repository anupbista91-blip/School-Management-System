/**
 * File: EntityTableModel.java
 * Purpose: Generic table model to display lists of entities in Swing tables.
 */
package School_Management_System.Widgets;

import javax.swing.table.AbstractTableModel;
import java.util.*;
import java.util.function.Function;

/**
 * Generic table model that maps each entity to an Object[] row using a mapper function.
 * @param <T> entity type
 */
public class EntityTableModel<T> extends AbstractTableModel {
    private List<T> items = new ArrayList<>();
    private final String[] columnNames;
    private final Function<T, Object[]> mapper;

    /**
     * Constructs a model.
     * @param columnNames column headers
     * @param mapper maps entity to row objects
     */
    public EntityTableModel(String[] columnNames, Function<T, Object[]> mapper) {
        this.columnNames = columnNames;
        this.mapper = mapper;
    }

    /** Sets items and refreshes table. */
    public void setItems(Collection<T> items) {
        this.items = new ArrayList<>(items);
        fireTableDataChanged();
    }

    @Override
    public int getRowCount() { return items.size(); }

    @Override
    public int getColumnCount() { return columnNames.length; }

    @Override
    public String getColumnName(int col) { return columnNames[col]; }

    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        T item = items.get(rowIndex);
        Object[] row = mapper.apply(item);
        return row[columnIndex];
    }
}
