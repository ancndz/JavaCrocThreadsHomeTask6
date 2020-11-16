package ru.ancndz.SecondTask;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.concurrent.ExecutionException;

class MaxFinderTest {

    private final int N = 10_000_00;
    private final List<Integer> integerList = new ArrayList<>();

    //число потоков
    private final int P = 10;

    @BeforeEach
    void createRawList() {
        for (int i = 0; i < N; i++) {
            integerList.add(new Random().nextInt());
        }
    }

    Integer getMaxItem() {
        Integer max = integerList.get(0);
        for (Integer eachItem : integerList) {
            if (eachItem.compareTo(max) > 0) {
                max = eachItem;
            }
        }
        return max;
    }

    @Test
    void runTest() throws ExecutionException, InterruptedException {
        MaxFinder<Integer> maxFinder = new MaxFinder<>(integerList, P);
        Integer max = maxFinder.getMaxItem();

        Assertions.assertEquals(getMaxItem(), max);
    }


}