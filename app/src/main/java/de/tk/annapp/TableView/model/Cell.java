/*
 * Copyright (c) 2018. Evren Coşkun
 *
 *  Licensed under the Apache License, Version 2.0 (the "License");
 *  you may not use this file except in compliance with the License.
 *  You may obtain a copy of the License at
 *
 *        http://www.apache.org/licenses/LICENSE-2.0
 *
 *  Unless required by applicable law or agreed to in writing, software
 *  distributed under the License is distributed on an "AS IS" BASIS,
 *  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  See the License for the specific language governing permissions and
 *  limitations under the License.
 *
 */

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

