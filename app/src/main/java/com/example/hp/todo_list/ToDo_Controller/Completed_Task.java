package com.example.hp.todo_list.ToDo_Controller;

import android.content.DialogInterface;
import android.database.Cursor;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;
import com.example.hp.todo_list.R;
import com.example.hp.todo_list.ToDo_Database.Database;

import java.util.ArrayList;



public class Completed_Task extends AppCompatActivity {

    // Declaring variables
    ListView_Adapter listViewAdapter;
    ListView listView;
    Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.completed_task);
        // Initializing objects by ID.
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        listView = (ListView) findViewById(R.id.list_todo);
        toolbar.setTitle("Completed Tasks"); // Setting toolbar tittle

        listView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, final View view, int position, long id) { //OnClick Listener

                /*
                AlertDialog.Builder Sets the callback that will be called when the dialog is
                  dismissed for any reason.
                 */
                //Creating object
                final AlertDialog.Builder dialog = new AlertDialog.Builder(Completed_Task.this);

                dialog.setMessage("Are you sure? You want to delete this task?");
                // Set a listener to be invoked when the positive button of the dialog is pressed.
                dialog.setPositiveButton("Delete", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        Database db = new Database(getApplicationContext());

                        String ids = ((TextView) view.findViewById(R.id.taskId)).getText().toString();
                        db.delete(Long.parseLong(ids));
                        Toast.makeText(Completed_Task.this, "Task is deleted.", Toast.LENGTH_LONG).
                                show();
                        updateUI(); // Update user interface
                        db.close(); // Close the database
                    }
                });
                 //Set a listener to be invoked when the negative button of the dialog is pressed.
                dialog.setNegativeButton("Cancel", null);
                dialog.create();//Creates an AlertDialog with the arguments supplied to this builder.
               /*
               Creates an AlertDialog with the arguments supplied to this builder and immediately
                 displays the dialog.
                */
                dialog.show();
                return true;
            }

        });
        updateUI();
    }

    private void updateUI() { //Calling method
        Database db = new Database(getApplicationContext());
        /*
         Cursor interface provides random read-write access to the result set returned by a
          database query.
         */
        Cursor cursor = db.fetch(); // Fetch data from Database
        // Creating ArrayList<> for updated UI.
        final ArrayList<Integer> ids= new ArrayList<>();
        final ArrayList<String> title = new ArrayList<>();
        ArrayList<String> description = new ArrayList<>();
        ArrayList<String> date = new ArrayList<>();
        ArrayList<Integer> status = new ArrayList<>();

        while (cursor.moveToNext()) {
            // Adding data in column
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
        listViewAdapter = new ListView_Adapter(this,ids, title, description,date,status);
        /*
         ListView Adapter object acts as a bridge between an AdapterView and the underlying data
          for that view.
          */
        listView.setAdapter(listViewAdapter);
        // Closes the Cursor, releasing all of its resources and making it completely invalid.
        cursor.close();
        db.close(); //Closing DB


    }
}

