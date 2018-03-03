package de.tk.annapp.TableView.holder;

import android.graphics.drawable.Drawable;
import android.support.v4.content.ContextCompat;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.evrencoskun.tableview.ITableView;
import com.evrencoskun.tableview.adapter.recyclerview.holder.AbstractSorterViewHolder;
import com.evrencoskun.tableview.sort.SortState;

import de.tk.annapp.R;
import de.tk.annapp.TableView.model.ColumnHeader;

public class ColumnHeaderViewHolder extends AbstractSorterViewHolder {

    private static final String LOG_TAG = ColumnHeaderViewHolder.class.getSimpleName();

    public final LinearLayout column_header_container;
    public final TextView column_header_textview;
    public final ImageButton column_header_sortButton;
    public final ITableView tableView;

    public ColumnHeaderViewHolder(View itemView, ITableView tableView) {
        super(itemView);
        this.tableView = tableView;
        column_header_textview = (TextView) itemView.findViewById(R.id.column_header_textView);
        column_header_container = (LinearLayout) itemView.findViewById(R.id
                .column_header_container);
        column_header_sortButton = (ImageButton) itemView.findViewById(R.id
                .column_header_sortButton);

        // Set click listener to the sort button
        column_header_sortButton.setOnClickListener(mSortButtonClickListener);
    }


    /**
     * This method is calling from onBindColumnHeaderHolder on TableViewAdapter
     */
    public void setColumnHeader(ColumnHeader columnHeader) {
        column_header_textview.setText(String.valueOf(columnHeader.getData()));

        // If your TableView should have auto resize for cells & columns.
        // Then you should consider the below lines. Otherwise, you can ignore them.

        // It is necessary to remeasure itself.
        column_header_container.getLayoutParams().width = LinearLayout.LayoutParams.WRAP_CONTENT;
        column_header_sortButton.requestLayout();
        column_header_textview.requestLayout();
        itemView.requestLayout();
    }

    private View.OnClickListener mSortButtonClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            if (getSortState() == SortState.ASCENDING) {
                tableView.sortColumn(getAdapterPosition(), SortState.DESCENDING);
            } else if (getSortState() == SortState.DESCENDING) {
                tableView.sortColumn(getAdapterPosition(), SortState.ASCENDING);
            } else {
                // Default one
                tableView.sortColumn(getAdapterPosition(), SortState.DESCENDING);
            }

        }
    };

    @Override
    public void onSortingStatusChanged(SortState sortState) {
        Log.e(LOG_TAG, " + onSortingStatusChanged : x:  " + getAdapterPosition() + " old state "
                + getSortState() + " current state : " + sortState + " visiblity: " +
                column_header_sortButton.getVisibility());

        super.onSortingStatusChanged(sortState);

        // It is necessary to remeasure itself.
        column_header_container.getLayoutParams().width = LinearLayout.LayoutParams.WRAP_CONTENT;

        controlSortState(sortState);

        Log.e(LOG_TAG, " - onSortingStatusChanged : x:  " + getAdapterPosition() + " old state "
                + getSortState() + " current state : " + sortState + " visiblity: " +
                column_header_sortButton.getVisibility());

        column_header_textview.requestLayout();
        column_header_sortButton.requestLayout();
        column_header_container.requestLayout();
        itemView.requestLayout();
    }

    private void controlSortState(SortState sortState) {
        if (sortState == SortState.ASCENDING) {
            column_header_sortButton.setVisibility(View.VISIBLE);

        } else if (sortState == SortState.DESCENDING) {
            column_header_sortButton.setVisibility(View.VISIBLE);;
        } else {
            column_header_sortButton.setVisibility(View.GONE);
        }
    }
}
