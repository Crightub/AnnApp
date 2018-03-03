package de.tk.annapp.TableView;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.widget.Toast;

import com.evrencoskun.tableview.TableView;
import com.evrencoskun.tableview.listener.ITableViewListener;

import de.tk.annapp.Lesson;
import de.tk.annapp.TableView.holder.ColumnHeaderViewHolder;
import de.tk.annapp.TableView.popup.ColumnHeaderLongPressPopup;
import de.tk.annapp.TableView.popup.RowHeaderLongPressPopup;
import de.tk.annapp.TimetableManager;

public class MyTableViewListener implements ITableViewListener {

    private TimetableManager timetableManager = TimetableManager.getInstance();


    private Toast mToast;
    private Context mContext;
    private TableView mTableView;

    public MyTableViewListener(TableView tableView) {
        this.mContext = tableView.getContext();
        this.mTableView = tableView;
    }

    /**
     * Called when user click any cell item.
     *
     * @param cellView : Clicked Cell ViewHolder.
     * @param column   : X (Column) position of Clicked Cell item.
     * @param row      : Y (Row) position of Clicked Cell item.
     */
    @Override
    public void onCellClicked(@NonNull RecyclerView.ViewHolder cellView, int column, int row) {
        // Do what you want.
        showToast("Cell " + column + " " + row + " has been clicked.");
    }

    @Override
    public void onCellLongPressed(@NonNull RecyclerView.ViewHolder cellView, int column, int row) {

    }

    /**
     * Called when user click any column header item.
     *
     * @param columnHeaderView : Clicked Column Header ViewHolder.
     * @param column           : X (Column) position of Clicked Column Header item.
     */
    @Override
    public void onColumnHeaderClicked(@NonNull RecyclerView.ViewHolder columnHeaderView, int
            column) {
        // Do what you want.
        showToast("Column header  " + column + " has been clicked.");
    }

    @Override
    public void onColumnHeaderLongPressed(@NonNull RecyclerView.ViewHolder columnHeaderView, int
            column) {

        if (columnHeaderView != null && columnHeaderView instanceof ColumnHeaderViewHolder) {
            // Create Long Press Popup
            ColumnHeaderLongPressPopup popup = new ColumnHeaderLongPressPopup((ColumnHeaderViewHolder) columnHeaderView, mTableView);
            // Show
            popup.show();
        }
    }

    /**
     * Called when user click any Row Header item.
     *
     * @param rowHeaderView : Clicked Row Header ViewHolder.
     * @param row           : Y (Row) position of Clicked Row Header item.
     */
    @Override
    public void onRowHeaderClicked(@NonNull RecyclerView.ViewHolder rowHeaderView, int row) {
        // Do what you want.


        showToast("Row header " + row + " has been clicked.");
    }

    @Override
    public void onRowHeaderLongPressed(@NonNull RecyclerView.ViewHolder rowHeaderView, int row) {

        if (rowHeaderView != null) {
            // Create Long Press Popup
            RowHeaderLongPressPopup popup = new RowHeaderLongPressPopup(rowHeaderView, mTableView);
            // Show
            popup.show();
        }
    }


    private void showToast(String p_strMessage) {
        if (mToast == null) {
            mToast = Toast.makeText(mContext, "", Toast.LENGTH_SHORT);
        }

        mToast.setText(p_strMessage);
        mToast.show();
    }
}
