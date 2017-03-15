package com.codepath.simpletodo.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import com.codepath.simpletodo.model.TodoList;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

/**
 * This class is the database helper for database
 * Created by anlinsquall on 2/3/17.
 */

public class TodoDatabaseHelper extends SQLiteOpenHelper {

    private static final String TAG="TodoDatabaseHelper";
    private static TodoDatabaseHelper sInstance;

    //  database Info
    private static final String DATABASE_NAME = "todoDatabase";
    private static final int DATABASE_VERSION = 1;

    // Table info
    private static final String TABLE_TODO = "todos";

    // Table Columns
    private static final String KEY_TODO_ID = "id";
    private static final String KEY_TODO_ITEM = "item";
    private static final String KEY_TODO_DUE = "due";

    // Singleton to create helper instance
    public static synchronized TodoDatabaseHelper getInstance(Context context){
        if (sInstance ==null){
            sInstance = new TodoDatabaseHelper(context.getApplicationContext());
        }
        return sInstance;
    }

    private TodoDatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }


    @Override
    public void onCreate(SQLiteDatabase db) {
        String CREATE_TODO_TABLE = "CREATE TABLE " + TABLE_TODO + "(" +
                KEY_TODO_ID + " INTEGER PRIMARY KEY, " +
                KEY_TODO_ITEM + " TEXT, " + KEY_TODO_DUE + " DATE)";
        db.execSQL(CREATE_TODO_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        if(oldVersion != newVersion){
            db.execSQL("DROP TABLE IF EXISTS " + TABLE_TODO);
            onCreate(db);
        }
    }

    // Add item to database
    public void addItem(TodoList todoList){
        SQLiteDatabase sqLiteDatabase = getWritableDatabase();
        sqLiteDatabase.beginTransaction();
        try {
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
            String date = sdf.format(todoList.dueDate);
            ContentValues contentValues = new ContentValues();
            contentValues.put(KEY_TODO_ITEM, todoList.todoItem);
            contentValues.put(KEY_TODO_DUE, date);

            sqLiteDatabase.insertOrThrow(TABLE_TODO, null, contentValues);
            sqLiteDatabase.setTransactionSuccessful();
        }
        catch (Exception e){
            Log.d(TAG, "Error while trying to add todolsit to database");
        }
        finally {
            sqLiteDatabase.endTransaction();
        }
    }

    // get all items.
    public List<TodoList> getAllTodoItems(){
        List<TodoList> todoList = new ArrayList<TodoList>();
        String TODOLIST_SELECT_QUERY = String.format("SELECT * FROM %s",TABLE_TODO);

        SQLiteDatabase sqLiteDatabase  = getReadableDatabase();
        Cursor cursor = sqLiteDatabase.rawQuery(TODOLIST_SELECT_QUERY, null);
        try{
            if(cursor.moveToFirst()){
                do{
                    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
                    TodoList newTodoList = new TodoList();
                    newTodoList.todoItem = cursor.getString(cursor.getColumnIndex(KEY_TODO_ITEM));
                    newTodoList.id = cursor.getLong(cursor.getColumnIndex(KEY_TODO_ID));
                    String date = cursor.getString(cursor.getColumnIndex(KEY_TODO_DUE));
                    newTodoList.dueDate = sdf.parse(date);
                    todoList.add(newTodoList);
                }while (cursor.moveToNext());
            }
        } catch (Exception e){
            Log.d(TAG, "Error while trying to get todolist from database");
        }
        finally {
            {
                if(cursor != null && !cursor.isClosed()){
                    cursor.close();
                }
            }
        }
        return todoList;
    }

    // Update an item
    public int updateTodoItem(TodoList todoList){
        SQLiteDatabase sqLiteDatabase = getWritableDatabase();

        ContentValues contentValues = new ContentValues();
        contentValues.put(KEY_TODO_ITEM, todoList.todoItem);
        contentValues.put(KEY_TODO_ID, todoList.id);

        return sqLiteDatabase.update(TABLE_TODO, contentValues, KEY_TODO_ID + " =?",
                new String[]{Long.toString(todoList.id)});
    }

    // Delete an item
    public void deleteTodoItem(TodoList todoList){
        SQLiteDatabase sqLiteDatabase = getWritableDatabase();
        sqLiteDatabase.beginTransaction();
        sqLiteDatabase.delete(TABLE_TODO, KEY_TODO_ID + "=" +todoList.id, null);
        sqLiteDatabase.setTransactionSuccessful();
        sqLiteDatabase.endTransaction();
    }

    // Delete All Item. For the future use.
    public void deleteAllTodoList(){
        SQLiteDatabase sqLiteDatabase = getWritableDatabase();
        sqLiteDatabase.beginTransaction();
        try {
            sqLiteDatabase.delete(TABLE_TODO, null, null);
            sqLiteDatabase.setTransactionSuccessful();
        }catch (Exception e){
            Log.d(TAG, "Error while deleting todo list table");
        }finally {
            sqLiteDatabase.endTransaction();
        }
    }

}
