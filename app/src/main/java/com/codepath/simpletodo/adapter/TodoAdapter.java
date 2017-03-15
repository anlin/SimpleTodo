package com.codepath.simpletodo.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.codepath.simpletodo.R;
import com.codepath.simpletodo.model.TodoList;

import org.w3c.dom.Text;

import java.util.ArrayList;

import static android.R.attr.resource;

/**
 * Created by aungnainglin on 3/3/17.
 */

public class TodoAdapter extends ArrayAdapter<TodoList> {


    public TodoAdapter(Context context, ArrayList<TodoList> todoLists) {
        super(context, 0, todoLists);
    }

    @NonNull
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        // get data for this position
        TodoList todoList = getItem(position);

        //Check if existing view can be reused.
        if(convertView == null){
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.item_todo, parent,
                    false);
        }

        TextView tvTodoItem = (TextView) convertView.findViewById(R.id.tvTodoItem);
        tvTodoItem.setText(todoList.todoItem);

        return convertView;
    }
}
