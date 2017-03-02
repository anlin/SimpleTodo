package com.codepath.simpletodo;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

import com.codepath.simpletodo.model.TodoList;

public class EditItemActivity extends AppCompatActivity {
    private static final int RESULT_OK = 20;
    private EditText mEditItem;
    private int position;
    private TodoList todoList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_item);
        todoList = (TodoList)getIntent().getSerializableExtra("item");
        position = getIntent().getIntExtra("position",0);
        mEditItem = (EditText) findViewById(R.id.etEditItem);
        mEditItem.setText(todoList.todoItem);
    }

    // Save button onClick event
    public void onSaveItem(View view) {
        String editedText;
        editedText = mEditItem.getText().toString();
        todoList.todoItem = editedText;
        Intent data = new Intent();
        data.putExtra("editedItem", todoList);
        data.putExtra("position", position);
        setResult(RESULT_OK, data);
        finish();
    }
}
