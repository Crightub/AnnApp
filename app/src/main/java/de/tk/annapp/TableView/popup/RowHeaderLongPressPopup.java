package de.tk.annapp.TableView.popup;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.PopupMenu;

import com.evrencoskun.tableview.ITableView;

import de.tk.annapp.R;


public class RowHeaderLongPressPopup extends PopupMenu implements PopupMenu
        .OnMenuItemClickListener {

    // Menu Item constants
    private static final int SCROLL_COLUMN = 1;
    private static final int SHOWHIDE_COLUMN = 2;

    private ITableView mTableView;
    private Context mContext;

    public RowHeaderLongPressPopup(RecyclerView.ViewHolder viewHolder, ITableView tableView) {
        super(viewHolder.itemView.getContext(), viewHolder.itemView);

        this.mTableView = tableView;
        this.mContext = viewHolder.itemView.getContext();

        initialize();
    }

    private void initialize() {
        createMenuItem();

        this.setOnMenuItemClickListener(this);
    }

    private void createMenuItem() {
        /*
        this.getMenu().add(Menu.NONE, SCROLL_COLUMN, 0, mContext.getString(R.string
                .scroll_to_column_position));
        this.getMenu().add(Menu.NONE, SHOWHIDE_COLUMN, 1, mContext.getString(R.string
                .show_hide_the_column));
        // add new one ...
        */

    }


    @Override
    public boolean onMenuItemClick(MenuItem menuItem) {
        // Note: item id is index of menu item..

        switch (menuItem.getItemId()) {
            case SCROLL_COLUMN:
                mTableView.scrollToColumnPosition(15);
                break;
            case SHOWHIDE_COLUMN:
                int column = 1;
                if (mTableView.isColumnVisible(column)) {
                    mTableView.hideColumn(column);
                } else {
                    mTableView.showColumn(column);
                }

                break;
        }
        return true;
    }

}
