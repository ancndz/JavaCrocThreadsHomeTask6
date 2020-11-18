package ru.ancndz.SecondTask;

import java.util.List;
import java.util.concurrent.Callable;

public class MaxFinder<T extends Comparable<T>> implements Callable<T> {

    private final List<T> list;
    private final int procId;
    private final int numOfProc;

    public MaxFinder(List<T> list, int procId, int numOfProc) {
        this.list = list;
        this.procId = procId;
        this.numOfProc = numOfProc;
    }

    @Override
    public T call() {
        T max = list.get(0);
        /**
         * | 0 |  1  |  2  |...
         * | i | i+1 | i+2 | i+n | i+1+n | i+2+n |...
         * n - [0, threads]
         * i - [0, list size]
         */
        for (int i = procId; i < list.size(); i += numOfProc) {
            if (list.get(i).compareTo(max) > 0) {
                max = list.get(i);
            }
        }
        return max;
    }
}
