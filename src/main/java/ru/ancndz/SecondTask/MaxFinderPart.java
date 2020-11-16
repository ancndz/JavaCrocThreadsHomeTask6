package ru.ancndz.SecondTask;

import java.util.List;
import java.util.concurrent.Callable;

public class MaxFinderPart<T extends Comparable<T>> implements Callable<T> {

    private final List<T> subList;

    public MaxFinderPart(List<T> subList) {
        this.subList = subList;
    }

    @Override
    public T call() {
        T max = subList.get(0);
        for (T eachItem : subList) {
            if (eachItem.compareTo(max) > 0) {
                max = eachItem;
            }
        }
        return max;
    }
}
