package com.example.myunittestapplication;

import android.util.Log;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.assertEquals;

public class Example {
    private List<String> list;


    @BeforeClass
    public static void setup() {
        Log.d( "xxxTEST" , "startup - creating DB connection");
    }

    @AfterClass
    public static void tearDown() {
        Log.d( "xxxTEST" , "closing DB connection");
    }

    @Before
    public void init() {
        Log.d( "xxxTEST" , "startup");
        list = new ArrayList<>(Arrays.asList("test1", "test2"));
    }

    @After
    public void teardown() {
        Log.d( "xxxTEST" , "teardown");
        list.clear();
    }


//    @Test
//    public void whenCheckingListSize_thenSizeEqualsToInit() {
//        Log.d( "xxxTEST" ,"executing test");
//        assertEquals(2, list.size());
//
//        list.add("another test");
//    }
//
//    @Test
//    public void whenCheckingListSizeAgain_thenSizeEqualsToInit() {
//        Log.d( "xxxTEST" ,"executing another test");
//        assertEquals(2, list.size());
//
//        list.add("yet another test");
//    }


    @Test
    public void simpleTest() {
        Log.d( "xxxTEST" ,"simple test");
    }

    @Test
    public void anotherSimpleTest() {
        Log.d( "xxxTEST" ,"another simple test");
    }
}
