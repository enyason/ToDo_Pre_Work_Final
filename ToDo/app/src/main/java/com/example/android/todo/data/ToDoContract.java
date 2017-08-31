package com.example.android.todo.data;

import android.provider.BaseColumns;

/**
 * Created by Enyason on 7/29/2017.
 */

public class ToDoContract {

    public ToDoContract() {
    }

    public static final class ToDoEntry implements BaseColumns {

        public final static String TABLE_NAME = "to_do";
        public final static String _ID = BaseColumns._ID;
        public final static String COLUMN_item = "item";
        public static final String COLUMN_LEVEL = "level";

        public static final String COLUMN_DES = "des";
        public static final String COLUMN_DATE = "date";
        public static final String COLUMN_TIME = "time";


    }
}
