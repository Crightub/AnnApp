
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

    public final LinearLayout column_header_container;
    public final TextView column_header_textview;
    public final ITableView tableView;


    public ColumnHeaderViewHolder(View itemView, ITableView tableView) {
        super(itemView);
        this.tableView = tableView;
        column_header_textview = (TextView) itemView.findViewById(R.id.column_header_textView);
        column_header_container = (LinearLayout) itemView.findViewById(R.id
                .column_header_container);

    }

    public void setColumnHeader(ColumnHeader columnHeader) {
        column_header_textview.setText(String.valueOf(columnHeader.getData()));
        column_header_container.getLayoutParams().width = LinearLayout.LayoutParams.WRAP_CONTENT;

        column_header_textview.requestLayout();
        column_header_container.requestLayout();
        itemView.requestLayout();
    }


}
