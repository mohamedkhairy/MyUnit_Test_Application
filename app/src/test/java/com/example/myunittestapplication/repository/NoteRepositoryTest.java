package com.example.myunittestapplication.repository;

import com.example.myunittestapplication.R;
import com.example.myunittestapplication.model.Note;
import com.example.myunittestapplication.persistence.NoteDao;
import com.example.myunittestapplication.ui.Resource;
import com.example.myunittestapplication.util.TestUtil;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.function.Executable;
import org.mockito.Mockito;

import io.reactivex.Single;

import static com.example.myunittestapplication.repository.NoteRepository.INSERT_FAILURE;
import static com.example.myunittestapplication.repository.NoteRepository.INSERT_SUCCESS;
import static com.example.myunittestapplication.repository.NoteRepository.NOTE_TITLE_NULL;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class NoteRepositoryTest {


    private static final Note NOTE1 = new Note(TestUtil.TEST_NOTE_1);
    private static final Note NOTE2 = new Note(TestUtil.TEST_NOTE_2);


    //// system under test
    private NoteRepository noteRepository;

    private NoteDao noteDao;

    @BeforeEach
    public void initEach(){
        noteDao = mock(NoteDao.class);
        noteRepository = new NoteRepository(noteDao);
    }


    /*
        insert note
        verify the correct method is called
        confirm observer is triggered
        confirm new rows inserted
     */
    @Test
    void insertNote_returnRow() throws Exception {

        /// arrange
        final long insertRow = 1L;
        final Single<Long> returnedData = Single.just(insertRow);
        when(noteDao.insertNote(any(Note.class))).thenReturn(returnedData);

        // Act
        final Resource<Integer> resourceValue = noteRepository.insertNote(NOTE1).blockingSingle();
        final Resource<Integer> resourceValue2 = noteRepository.insertNote(NOTE2).blockingSingle();

        /// assert
        verify(noteDao , times(2)).insertNote(any(Note.class));
        verifyNoMoreInteractions(noteDao);

        System.out.println("Returned Value : " + resourceValue.data);

        assertEquals(Resource.success(1 , INSERT_SUCCESS) , resourceValue);

                // Or test using RxJava
//        noteRepository.insertNote(NOTE1)
//                .test()
//                .await()
//                .assertValue(Resource.success(1, INSERT_SUCCESS));

    }



    /*
        Insert note
        Failure (return -1)
     */

    @Test
    void insertNote_returnFailure() throws Exception {
        // arrange

        final Long insertRow = -1L;
        final Single<Long> returnedData = Single.just(insertRow);
        when(noteDao.insertNote(any(Note.class))).thenReturn(returnedData);

        //// act
        final Resource<Integer> resource = noteRepository.insertNote(NOTE1).blockingSingle();

        // assert
        verify(noteDao).insertNote(any(Note.class));
        verifyNoMoreInteractions(noteDao);

        System.out.println("Returned Value : " + resource.data);

        assertEquals(Resource.error(null , INSERT_FAILURE) , resource);

    }



    /*
        insert note
        null title
        confirm throw exception
     */
    @Test
    void insertNote_nullTitle_throwException() throws Exception {
       Exception exception = assertThrows(Exception.class, new Executable() {
            @Override
            public void execute() throws Throwable {
                final Note note = new Note(TestUtil.TEST_NOTE_1);
                note.setTitle(null);
                noteRepository.insertNote(note);
            }
        });


       assertEquals(NOTE_TITLE_NULL , exception.getMessage());
    }




    }
