package com.rest.webservice.util;

public class AppUtils {

    //checks whether given number is even
    public static boolean evenCheck(int number)
    {
        return number % 2 == 0;
    }

    //gets the remainder for division by 2 for given number
    public static Integer getRemainderOfDivisionBy2(int number)
    {
        return number%2;
    }
}
