package ru.ancndz.SecondTask.AtomicIntegerUse;

import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

public class AtomicFinder implements Runnable {

    private final List<Integer> list;
    private final AtomicInteger max;
    private final int procId;
    private final int numOfProc;

    public AtomicFinder(List<Integer> list, int procId, int numOfProc, AtomicInteger atomicInteger) {
        this.list = list;
        this.procId = procId;
        this.numOfProc = numOfProc;
        this.max = atomicInteger;
    }

    @Override
    public void run() {
        /**
         * | 0 |  1  |  2  |...
         * | i | i+1 | i+2 | i+n | i+1+n | i+2+n |...
         * n - [0, threads]
         * i - [0, list size]
         */
        for (int i = procId; i < list.size(); i += numOfProc) {
            if (list.get(i).compareTo(max.get()) > 0) {
                max.set(list.get(i));
            }
        }
    }
}
