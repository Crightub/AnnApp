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

    public TVAdapterTimetable(Context context) {
        super(context);

    }

    @Override
    public RecyclerView.ViewHolder onCreateCellViewHolder(ViewGroup parent, int viewType) {
        // Get cell xml layout
        View layout = LayoutInflater.from(mContext).inflate(R.layout.table_view_cell_layout,
                parent, false);

        System.out.println("Create Cell View Holder...");

        // Create a Custom ViewHolder for a Cell item.
        return new CellViewHolder(layout);
    }

    @Override
    public void onBindCellViewHolder(AbstractViewHolder holder, Object cellItemModel, int
            columnPosition, int rowPosition) {
        Cell cell = (Cell) cellItemModel;

        System.out.println("Set Attributes for the Cell");

        // Get the holder to update cell item text
        CellViewHolder viewHolder = (CellViewHolder) holder;
        viewHolder.cell_textview.setText(cell.getData().toString());

        // If your TableView should have auto resize for cells & columns.
        // Then you should consider the below lines. Otherwise, you can ignore them.

        // It is necessary to remeasure itself.
        viewHolder.itemView.getLayoutParams().width = LinearLayout.LayoutParams.WRAP_CONTENT;
        viewHolder.cell_textview.requestLayout();
    }

    @Override
    public RecyclerView.ViewHolder onCreateColumnHeaderViewHolder(ViewGroup parent, int viewType) {

        // Get Column Header xml Layout
        View layout = LayoutInflater.from(mContext).inflate(R.layout
                .table_view_column_header_layout, parent, false);

        // Create a ColumnHeader ViewHolder
        return new ColumnHeaderViewHolder(layout, getTableView());
    }

    @Override
    public void onBindColumnHeaderViewHolder(AbstractViewHolder holder, Object columnHeaderItemModel, int
            position) {
        ColumnHeader columnHeader = (ColumnHeader) columnHeaderItemModel;

        // Get the holder to update cell item text
        ColumnHeaderViewHolder columnHeaderViewHolder = (ColumnHeaderViewHolder) holder;
        columnHeaderViewHolder.column_header_textview.setText(columnHeader.getData().toString());

        // If your TableView should have auto resize for cells & columns.
        // Then you should consider the below lines. Otherwise, you can ignore them.

        // It is necessary to remeasure itself.
        columnHeaderViewHolder.column_header_container.getLayoutParams().width = LinearLayout
                .LayoutParams.WRAP_CONTENT;
        columnHeaderViewHolder.column_header_textview.requestLayout();
    }

    @Override
    public RecyclerView.ViewHolder onCreateRowHeaderViewHolder(ViewGroup parent, int viewType) {

        // Get Row Header xml Layout
        View layout = LayoutInflater.from(mContext).inflate(R.layout
                .table_view_row_header_layout, parent, false);

        // Create a Row Header ViewHolder
        return new RowHeaderViewHolder(layout);
    }

    @Override
    public void onBindRowHeaderViewHolder(AbstractViewHolder holder, Object rowHeaderItemModel, int
            position) {
        RowHeader rowHeader = (RowHeader) rowHeaderItemModel;

        // Get the holder to update row header item text
        RowHeaderViewHolder rowHeaderViewHolder = (RowHeaderViewHolder) holder;
        rowHeaderViewHolder.row_header_textview.setText(rowHeader.getData().toString());
    }


    @Override
    public View onCreateCornerView() {
        // Get Corner xml layout
        return LayoutInflater.from(mContext).inflate(R.layout.table_view_corner_layout, null);
    }

    @Override
    public int getColumnHeaderItemViewType(int columnPosition) {
        // The unique ID for this type of column header item
        // If you have different items for Cell View by X (Column) position,
        // then you should fill this method to be able create different
        // type of CellViewHolder on "onCreateCellViewHolder"
        return 0;
    }

    @Override
    public int getRowHeaderItemViewType(int rowPosition) {
        // The unique ID for this type of row header item
        // If you have different items for Row Header View by Y (Row) position,
        // then you should fill this method to be able create different
        // type of RowHeaderViewHolder on "onCreateRowHeaderViewHolder"
        return 0;
    }

    @Override
    public int getCellItemViewType(int columnPosition) {
        // The unique ID for this type of cell item
        // If you have different items for Cell View by X (Column) position,
        // then you should fill this method to be able create different
        // type of CellViewHolder on "onCreateCellViewHolder"
        return 0;
    }
}
