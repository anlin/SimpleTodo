package com.codepath.simpletodo;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ListView;

import com.codepath.simpletodo.adapters.TodoAdapter;
import com.codepath.simpletodo.database.TodoDatabaseHelper;
import com.codepath.simpletodo.model.TodoList;

import org.apache.commons.io.FileUtils;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

import static android.R.attr.startYear;

public class MainActivity extends AppCompatActivity{

    ArrayList<TodoList> items;
    TodoAdapter itemsAdaper;
    ListView lvItems;

    TodoDatabaseHelper helper;

    Date selectedDate;

    private static final int RESULT_CODE = 20;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        helper = TodoDatabaseHelper.getInstance(MainActivity.this);
        setContentView(R.layout.activity_main);
        lvItems = (ListView) findViewById(R.id.lvItems);
        items = new ArrayList<TodoList>();
        readItems();
        itemsAdaper = new TodoAdapter(this, items);
        lvItems.setAdapter(itemsAdaper);
        setupListViewListner();
    }

    // Add button onClick event
    public void onAddItem(View view) {
        EditText etNewItem = (EditText) findViewById(R.id.etNewItem);
        TodoList todoList = new TodoList();
        todoList.todoItem = etNewItem.getText().toString();
        todoList.dueDate = selectedDate;
        itemsAdaper.add(todoList);
        etNewItem.setText("");
        helper.addItem(todoList);
    }

    // Setup the list view item listener
    private void setupListViewListner(){
        lvItems.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                helper.deleteTodoItem(items.get(position));
                items.remove(position);
                itemsAdaper.notifyDataSetChanged();
                return true;
            }
        });

        lvItems.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                TodoList item = items.get(position);
                Intent intent = new Intent(MainActivity.this, EditItemActivity.class);
                intent.putExtra("item", item);
                intent.putExtra("position", position);
                startActivityForResult(intent, RESULT_CODE);
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if(requestCode == RESULT_CODE && resultCode == RESULT_CODE){
            TodoList todoList = (TodoList)data.getSerializableExtra("editedItem");
            int position = data.getIntExtra("position", 0);
            items.set(position, todoList);
            itemsAdaper.notifyDataSetChanged();
            helper.updateTodoItem(todoList);
        }
    }

    // To read and load items from database
    private void readItems(){
            items = (ArrayList<TodoList>) helper.getAllTodoItems();
    }

    public void onDueDateClick(View view) {
        DatePickerDialog datePickerDialog = new DatePickerDialog(this, onDateSetListener, 2017, 03, 15);
        datePickerDialog.show();
    }

    DatePickerDialog.OnDateSetListener onDateSetListener = new DatePickerDialog.OnDateSetListener() {
        @Override
        public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
            Calendar calendar = Calendar.getInstance();
            calendar.set(year, month, dayOfMonth);
            selectedDate = calendar.getTime();
        }
    };
}
