package com.example.hp.todo_list.ToDo_Controller;

/**
 * Created by Hp on 06-10-2017.
 */

import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import com.example.hp.todo_list.R;
import com.example.hp.todo_list.ToDo_Database.Database;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {


    ListView listView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        listView = (ListView) findViewById(R.id.listView);
        updateUI();
    }

    public boolean onCreateOptionsMenu(Menu menu) {
        // MenuInflater is used to represent menu XML files into Menu objects.
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_main, menu);
        return true;

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.new_task:
                AlertDialog.Builder dialog = new AlertDialog.Builder(this);

                LayoutInflater inflater = getLayoutInflater();
                final View inflaterView = inflater.inflate(R.layout.dialog_box, null);
                dialog.setView(inflaterView);


                final EditText title = (EditText) inflaterView.findViewById(R.id.edtxt1);
                String s = title.getText().toString();
                if(s.length()  != 0){
                    title.setError("title can not be empty");
                } else {

                }
                final EditText description = (EditText) inflaterView.findViewById(R.id.edtxt2);
                final DatePicker date = (DatePicker) inflaterView.findViewById(R.id.date);

                dialog.setPositiveButton("Add", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Log.d("MainActivity: ", "Add Title Of task: " + title.getText().toString());
                        Log.d("MainActivity: ", "Description Of Task: " + description.getText().
                                toString());
                        Log.d("MainActivity: ", "Date: " + date.getYear() + "-" + date.getMonth()
                                + 1 + "-" + date.getDayOfMonth());


                        final Database databaseManager = new Database(getApplicationContext());
                        databaseManager.insert(title.getText().toString(), description.getText()
                                .toString(), date.getYear() + "-" +
                                (date.getMonth() + 1) + "-" + date.getDayOfMonth(), "1");

                        databaseManager.close();

                        updateUI();

                    }
                });
                // Set a listener to be invoked when the negative button of the dialog is pressed.
                dialog.setNegativeButton("Cancel", null);
                dialog.create();
                dialog.show();
                return true;
            case R.id.complete:
                Toast.makeText(this, "Completed Tasks", Toast.LENGTH_LONG).show();

                Intent intent=new Intent(MainActivity.this,Completed_Task.class);
                startActivity(intent);

                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
    private void updateUI() {
       Database db = new Database(getApplicationContext());

        Log.d("Called: ", "update");
        Cursor cursor = db.fetch();
        ArrayList<Integer> ids = new ArrayList<>();
        ArrayList<String> title = new ArrayList<>();
        ArrayList<String> description = new ArrayList<>();
        ArrayList<String> date = new ArrayList<>();
        ArrayList<Integer> status = new ArrayList<>();


        while (cursor.moveToNext()) {
            Log.d("title: ", cursor.getString(cursor.getColumnIndex(Database.MyDatabaseHelper.COLUMN_TITLE)));
            ids.add(cursor.getInt(cursor.getColumnIndex(Database.MyDatabaseHelper.COLUMN_ID)));
            title.add(cursor.getString(cursor.getColumnIndex
                    (Database.MyDatabaseHelper.COLUMN_TITLE)));
            description.add(cursor.getString(cursor.getColumnIndex
                    (Database.MyDatabaseHelper.COLUMN_DESCRIPTION)));
            date.add(cursor.getString(cursor.getColumnIndex
                    (Database.MyDatabaseHelper.COLUMN_DATE)));
            status.add(cursor.getInt(cursor.getColumnIndex
                    (Database.MyDatabaseHelper.COLUMN_STATUS)));
        }
        ListView_Adapter listViewAdapter = new ListView_Adapter(this,ids,title,
                description, date,status);
        listView.setAdapter(listViewAdapter);
        cursor.close();
        db.close();
    }

}
