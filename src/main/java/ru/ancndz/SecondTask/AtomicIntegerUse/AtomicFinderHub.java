package ru.ancndz.SecondTask.AtomicIntegerUse;


import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * Класс для параллельного поиска максимального элемента в списке
 * Передает в {@link AtomicFinder} ссылку списка, номер треда, кол-во всех тредов и переменную {@link AtomicInteger}
 *      для максимума
 */
public class AtomicFinderHub {
    private final List<Integer> list;
    private final Integer procNum;
    private final AtomicInteger atomicInteger;

    public AtomicFinderHub(List<Integer> list, Integer parts) {
        this.list = list;
        this.procNum = parts;
        atomicInteger = new AtomicInteger(list.get(0));
    }

    /**
     * Работа с параллельными вычислениями через {@link Thread} и {@link AtomicInteger}
     * @return java.lang.Integer - {@link AtomicInteger} .get
     * @throws InterruptedException
     */
    public Integer getMaxItem() throws InterruptedException {
        List<Thread> threads = new ArrayList<>();
        for (int i = 0; i < procNum; i++) {
            //не передаю .subList(), т.к. он создает new SubList, а это в два раза больше по памяти?
            Thread thread = new Thread(new AtomicFinder(this.list, i, procNum, this.atomicInteger));
            threads.add(thread);
            thread.start();
        }

        for (Thread thread : threads) {
            thread.join();
        }

        return atomicInteger.get();
    }
}
