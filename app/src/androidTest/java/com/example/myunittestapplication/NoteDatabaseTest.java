package com.example.myunittestapplication;

import android.util.Log;

import androidx.room.Room;
import androidx.test.core.app.ApplicationProvider;

import com.example.myunittestapplication.persistence.NoteDao;
import com.example.myunittestapplication.persistence.NoteDatabase;

import org.junit.After;
import org.junit.Before;

public abstract class NoteDatabaseTest {

    // system under test
    private NoteDatabase noteDatabase;

    public NoteDao getNoteDao(){
        return noteDatabase.getNoteDao();
    }

    @Before
    public void init(){
        Log.d("xxx" , "Before");
        noteDatabase = Room.inMemoryDatabaseBuilder(
                ApplicationProvider.getApplicationContext(),
                NoteDatabase.class
        ).build();
    }

    @After
    public void finish(){
        Log.d("xxx" , "After");

        noteDatabase.close();
    }
}