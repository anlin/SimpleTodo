package com.codepath.simpletodo.adapters;

import android.content.Context;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.codepath.simpletodo.R;
import com.codepath.simpletodo.model.TodoList;

import java.text.SimpleDateFormat;
import java.util.ArrayList;

import static android.R.attr.resource;

/**
 * Created by anlinsquall on 15/3/17.
 */

public class TodoAdapter extends ArrayAdapter<TodoList> {
    public TodoAdapter(Context context, ArrayList<TodoList> todoLists) {
        super(context, 0, todoLists);
    }

    @NonNull
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        TodoList todoList = getItem(position);

        if(convertView == null){
            convertView = LayoutInflater.from(getContext())
                    .inflate(R.layout.item_todo, parent, false);
        }
        TextView tvTodo = (TextView) convertView.findViewById(R.id.tvTodoItem);
        TextView tvDueDate = (TextView) convertView.findViewById(R.id.tvDueDate);

        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
        tvDueDate.setText(simpleDateFormat.format(todoList.dueDate));
        tvTodo.setText(todoList.todoItem);
        return convertView;
    }
}
