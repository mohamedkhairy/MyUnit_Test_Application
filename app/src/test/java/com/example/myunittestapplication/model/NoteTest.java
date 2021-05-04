package com.example.myunittestapplication.model;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;


public class NoteTest {


    public static final String TIMESTAMP_1 = "05-2019";
    public static final String TIMESTAMP_2 = "04-2019";


    /*
        Compare two equal Notes
     */

    @Test
    void isNoteEqual_identicalProperties_returnTrue() throws Exception{
        // Arrange

        Note note1 = new Note("Note #1", "This is note #1", TIMESTAMP_1);
        note1.setId(1);
        Note note2 = new Note("Note #1", "This is note #1", TIMESTAMP_1);
        note2.setId(1);
        // Act


        // Assert
        assertEquals(note1 , note2);
        System.out.println("The notes are equal!");

    }

    /*
        Compare notes with 2 different ids
     */

    @Test
    void isNoteEqual_differentIds_returnFalse() throws Exception{
        Note note1 = new Note("Note #1", "This is note #1", TIMESTAMP_1);
        note1.setId(1);
        Note note2 = new Note("Note #1", "This is note #1", TIMESTAMP_1);
        note2.setId(2);
        // Act


        // Assert
        assertNotEquals(note1 , note2);
        System.out.println("The notes Ids are NOT equal!");
    }



    /*
        Compare two notes with different timestamps
     */

    @Test
    void isNoteEqual_differentTime_returnTrue() throws Exception{
        Note note1 = new Note("Note #1", "This is note #1", TIMESTAMP_1);
        note1.setId(1);
        Note note2 = new Note("Note #1", "This is note #1", TIMESTAMP_2);
        note2.setId(1);
        // Act


        // Assert
        assertEquals(note1 , note2);
        System.out.println("The notes are equal!");
    }

    /*
        Compare two notes with different titles
     */
    @Test
    void isNoteEqual_differentTitle_returnFalse() throws Exception{
        Note note1 = new Note("Note #1", "This is note #1", TIMESTAMP_1);
        note1.setId(1);
        Note note2 = new Note("Note #2", "This is note #1", TIMESTAMP_2);
        note2.setId(1);
        // Act


        // Assert
        assertNotEquals(note1 , note2);
        System.out.println("The notes Titles are Not equal!");
    }


    /*
        Compare two notes with different content
     */

    @Test
    void isNoteEqual_differentContent_returnFalse() throws Exception{
        Note note1 = new Note("Note #1", "This is note #1", TIMESTAMP_1);
        note1.setId(1);
        Note note2 = new Note("Note #1", "This is note #2", TIMESTAMP_2);
        note2.setId(1);
        // Act


        // Assert
        assertNotEquals(note1 , note2);
        System.out.println("The notes Contents are Not equal!");
    }
}
