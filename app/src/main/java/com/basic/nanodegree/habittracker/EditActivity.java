package com.basic.nanodegree.habittracker;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.content.ContentValues;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.Calendar;

public class EditActivity extends AppCompatActivity {


    EditText mNameEditText;
    EditText mNumberOfTimesEditText;
    TextView mDateTextView;
    private String mCurrentDate;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit);

        mNameEditText = (EditText) findViewById(R.id.edit_text_name);
        mNumberOfTimesEditText = (EditText) findViewById(R.id.edit_text_number_of_times);
        mDateTextView = (EditText) findViewById(R.id.text_view_date);

        Calendar c = Calendar.getInstance();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        mCurrentDate =  sdf.format(c.getTime());
        Log.d("Edit", mCurrentDate);
        mDateTextView.setText(mCurrentDate);
    }

    private void insertHabit(){

        String nameString = mNameEditText.getText().toString().trim();
        String numberOfTimesString = mNumberOfTimesEditText.getText().toString().trim();
        int numberOfTimes = 0;
        if(!"".equals(numberOfTimesString))
            numberOfTimes = Integer.parseInt(numberOfTimesString);

        HabitDbHelper mHabitDbHelper = new HabitDbHelper(this);

        SQLiteDatabase db = mHabitDbHelper.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(HabitContract.HabitEntry.COLUMN_NAME, nameString);
        values.put(HabitContract.HabitEntry.COLUMN_START_DATE, mCurrentDate);
        values.put(HabitContract.HabitEntry.COLUMN_NUMBER_OF_TIMES, numberOfTimes);

        long newRowId = db.insert(HabitContract.HabitEntry.TABLE_NAME, null, values);

        if (newRowId == -1) {
            // If the row ID is -1, then there was an error with insertion.
            Toast.makeText(this, "Error with saving habit", Toast.LENGTH_SHORT).show();
        } else {
            // Otherwise, the insertion was successful and we can display a toast with the row ID.
            Toast.makeText(this, "Habit saved with row id: " + newRowId, Toast.LENGTH_SHORT).show();
        }
    }

    public void onSave(View view) {
        if("".equals(mNameEditText.getText().toString())) {
            Toast.makeText(this, "Habit Name must be not empty", Toast.LENGTH_SHORT).show();
            return;
        }
        insertHabit();
        finish();
    }

    public void onCancel(View view) {
        finish();
    }
}

