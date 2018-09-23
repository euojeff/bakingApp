package com.jeffersonaraujo.bakingapp.database;

import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;

import java.util.List;

@Dao
public interface RecipeDao {

    @Insert
    void insertRecipe(RecipeEntry recipe);

    @Query("Delete FROM recipe")
    void clean();

    @Query("SELECT * FROM recipe")
    LiveData<RecipeEntry> loadSelectedRecipeLiveData();

    @Query("SELECT * FROM recipe")
    RecipeEntry loadSelectedRecipe();
}
