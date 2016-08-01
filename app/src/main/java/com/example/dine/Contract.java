package com.example.dine;

import android.provider.BaseColumns;

/**
 * Created by junze on 16-08-01.
 */
public final class Contract {
    private Contract() {}
    public static abstract class Entry implements BaseColumns {
        public static final String TABLE_NAME = "restaurants";
        public static final String COLUMN_NAME_RESTAURANT = "restaurant";
    }
}
