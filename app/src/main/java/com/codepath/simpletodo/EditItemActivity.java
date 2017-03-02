package com.codepath.simpletodo;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

public class EditItemActivity extends AppCompatActivity {
    private static final int RESULT_OK = 20;
    private EditText mEditItem;
    private int position;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_item);
        String itemText = getIntent().getStringExtra("itemText");
        position = getIntent().getIntExtra("position",0);
        mEditItem = (EditText) findViewById(R.id.etEditItem);
        mEditItem.setText(itemText);
    }


    public void onSaveItem(View view) {
        String editedText;
        editedText = mEditItem.getText().toString();
        Intent data = new Intent();
        data.putExtra("editedText", editedText);
        data.putExtra("position", position);
        setResult(RESULT_OK, data);
        finish();
    }
}
