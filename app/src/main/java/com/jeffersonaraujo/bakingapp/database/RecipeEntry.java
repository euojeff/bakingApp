package com.jeffersonaraujo.bakingapp.database;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.Ignore;
import android.arch.persistence.room.PrimaryKey;

import java.util.Date;

@Entity(tableName = "recipe")
public class RecipeEntry {

    @PrimaryKey(autoGenerate = true)
    private int id;
    private String json;

    @Ignore
    public RecipeEntry(String json) {
        this.json = json;
    }

    public RecipeEntry(int id, String json) {
        this.id = id;
        this.json = json;
    }

    public String getJson() {
        return json;
    }

    public void setJson(String json) {
        this.json = json;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
}
