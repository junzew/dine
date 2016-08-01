package com.example.dine;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.Scanner;

public class MainActivity extends AppCompatActivity {

    private TextView mTextView;
    private Button mButton;
    private Button manageButton;
    private List<String> restaurants;
    private DBHelper mDBHelper;
    private ListView mListView;
    private ArrayAdapter<String> mAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        restaurants = new ArrayList<>();
        mAdapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, restaurants);

        mDBHelper = new DBHelper(this);
        read();
        findViewsById();
        mButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int index = (int)(Math.random() * ((restaurants.size()) + 1));
                if (index >= 0 && index < restaurants.size()) {
                    mTextView.setText(restaurants.get(index));
                }
            }
        });
        manageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, ManageActivity.class);
                startActivityForResult(intent, 0);
            }
        });
        mListView.setAdapter(mAdapter);
        mListView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                SQLiteDatabase db = mDBHelper.getReadableDatabase();
                // Define 'where' part of query.
                String selection = Contract.Entry.COLUMN_NAME_RESTAURANT + " LIKE ?";
                // Specify arguments in placeholder order.
                String[] selectionArgs = { String.valueOf(restaurants.get(position)) };
                // Issue SQL statement.
                db.delete(Contract.Entry.TABLE_NAME, selection, selectionArgs);
                read();
                mAdapter.notifyDataSetChanged();
                return false;
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        read();
        mAdapter.notifyDataSetChanged();
    }

    private void findViewsById() {
        mButton = (Button) findViewById(R.id.bt);
        manageButton = (Button) findViewById(R.id.bt_manage);
        mTextView = (TextView) findViewById(R.id.tv);
        mListView = (ListView) findViewById(R.id.lv);
    }

    private void read() {
        restaurants.clear();
        SQLiteDatabase db = mDBHelper.getReadableDatabase();
        String[] projection = {
                Contract.Entry.COLUMN_NAME_RESTAURANT
        };
        String sortOrder =
                Contract.Entry.COLUMN_NAME_RESTAURANT + " DESC";
        Cursor c = db.query(
                Contract.Entry.TABLE_NAME,  // The table to query
                projection,                               // The columns to return
                null,                                // The columns for the WHERE clause
                null,                            // The values for the WHERE clause
                null,                                     // don't group the rows
                null,                                     // don't filter by row groups
                sortOrder                                 // The sort order
        );
        while(c.moveToNext()) {
            String name = c.getString(c.getColumnIndexOrThrow(Contract.Entry.COLUMN_NAME_RESTAURANT));
            restaurants.add(name);
        }
    }
}
