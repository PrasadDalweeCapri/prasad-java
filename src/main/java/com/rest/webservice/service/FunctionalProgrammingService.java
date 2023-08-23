package com.rest.webservice.service;

import com.rest.webservice.util.ListUtils;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

@Service
public class FunctionalProgrammingService
{
    private List<Integer> numbers=List.of(1,2,3,4,5,6,7,8);


    //streams use multithreading therefore their elements have to be threadsafe, so local variables are generally atomic
    public Integer getRemainders()
    {
        AtomicInteger x= new AtomicInteger(0);
        numbers.stream().forEach(number->x.addAndGet(ListUtils.getRemainder(number)));
        return x.get();
    }


    //filters and foreach can be combined
    public Integer sumEven()
    {
        AtomicInteger x=new AtomicInteger(0);
        numbers.stream()
                .filter(ListUtils::evenCheck)
                .forEach(num->x.addAndGet(num));
        return x.get();
    }

    //filter can be used to create new lists based upon a condition
    public List<Integer> evenCheck(){
        return numbers.stream()
                .filter(ListUtils::evenCheck)
                .collect(Collectors.toList());
    }

    //map doesn't affect original list
    public List<Integer> evenSquare()
    {
        return numbers.stream()
                .filter(ListUtils::evenCheck)       //filter out even numbers
                .map(x->x*x)                        //return square of even numbers   (doesn't change original list)
                .collect(Collectors.toList());      //save the square of even numbers
    }


}
