package com.example.myunittestapplication.util;

import com.example.myunittestapplication.model.Note;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInfo;
import org.junit.jupiter.api.TestReporter;
import org.junit.jupiter.api.function.Executable;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import java.util.Random;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static com.example.myunittestapplication.util.DateUtil.GET_MONTH_ERROR;
import static com.example.myunittestapplication.util.DateUtil.getMonthFromNumber;
import static com.example.myunittestapplication.util.DateUtil.monthNumbers;
import static com.example.myunittestapplication.util.DateUtil.months;

public class DateUtilTest {


    private static final String today = "02-2021";

    @Test
    public void testingCurrentTimeStamp_returnTiomeStamp(){

        assertDoesNotThrow(new Executable() {
            @Override
            public void execute() throws Throwable {

                assertEquals(today , DateUtil.getCurrentTimeStamp());
                System.out.println("Timestamp is generated correctly");

            }
        });
    }


    @ParameterizedTest
    @ValueSource(ints = {0,1,2,3,4,5,6,7,8,9,10,11})
    public void testGetMonthFromNumber_returnSuccess(int monthNubmer , TestInfo testInfo , TestReporter testReporter){
        assertEquals(months[monthNubmer] , getMonthFromNumber(monthNumbers[monthNubmer]));
        System.out.println(monthNumbers[monthNubmer] + " : " + months[monthNubmer]);
    }



    @ParameterizedTest
    @ValueSource(ints = {1,2,3,4,5,6,7,8,9,10,11})
    public void testGetMonthFromNumber_returnError(int monthNubmer , TestInfo testInfo , TestReporter testReporter){
        int randomInt = new Random().nextInt(90) + 13;
        assertEquals(GET_MONTH_ERROR , getMonthFromNumber(String.valueOf(monthNubmer * randomInt)));
        System.out.println(monthNumbers[monthNubmer] + " : " + GET_MONTH_ERROR);
    }




}
