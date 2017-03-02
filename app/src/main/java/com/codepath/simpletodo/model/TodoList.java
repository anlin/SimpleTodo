package com.codepath.simpletodo.model;

import java.io.Serializable;

/**
 * Created by anlinsquall on 2/3/17.
 */

public class TodoList implements Serializable {
    public long id;
    public String todoItem;

    @Override
    public String toString() {
        return todoItem;
    }
}
