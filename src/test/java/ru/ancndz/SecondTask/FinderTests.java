package ru.ancndz.SecondTask;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import ru.ancndz.SecondTask.AtomicIntegerUse.AtomicFinderHub;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.concurrent.ExecutionException;

public class FinderTests {
    private static final int N = 10_000_000;
    private static final List<Integer> integerList = new ArrayList<>();

    private static int expectedValue;

    //число потоков
    private final int P = 10;

    @BeforeAll
    static void createRawList() {
        for (int i = 0; i < N; i++) {
            integerList.add(new Random().nextInt());
        }
        expectedValue = integerList.get(0);
        for (Integer eachItem : integerList) {
            if (eachItem.compareTo(expectedValue) > 0) {
                expectedValue = eachItem;
            }
        }
    }

    @Test
    void runTestWithFutures() throws ExecutionException, InterruptedException {
        MaxFinderHub<Integer> maxFinder = new MaxFinderHub<>(integerList, P);
        Integer max = maxFinder.getMaxItem();

        Assertions.assertEquals(expectedValue, max);
    }

    @Test
    void runTestWithAtomic() throws InterruptedException {
        AtomicFinderHub atomicFinderHubV2 = new AtomicFinderHub(integerList, P);
        Integer max = atomicFinderHubV2.getMaxItem();

        Assertions.assertEquals(expectedValue, max);
    }

    //P.S. мне было интересно, что выполнится быстрее - победил runTestWithFutures

}
