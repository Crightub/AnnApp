package de.tk.annapp.TableView.model;

import com.evrencoskun.tableview.sort.ISortableModel;


public class Cell implements ISortableModel {

    private String mId;
    private Object mData;

    public Cell(String id) {
        this.mId = id;
    }

    public Cell(String id, Object data) {
        this.mId = id;
        this.mData = data;
    }

    public String getId() {
        return mId;
    }

    @Override
    public Object getContent() {
        return mData;
    }


    public Object getData() {
        return mData;
    }

    public void setData(String data) {
        mData = data;
    }
}

