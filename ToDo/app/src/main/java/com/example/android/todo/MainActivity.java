package com.example.android.todo;

import android.content.ContentValues;
import android.content.DialogInterface;
import android.content.res.Resources;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.android.todo.data.ToDoContract;
import com.example.android.todo.data.ToDoContract.ToDoEntry;
import com.example.android.todo.data.ToDoDbHelper;

public class MainActivity extends AppCompatActivity {

    EditText textItem;
    EditText textDes;
    EditText textDate;
    EditText textTime;

    String toDoItem;
    String toDoDes;
    String toDoDate;
    String toDoTime;
    String toDoLevel = "";

    ToDoDbHelper toDoDbHelper;

    Cursor cursor;
    ListView listView;
    ToDoCursorAdapter cursorAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolBar);
        setSupportActionBar(toolbar);

        toDoDbHelper = new ToDoDbHelper(this);


        listView = (ListView) findViewById(R.id.list_toDo);


        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                final AlertDialog.Builder alertDialog = new AlertDialog.Builder(MainActivity.this);
                alertDialog.setIcon(R.mipmap.ic_launcher);
                alertDialog.setTitle("Add Task");

                ViewGroup parent = (LinearLayout) findViewById(R.id.linearLayout);
                final View v = LayoutInflater.from(MainActivity.this).inflate(R.layout.alert_view, parent, false);
                Spinner spinnerLevel = (Spinner) v.findViewById(R.id.spinnerlevel);

                alertDialog.setView(v);

                Spinner spinner = (Spinner) v.findViewById(R.id.spinnerlevel);
// Create an ArrayAdapter using the string array and a default spinner layout
                ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(MainActivity.this,
                        R.array.level_array, android.R.layout.simple_spinner_item);

// Specify the layout to use when the list of choices appears
                adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
// Apply the adapter to the spinner
                spinner.setAdapter(adapter);
                spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                    @Override
                    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {


                        toDoLevel = String.valueOf(parent.getItemAtPosition(position));


                    }


                    @Override
                    public void onNothingSelected(AdapterView<?> parent) {

                    }
                });


                alertDialog.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
//
                        textItem = (EditText) v.findViewById(R.id.tv_title);
                        toDoItem = textItem.getText().toString().trim();

                        textDes = (EditText) v.findViewById(R.id.tv_des);
                        toDoDes = textDes.getText().toString().trim();


                        textDate = (EditText) v.findViewById(R.id.tv_date);
                        toDoDate = textDate.getText().toString().trim();

                        textTime = (EditText) v.findViewById(R.id.tv_time);
                        toDoTime = textTime.getText().toString().trim();



                        if (toDoItem.isEmpty()) {
                            Toast.makeText(MainActivity.this, "No Task Entered!", Toast.LENGTH_SHORT).show();
                        } else {
                            insert();
                            displayToDo();
                            Toast.makeText(MainActivity.this, "New Task Entered!", Toast.LENGTH_SHORT).show();
                        }


                    }
                });
                alertDialog.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {


                    }
                });
                alertDialog.show();


            }
        });


        //click item to edit Task
//
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {


                int idColumnIndex = cursor.getColumnIndex(ToDoEntry._ID);
                int itemColumnIndex = cursor.getColumnIndex(ToDoEntry.COLUMN_item);
                int levelColumnIndex = cursor.getColumnIndex(ToDoEntry.COLUMN_LEVEL);
                int desColumindex = cursor.getColumnIndex(ToDoEntry.COLUMN_DES);
                int dateColumindex = cursor.getColumnIndex(ToDoEntry.COLUMN_DATE);
                int timeColumindex = cursor.getColumnIndex(ToDoEntry.COLUMN_TIME);

                final int mId = cursor.getInt(idColumnIndex);
                final String item = cursor.getString(itemColumnIndex);
                final String level = cursor.getString(levelColumnIndex);
                final String des = cursor.getString(desColumindex);
                final String date = cursor.getString(dateColumindex);
                final String time = cursor.getString(timeColumindex);

                final AlertDialog.Builder alertDialog = new AlertDialog.Builder(MainActivity.this);
                alertDialog.setIcon(R.mipmap.ic_launcher);
                alertDialog.setTitle("Edit Task");

                final View v = LayoutInflater.from(MainActivity.this).inflate(R.layout.alert_view, parent, false);

                alertDialog.setView(v);


                textItem = (EditText) v.findViewById(R.id.tv_title);
                textItem.setText(item);


                textDes = (EditText) v.findViewById(R.id.tv_des);
                textDes.setText(des);


                textDate = (EditText) v.findViewById(R.id.tv_date);
                textDate.setText(date);


                textTime = (EditText) v.findViewById(R.id.tv_time);
                textTime.setText(time);


                Spinner spinner = (Spinner) v.findViewById(R.id.spinnerlevel);
// Create an ArrayAdapter using the string array and a default spinner layout
                ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(MainActivity.this,
                        R.array.level_array, android.R.layout.simple_spinner_item);
// Specify the layout to use when the list of choices appears
                adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
// Apply the adapter to the spinner
                spinner.setAdapter(adapter);

                spinner.setSelection(selection(level));


                spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                    @Override
                    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {


                        toDoLevel = String.valueOf(parent.getItemAtPosition(position));


                    }

                    @Override
                    public void onNothingSelected(AdapterView<?> parent) {

                    }
                });


                alertDialog.setPositiveButton("Update", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                        updateTask(textItem.getText().toString().trim(),
                                level, textDes.getText().toString().trim(), mId,
                                textDate.getText().toString().trim(),
                                textTime.getText().toString().trim());
                        displayToDo();
                         Toast.makeText(MainActivity.this,"New Task Entered!",Toast.LENGTH_SHORT).show();

                    }
                });
                alertDialog.setNegativeButton("Delete", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                        deleteTask(mId);
                        displayToDo();

                    }

                });

                alertDialog.show();


            }

        });


    }

    public int selection(String level) {

        int id;
        switch (level) {

            case "High":
                id = 0;
                break;
            case "Medium":
                id = 1;
                break;
            default:
                id = 2;
                break;
        }
        return id;
    }

    public void updateTask(String title, String itemLevel, String des, int pos,String date,String time) {
        SQLiteDatabase sqLiteDatabase = toDoDbHelper.getWritableDatabase();
        ContentValues values = new ContentValues();

        values.put(ToDoEntry.COLUMN_item, title);
        values.put(ToDoEntry.COLUMN_LEVEL, itemLevel);
        values.put(ToDoEntry.COLUMN_DES, des);

        values.put(ToDoEntry.COLUMN_DATE, date);
        values.put(ToDoEntry.COLUMN_TIME, time);


        String selection = ToDoContract.ToDoEntry._ID + "=?";
        String[] selectionArgs = new String[]{String.valueOf(pos)};

        sqLiteDatabase.update(ToDoContract.ToDoEntry.TABLE_NAME, values, selection, selectionArgs);

        Toast.makeText(this, "Task Updated Succesfully", Toast.LENGTH_SHORT).show();

    }

    public void deleteTask(int pos) {

        SQLiteDatabase sqLiteDatabase = toDoDbHelper.getWritableDatabase();
        String selection = ToDoContract.ToDoEntry._ID + "=?";
        String[] selectionArgs = new String[]{String.valueOf(pos)};
        sqLiteDatabase.delete(ToDoContract.ToDoEntry.TABLE_NAME, selection, selectionArgs);
        Toast.makeText(this, "Task Deleted Succesfully", Toast.LENGTH_SHORT).show();

    }

    public void insert() {

        SQLiteDatabase sqLiteDatabase = toDoDbHelper.getWritableDatabase();
        ContentValues contentValues = new ContentValues();


        contentValues.put(ToDoEntry.COLUMN_item, toDoItem);
        contentValues.put(ToDoEntry.COLUMN_LEVEL, toDoLevel);
        contentValues.put(ToDoEntry.COLUMN_DES, toDoDes);

        contentValues.put(ToDoEntry.COLUMN_DATE, toDoDate);
        contentValues.put(ToDoEntry.COLUMN_TIME, toDoTime);



        sqLiteDatabase.insert(ToDoEntry.TABLE_NAME, null, contentValues);
    }

    public void displayToDo() {
        String[] projection = {ToDoEntry._ID,
                ToDoEntry.COLUMN_item, ToDoEntry.COLUMN_LEVEL, ToDoEntry.COLUMN_DES,ToDoEntry.COLUMN_DATE,ToDoEntry.COLUMN_TIME};

        SQLiteDatabase sqLiteDatabase = toDoDbHelper.getReadableDatabase();

        // to get a Cursor that contains all rows from the pets table.
        cursor = sqLiteDatabase.query(ToDoEntry.TABLE_NAME,
                projection,
                null,
                null,
                null,
                null,
                null);

        cursorAdapter = new ToDoCursorAdapter(MainActivity.this, cursor,true);
        listView.setAdapter(cursorAdapter);


    }

    public void deleteAllTasks() {

        SQLiteDatabase sqLiteDatabase = toDoDbHelper.getWritableDatabase();
        sqLiteDatabase.delete(ToDoContract.ToDoEntry.TABLE_NAME, null, null);

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.clear:
                deleteAllTasks();
                displayToDo();
                return true;
            case R.id.help:
                helpAbout(item.getItemId());
                return true;
            case R.id.about:
                helpAbout(item.getItemId());
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onStart() {
        super.onStart();
        displayToDo();
    }


    public void helpAbout(int id) {
        Resources res = getResources();
        String title, msg;
        switch (id) {
            case R.id.help:
                title = "Help";
                msg = res.getString(R.string.help);
                break;
            case R.id.about:
                title = "About";
                msg = res.getString(R.string.about);
                break;
            default:
                title = "";
                msg = "";
                break;


        }


        AlertDialog alertBuilder = new AlertDialog.Builder(this).
                setIcon(R.mipmap.ic_launcher)
                .setTitle(title)
                .setMessage(msg)
                .show();


    }
}
