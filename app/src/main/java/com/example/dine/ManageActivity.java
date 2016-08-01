package com.example.dine;

import android.app.Activity;
import android.content.ContentValues;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintWriter;

/**
 * Created by junze on 16-07-27.
 */
public class ManageActivity extends Activity {

    private EditText mEditText;
    private Button mButton;
    private DBHelper mDbHelper;
    private int counter = 0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_manage);
        mButton = (Button) findViewById(R.id.bt);
        mEditText = (EditText) findViewById(R.id.et);
        mDbHelper = new DBHelper(this);
        mButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String text = mEditText.getText().toString();
                mEditText.setText("");
                Toast.makeText(ManageActivity.this,text + " added",Toast.LENGTH_SHORT).show();
                SQLiteDatabase db = mDbHelper.getWritableDatabase();
                ContentValues values = new ContentValues();
                values.put(Contract.Entry.COLUMN_NAME_RESTAURANT, text);
                db.insert(Contract.Entry.TABLE_NAME,null,values);
            }
        });
    }
}
