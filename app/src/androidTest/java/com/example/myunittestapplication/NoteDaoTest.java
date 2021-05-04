package com.example.myunittestapplication;

import android.database.sqlite.SQLiteConstraintException;
import android.util.Log;

import androidx.arch.core.executor.testing.InstantTaskExecutorRule;

import com.example.myunittestapplication.model.Note;
import com.example.myunittestapplication.util.LiveDataTestUtil;
import com.example.myunittestapplication.util.TestUtil;

import org.junit.Rule;
import org.junit.Test;

import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;

public class NoteDaoTest extends NoteDatabaseTest {

    public static final String TEST_TITLE = "This is a test title";
    public static final String TEST_CONTENT = "This is some test content";
    public static final String TEST_TIMESTAMP = "08-2018";

    @Rule
    public InstantTaskExecutorRule rule = new InstantTaskExecutorRule();

    /**
     * Insert , Read , Delete
     * ***/
    @Test
    public void insertReadDelete() throws Exception {
        Log.d("xxx" , "Test 1");

        Note note = new Note(TestUtil.TEST_NOTE_1);

        /// Insert
        getNoteDao().insertNote(note).blockingGet();

        /// Read
        LiveDataTestUtil<List<Note>> liveDataTestUtil = new LiveDataTestUtil<>();
        List<Note> noteList = liveDataTestUtil.getValue(getNoteDao().getNotes());

        assertNotNull(noteList);

        assertEquals(note.getContent() , noteList.get(0).getContent());
        assertEquals(note.getTimestamp() , noteList.get(0).getTimestamp());
        assertEquals(note.getTitle() , noteList.get(0).getTitle());

        note.setId(noteList.get(0).getId());
        assertEquals(note , noteList.get(0));


        //// Delete
        getNoteDao().deleteNote(note).blockingGet();

        // confirm the database is empty
        noteList = liveDataTestUtil.getValue(getNoteDao().getNotes());
        assertEquals(0 , noteList.size());

    }

    /*
        Insert, Read, Update, Read, Delete,
     */
    @Test
    public void insertReadUpdateReadDelete() throws Exception{
        Log.d("xxx" , "Test 2");

        Note note = new Note(TestUtil.TEST_NOTE_1);

        // Insert
        getNoteDao().insertNote(note).blockingGet();

        // Read
        LiveDataTestUtil<List<Note>> listLiveDataTestUtil = new LiveDataTestUtil<>();
        List<Note> noteList = listLiveDataTestUtil.getValue(getNoteDao().getNotes());

        assertNotNull(noteList);

        assertEquals(note.getContent() , noteList.get(0).getContent());
        assertEquals(note.getTimestamp() , noteList.get(0).getTimestamp());
        assertEquals(note.getTitle() , noteList.get(0).getTitle());

        note.setId(noteList.get(0).getId());
        assertEquals(note , noteList.get(0));

        //// Update
        note.setTitle(TEST_TITLE);
        note.setContent(TEST_CONTENT);
        note.setTimestamp(TEST_TIMESTAMP);
        getNoteDao().updateNote(note).blockingGet();

        //// Read Updates
        noteList = listLiveDataTestUtil.getValue(getNoteDao().getNotes());

        assertEquals( TEST_CONTENT , noteList.get(0).getContent());
        assertEquals( TEST_TITLE , noteList.get(0).getTitle());
        assertEquals( TEST_TIMESTAMP , noteList.get(0).getTimestamp());

        note.setId(noteList.get(0).getId());
        assertEquals(note , noteList.get(0));

        //// Delete
        getNoteDao().deleteNote(note).blockingGet();

        // confirm the database is empty
        noteList = listLiveDataTestUtil.getValue(getNoteDao().getNotes());
        assertEquals(0 , noteList.size());

    }

    /*
        Insert note with null title, throw exception
     */
    @Test(expected = SQLiteConstraintException.class)
    public void insert_nullTitle_throwSQLiteConstraintException() throws Exception{

        final Note note = new Note(TestUtil.TEST_NOTE_1);
        note.setTitle(null);

        // insert
        getNoteDao().insertNote(note).blockingGet();
    }


   /*
        Insert, Update with null title, throw exception
     */

    @Test(expected = SQLiteConstraintException.class)
    public void updateNote_nullTitle_throwSQLiteConstraintException() throws Exception{

        Note note = new Note(TestUtil.TEST_NOTE_1);

        // insert
        getNoteDao().insertNote(note).blockingGet();

        // read
        LiveDataTestUtil<List<Note>> liveDataTestUtil = new LiveDataTestUtil<>();
        List<Note> insertedNotes = liveDataTestUtil.getValue(getNoteDao().getNotes());
        assertNotNull(insertedNotes);

        // update
        note = new Note(insertedNotes.get(0));
        note.setTitle(null);
        getNoteDao().updateNote(note).blockingGet();

    }


}
