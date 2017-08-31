package com.example.android.todo;

import android.content.Context;
import android.content.res.Resources;
import android.database.Cursor;
import android.graphics.drawable.GradientDrawable;
import android.os.Build;
import android.support.v4.content.ContextCompat;
import android.support.v4.content.res.ResourcesCompat;
import android.support.v4.widget.CursorAdapter;
import android.support.v4.widget.ResourceCursorAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.android.todo.data.ToDoContract;

/**
 * Created by Enyason on 7/30/2017.
 */

public class ToDoCursorAdapter extends CursorAdapter {

    Context context;

    public ToDoCursorAdapter(Context context, Cursor c, boolean autoRequery) {
        super(context, c, autoRequery);
        this.context = context;
    }

    @Override
    public View newView(Context context, Cursor cursor, ViewGroup parent) {
       return LayoutInflater.from(context).inflate(R.layout.list_item,parent,false);

    }

    @Override
    public void bindView(View view, Context context, Cursor cursor) {

        TextView tvItem = (TextView)view.findViewById(R.id.tv_item);
        TextView tvDate = (TextView)view.findViewById(R.id.tv_date);
        TextView tvTime = (TextView)view.findViewById(R.id.tv_time);

        TextView tvCircle = (TextView)view.findViewById(R.id.tv_circle);

//        String item = cursor.getString(cursor.getColumnIndexOrThrow(ToDoContract.ToDoEntry.COLUMN_item));
        int itemColumnIndex = cursor.getColumnIndex(ToDoContract.ToDoEntry.COLUMN_item);
        int levelColumIndex = cursor.getColumnIndex(ToDoContract.ToDoEntry.COLUMN_LEVEL);

        int dateColumindex = cursor.getColumnIndex(ToDoContract.ToDoEntry.COLUMN_DATE);
        int timeColumindex = cursor.getColumnIndex(ToDoContract.ToDoEntry.COLUMN_TIME);

        String item = cursor.getString(itemColumnIndex);
        String level = cursor.getString(levelColumIndex);
        String date = cursor.getString(dateColumindex);
        String time = cursor.getString(timeColumindex);

        tvItem.setText(item);

        tvDate.setText(date);
        tvTime.setText(time);
        tvCircle.setText(setCircleText(level));

//        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
//            tvStatus.setTextColor(c);
//        }

//        int color = getPriorityColor(level);
//        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
//            tvStatus.setTextColor(context.getColor(color));
//     // Set the proper background color on the magnitude circle.
        // Fetch the background from the TextView, which is a GradientDrawable.
        GradientDrawable magnitudeCircle = (GradientDrawable) tvCircle.getBackground();
        // Get the appropriate background color based on the current earthquake magnitude
        int magnitudeColor = getPriorityColor(level);
        // Set the color on the magnitude circle
        magnitudeCircle.setColor(magnitudeColor);

//        tvStatus.setTextColor(context.getApplicationContext().getResources().getColor(color));






    }


    public int getPriorityColor(String level){
        int col;
        switch (level){
            case "High":
                col = R.color.colorHigh;
                break;
            case "Medium":
                col = R.color.colorMesium;
                break;
            default:
                col = R.color.clorLow;

        }
        return ContextCompat.getColor(context,col);
    }

    public String setCircleText(String level){

        String text;
        switch (level){
            case "High":
                text = "H";
                break;
            case "Medium":
                text = "M";
                break;
            default:
                text = "L";

        }
        return text;


    }
}
