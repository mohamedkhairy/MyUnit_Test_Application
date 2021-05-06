package com.example.myunittestapplication.ui.note;

import com.example.myunittestapplication.model.Note;
import com.example.myunittestapplication.repository.NoteRepository;
import com.example.myunittestapplication.ui.Resource;
import com.example.myunittestapplication.util.InstantExecutorExtension;
import com.example.myunittestapplication.util.LiveDataTestUtil;
import com.example.myunittestapplication.util.TestUtil;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.api.function.Executable;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import java.time.Instant;

import io.reactivex.Flowable;
import io.reactivex.internal.operators.single.SingleToFlowable;

import static com.example.myunittestapplication.repository.NoteRepository.INSERT_SUCCESS;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

//@ExtendWith(InstantExecutorExtension.class)
public class NoteViewModelTest {


    // system under test

    private NoteViewModel noteViewModel;

    @Mock
    private NoteRepository noteRepository;


    @BeforeEach
    public void init(){
        MockitoAnnotations.initMocks(this);
        noteViewModel = new NoteViewModel(noteRepository);
    }


    /**
     * can't observe a note that hasn't been set
     * */
    @Test
    void observeEmptyNoteWhenNotSet() throws Exception{
        // Arrange
        LiveDataTestUtil<Note> liveDataTestUtil = new LiveDataTestUtil<Note>();

        //Act
        Note note = liveDataTestUtil.getValue(noteViewModel.observeNote());

        //Assert
        assertNull(note);
    }


     /**
        Observe a note has been set and onChanged will trigger in activity
     */

    @Test
    void observeNote_whenSet() throws Exception {
       //Arrange
       LiveDataTestUtil<Note> liveDataTestUtil = new LiveDataTestUtil<>();
       Note note = new Note(TestUtil.TEST_NOTE_1);

       //Act
        noteViewModel.setNote(note);
        Note returnedNote = liveDataTestUtil.getValue(noteViewModel.observeNote());

        //

        assertEquals(note , returnedNote);


    }

    /**
        Insert a new note and observe row returned
     */

    @Test
    void insertNote_returnRow() throws Exception {
        //Arrange
        final int insertedRow = 1;
        Note note = new Note(TestUtil.TEST_NOTE_1);
        LiveDataTestUtil<Resource<Integer>> liveDataTestUtil = new LiveDataTestUtil<>();
        Flowable<Resource<Integer>> flowable = SingleToFlowable.just(Resource.success(insertedRow , INSERT_SUCCESS));
        when(noteRepository.insertNote(any(Note.class))).thenReturn(flowable);

        //Act
        noteViewModel.setNote(note);
        Resource<Integer> resource = liveDataTestUtil.getValue(noteViewModel.insertNote());

        //Assert
        verify(noteRepository).insertNote(any(Note.class));
        verifyNoMoreInteractions(noteRepository);
        assertEquals(Resource.success(insertedRow , INSERT_SUCCESS) , resource);

    }



    /**
        insert: dont return a new row without observer
     */

    @Test
    void dontReturnInsertRowWithoutObserver() throws Exception {
        // Arrange
        Note note = new Note(TestUtil.TEST_NOTE_1);

        // Act
        noteViewModel.setNote(note);

        // Assert
        verify(noteRepository, never()).insertNote(any(Note.class));
    }


/*
        set note, null title, throw exception
     */

    @Test
    void setNote_nullTitle_throwException() throws Exception {
        //Arrange
        Note note = new Note(TestUtil.TEST_NOTE_1);
        note.setTitle(null);



        //assert
        assertThrows(Exception.class, new Executable() {
            @Override
            public void execute() throws Throwable {
                //Act
                noteViewModel.setNote(note);
            }
        });
    }







}
