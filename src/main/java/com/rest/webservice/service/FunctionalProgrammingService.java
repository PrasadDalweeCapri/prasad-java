package com.rest.webservice.service;

import com.rest.webservice.util.AppUtils;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.function.Predicate;
import java.util.stream.Collectors;


//This service is used to try and test different methods in stream api
//I had used two methods defined in the util class to see how streams use custom methods in filter()

@Service
public class FunctionalProgrammingService
{
    private final List<Integer> numbers=List.of(1,2,3,4,5,6,7,8);

    private Predicate<Integer> isEven= numbers-> (numbers%2==0);


    //streams use multithreading therefore their elements have to be threadsafe, so local variables are generally atomic
    public Map<Integer, Integer> getRemainders()
    {
        Map<Integer, Integer> result = numbers.stream().collect(Collectors.toMap(
                number -> number,
                number -> number % 2
        ));
//        int sum = sum(numbers);
        return result;
    }


    //filters and foreach can be combined
    public Integer sumEven()
    {
        AtomicInteger x=new AtomicInteger(0);
        numbers.stream()
                .filter(isEven)
                .forEach(num->x.addAndGet(num));
        return x.get();
    }

    //filter can be used to create new lists based upon a condition
    public List<Integer> evenCheck(){
        return numbers.stream()
                .filter(isEven)
                .collect(Collectors.toList());
    }

    //map doesn't affect original list
    public List<Integer> evenSquare()
    {
        return numbers.stream()
                .filter(AppUtils::evenCheck)       //filter out even numbers
                .map(x->x*x)                        //return square of even numbers   (doesn't change original list)
                .collect(Collectors.toList());      //save the square of even numbers
    }


}
