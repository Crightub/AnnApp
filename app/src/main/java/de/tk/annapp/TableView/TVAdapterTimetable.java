package de.tk.annapp.TableView;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.evrencoskun.tableview.adapter.AbstractTableAdapter;
import com.evrencoskun.tableview.adapter.recyclerview.holder.AbstractViewHolder;

import de.tk.annapp.R;
import de.tk.annapp.TableView.holder.CellViewHolder;
import de.tk.annapp.TableView.holder.ColumnHeaderViewHolder;
import de.tk.annapp.TableView.holder.RowHeaderViewHolder;
import de.tk.annapp.TableView.model.Cell;
import de.tk.annapp.TableView.model.ColumnHeader;
import de.tk.annapp.TableView.model.RowHeader;

public class TVAdapterTimetable extends AbstractTableAdapter<ColumnHeader, RowHeader, Cell> {

    public TVAdapterTimetable(Context p_jContext) {
        super(p_jContext);
    }

    @Override
    public RecyclerView.ViewHolder onCreateCellViewHolder(ViewGroup parent, int viewType) {

        // Get Cell xml Layout
        View layout = LayoutInflater.from(m_jContext).inflate(R.layout.table_view_cell_layout, parent, false);

        // Create a Cell ViewHolder
        return new CellViewHolder(layout);
    }

    @Override
    public void onBindCellViewHolder(AbstractViewHolder holder, Object cellItemModel, int columnPosition, int rowPosition) {
        Cell cell = (Cell) cellItemModel;

        // Get the holder to update cell item text
        CellViewHolder viewHolder = (CellViewHolder) holder;
        viewHolder.cell_textview.setText(String.valueOf(cell.getData()));

        // If your TableView should have auto resize for cells & columns.
        // Then you should consider the below lines. Otherwise, you can ignore them.

        // It is necessary to remeasure itself.
        viewHolder.cell_container.getLayoutParams().width = LinearLayout.LayoutParams.WRAP_CONTENT;
        viewHolder.cell_textview.requestLayout();
    }

    @Override
    public RecyclerView.ViewHolder onCreateColumnHeaderViewHolder(ViewGroup parent, int viewType) {

        // Get Column Header xml Layout
        View layout = LayoutInflater.from(m_jContext).inflate(R.layout.table_view_column_header_layout, parent, false);

        // Create a ColumnHeader ViewHolder
        return new ColumnHeaderViewHolder(layout, getTableView());
    }

    @Override
    public void onBindColumnHeaderViewHolder(AbstractViewHolder holder, Object columnHeaderItemModel, int columnPosition) {
        ColumnHeader columnHeader = (ColumnHeader) columnHeaderItemModel;

        // Get the holder to update cell item text
        ColumnHeaderViewHolder columnHeaderViewHolder = (ColumnHeaderViewHolder) holder;
        columnHeaderViewHolder.setColumnHeader(columnHeader);
    }

    @Override
    public RecyclerView.ViewHolder onCreateRowHeaderViewHolder(ViewGroup parent, int viewType) {

        // Get Row Header xml Layout
        View layout = LayoutInflater.from(m_jContext).inflate(R.layout.table_view_row_header_layout, parent, false);

        // Create a Row Header ViewHolder
        return new RowHeaderViewHolder(layout);
    }

    @Override
    public void onBindRowHeaderViewHolder(AbstractViewHolder holder, Object rowHeaderItemModel, int rowPosition) {
        RowHeader rowHeader = (RowHeader) rowHeaderItemModel;

        // Get the holder to update row header item text
        RowHeaderViewHolder rowHeaderViewHolder = (RowHeaderViewHolder) holder;
        rowHeaderViewHolder.row_header_textview.setText(String.valueOf(rowHeader.getData()));
    }


    @Override
    public View onCreateCornerView() {
        // Get Corner xml layout
        return LayoutInflater.from(m_jContext).inflate(R.layout.table_view_corner_layout, null);
    }

    @Override
    public int getColumnHeaderItemViewType(int position) {
        return 0;
    }

    @Override
    public int getRowHeaderItemViewType(int position) {
        return 0;
    }

    @Override
    public int getCellItemViewType(int position) {
        return 0;
    }
}




