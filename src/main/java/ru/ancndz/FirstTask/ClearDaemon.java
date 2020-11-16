package ru.ancndz.FirstTask;

import java.io.File;

public class ClearDaemon extends Thread {
    private int time;
    private String path;

    public ClearDaemon(int time, String path) {
        System.out.println("Initialization...");
        this.time = time;
        this.path = path;
        this.setDaemon(true);
    }

    // удаление все файлов в папке (саму папку не трогаем)
    public void clearSchedule() {
        File folder = new File(path);
        clearRec(folder);
    }

    // *почти* такой же метод, только с последующим удалением пустой папки
    private void clearFolder(File folder) {
        clearRec(folder);
        folder.delete();
    }

    private void clearRec(File folder) {
        File[] files = folder.listFiles();
        if (files != null) {
            for (File file : files) {
                //если файл в нашей папке - папка, то удаляем ее методом выше
                if (file.isDirectory()) {
                    clearFolder(folder);
                } else {
                    file.delete();
                }
            }
        }
    }

    public int getTime() {
        return time;
    }

    public String getPath() {
        return path;
    }

    @Override
    public void run() {
        while (!isInterrupted() && isDaemon()) {
            System.out.println("Clearing...");
            clearSchedule();
            System.out.println("Cleared!");
            for (int i = 5; i > 0; i--) {
                System.out.println("Next clean in " + ((time / 5) * i) + " seconds!");
                try {
                    sleep(time/5 * 1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
