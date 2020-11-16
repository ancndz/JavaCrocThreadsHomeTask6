package ru.ancndz.FirstTask;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import ru.ancndz.FirstTask.ClearDaemon;

import java.io.*;

class ClearDaemonTest {

    private final String path = "src/test/resources/temp/files/";
    private final int time = 10;
    private final int loopCyclesTest = 2;

    void createFilesToDelete() throws IOException {
        for (int i = 0; i < 10; i++) {
            File file = new File(path + i);
            try (OutputStream outputStream = new FileOutputStream(file)) {
                outputStream.write(1);
            } catch (FileNotFoundException e) {
                createPath();
                OutputStream outputStream = new FileOutputStream(file);
                outputStream.write(1);
                outputStream.close();
            }
        }
    }

    private void createPath() {
        try {
            File dir = new File(path);
            dir.mkdirs();
        } catch (SecurityException e) {
            System.out.println(e.getMessage());
            e.printStackTrace();
        }
    }

    public boolean isDirEmpty() {
        File dir = new File(path);
        File[] files = dir.listFiles();
        if (files != null) {
            return files.length == 0;
        } else {
            return true;
        }
    }

    void loopTest() throws IOException, InterruptedException {

        //ждем чуть чуть пока демон произведет очистку (этот тред ждет)
        Thread.sleep(1000);
        //проверяем, что папка пустая
        Assertions.assertTrue(isDirEmpty());

        Thread.sleep(2000);
        //снова добавляем файлы
        createFilesToDelete();
        System.out.println("Files generated!");
        Assertions.assertFalse(isDirEmpty());

        //ждем полный цикл (что бы очистка точно прошла)
        Thread.sleep(time * 1000);
        //снова проверяем
        Assertions.assertTrue(isDirEmpty());

    }

    @Test
    void clearDirectory() throws IOException, InterruptedException {
        ClearDaemon clearDaemon = new ClearDaemon(time, path);
        clearDaemon.start();
        for (int i = 0; i < loopCyclesTest; i++) {
            loopTest();
        }
    }

}