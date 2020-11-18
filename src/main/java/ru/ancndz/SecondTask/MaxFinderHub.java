package ru.ancndz.SecondTask;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

/**
 * Класс для параллельного поиска максимального элемента в списке
 * Передает в {@link MaxFinder} ссылку списка, номер треда и кол-во всех тредов
 * Получает список локальных максимумов из частей списка, находит среди них максимум и возвращает его
 * @param <T> - {@link Comparable} any
 */
public class MaxFinderHub<T extends Comparable<T>> {

    private final List<T> list;
    private final Integer procNum;

    public MaxFinderHub(List<T> list, Integer parts) {
        this.list = list;
        this.procNum = parts;
    }

    /**
     * Работа с параллельными вычислениями с помощью {@link ExecutorService} и {@link Future}
     * @return T {@link Comparable} max - максимальное среди полученных промежуточных результатов
     * @throws ExecutionException
     * @throws InterruptedException
     */
    public T getMaxItem() throws ExecutionException, InterruptedException {
        ExecutorService executorService = Executors.newFixedThreadPool(procNum);
        List<Future<T>> smallTempList = new ArrayList<>();
        T max = list.get(0);

        for (int i = 0; i < procNum; i++) {
            //не передаю .subList(), т.к. он создает new SubList, а это в два раза больше по памяти?
            MaxFinder<T> task = new MaxFinder<>(this.list, i, procNum);
            Future<T> future = executorService.submit(task);
            smallTempList.add(future);
        }

        for (Future<T> future : smallTempList) {
            while (!future.isDone());
            if (future.get().compareTo(max) > 0) {
                max = future.get();
            }
        }
        return max;
    }
}
