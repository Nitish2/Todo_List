package com.example.hp.todo_list.ToDo_Controller;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.hp.todo_list.R;

import java.util.ArrayList;


/*
  * Creating class and extends it from BaseAdapter.
  * BaseAdapter is the base class for so many concrete adapter implementations .
 */
public class ListView_Adapter extends BaseAdapter {

    private Activity context;
    /*
       Creating ArrayList<> because arrayList inherits AbstractList class and implements
       List interface.
     */
    private ArrayList<Integer> taskIds;
    private ArrayList<String> date;
    private ArrayList<String> title;
    private ArrayList<String> description;
    private ArrayList<Integer> status;

    //Creating constructor
    ListView_Adapter(Activity context, ArrayList<Integer> ids, ArrayList<String> title,
                     ArrayList<String> description, ArrayList<String> date, ArrayList<Integer> status) {
        super(); // Calling super class
        // Giving reference to the objects
        this.taskIds = ids;
        this.context = context;
        this.date = date;
        this.title = title;
        this.description = description;
        this.status = status;
    }

    @Override
    public int getCount() {
        return taskIds.size();
    }

    @Override
    public Object getItem(int i) {
        return null;
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

// A ViewHolder object stores each of the component views inside the tag field of the Layout.
    private class ViewHolder {
        TextView textViewId;
        TextView textViewDate;
        TextView textViewTitle;
        TextView textViewDescription;
        ImageView statusView;
        TextView textViewDuedate;
       //TextView textViewStatus;
    }

    @Override
    public View getView(int position, View view, ViewGroup viewGroup) {
        ViewHolder holder;
        // LayoutInflater is used to represent layout XML file into its corresponding.
        LayoutInflater layoutInflater = context.getLayoutInflater();

        if (view == null) {
            // Creating and initializing view holder objects by ID.
            view = layoutInflater.inflate(R.layout.row_list, null);
            holder = new ViewHolder();
            holder.textViewId = (TextView) view.findViewById(R.id.taskId);
            holder.textViewDate = (TextView) view.findViewById(R.id.date);
            holder.textViewTitle = (TextView) view.findViewById(R.id.title);
            holder.textViewDescription = (TextView) view.findViewById(R.id.description);
            // holder.textViewStatus = (TextView) view.findViewById(R.id.taskStatus);
            holder.statusView = (ImageView) view.findViewById(R.id.status);
            holder.textViewDuedate = (TextView) view.findViewById(R.id.timeStamp);
            view.setTag(holder);
        } else {
            holder = (ViewHolder) view.getTag();
        }
        holder.textViewId.setText(String.valueOf(taskIds.get(position)));
        //  holder.textViewStatus.setText(String.valueOf(status.get(position)));
        holder.textViewDate.setText(date.get(position));
        holder.textViewTitle.setText(title.get(position));
        holder.textViewDescription.setText(description.get(position));

        if (status.get(position) == 0) {
            holder.statusView.setImageResource(R.drawable.incomplete);
        } else {
            holder.statusView.setImageResource(R.drawable.complete);
        }
        holder.textViewDuedate.setText(date.get(position));
        return view;
    }
}
