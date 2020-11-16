package ru.ancndz.SecondTask;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

public class MaxFinder<T extends Comparable<T>> {

    private final List<T> list;
    private final Integer parts;

    public MaxFinder(List<T> list, Integer parts) {
        this.list = list;
        this.parts = parts;
    }

    public T getMaxItem() throws ExecutionException, InterruptedException {
        ExecutorService executorService = Executors.newFixedThreadPool(parts);
        List<Future<T>> smallTempList = new ArrayList<>();
        T max = list.get(0);

        for (int i = 0; i < parts; i++) {
            System.out.println("Starting new task (" + i + ")...");
            MaxFinderPart<T> task = new MaxFinderPart<>(this.list.subList(i * (list.size() / parts), (i + 1) * (list.size() / parts) - 1));
            Future<T> future = executorService.submit(task);
            smallTempList.add(future);
        }

        for (Future<T> future : smallTempList) {
            while (!future.isDone());
            System.out.println("Check new result...");
            if (future.get().compareTo(max) > 0) {
                max = future.get();
            }
        }
        return max;
    }
}
