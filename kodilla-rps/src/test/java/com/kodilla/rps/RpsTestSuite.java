package com.kodilla.rps;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class RpsTestSuite {

    @Test
    public void testGetScore(){
        //GIVEN
        GameBoxWithoutGui.testSetter("Player", 1, 3, 3, 2);

        //WHEN
        String actual = GameBoxWithoutGui.getScore();
        String expected ="SCORE\nPlayer: 3 VS computer: 2";

        //THEN
        Assertions.assertEquals(expected, actual);
    }

//    @Test
//    public void testSelectName(){
//        //GIVEN
//
//
//        //WHEN
//
//        //THEN
//
//    }

//    @Test
//    public void testSelectRoundLimit_negativeNumber(){
//        //GIVEN
//
//        //WHEN
//
//        //THEN
//
//    }

//    @Test
//    public void testSelectRoundLimit_zero(){
//        //GIVEN
//
//        //WHEN
//
//        //THEN
//
//    }

//    @Test
//    public void testSelectRoundLimit_correctNumber(){
//        //GIVEN
//
//        //WHEN
//
//        //THEN
//
//    }

//    @Test
//    public void testValidate_finalOk(){
//     //GIVEN
//
//    //WHEN
//
//    //THEN
//
//    }

//    @Test
//    public void testValidate_finalWrong(){
//        //GIVEN
//
//        //WHEN
//
//        //THEN
//
//    }

//    @Test
//    public void testValidate_noFinalOk(){
//        //GIVEN
//
//        //WHEN
//
//        //THEN
//
//    }

//    @Test
//    public void testValidate_noFinalWrong(){
//        //GIVEN
//
//        //WHEN
//
//        //THEN
//
//    }

/*
       P/C      | 1.rock | 2.paper | 3.scissors | 4.lizard | 5.spock |
    1.rock      |    =   |    C    |     P      |    P     |    C    |
    2.paper     |    P   |    =    |     C      |    C     |    P    |
    3.scissors  |    C   |    P    |     =      |    P     |    C    |
    4.lizard    |    C   |    P    |     C      |    =     |    P    |
    5.spock     |    P   |    C    |     P      |    C     |    =    |
*/


    @Test
    public void testFight(){

        //GIVEN

        //WHEN

        //THEN

    }
}
