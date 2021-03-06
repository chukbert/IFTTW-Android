package com.example.ifttw;

import androidx.room.Database;
import androidx.room.RoomDatabase;

import com.example.ifttw.Routines;
import com.example.ifttw.RoutinesDAO;

@Database(entities = {Routines.class}, version = 2, exportSchema = false)
public abstract class AppDatabase extends RoomDatabase {
    public abstract RoutinesDAO userDao();
}