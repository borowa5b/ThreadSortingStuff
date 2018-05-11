package pl.borowa.file_sorting.model;

import javax.swing.*;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.concurrent.BlockingQueue;

public class ThreadSorter implements Runnable {
    private int[] numbers;
    private int id;
    private JProgressBar[] jProgressBars;
    private int progressBarID;
    private BlockingQueue<Object> queue;
    private Producer producer;

    ThreadSorter(int[] numbers, int id, JProgressBar[] jProgressBars, int progressBarID, BlockingQueue<Object> queue, Producer producer) {
        this.numbers = numbers;
        this.id = id;
        this.jProgressBars = jProgressBars;
        this.progressBarID = progressBarID;
        this.queue = queue;
        this.producer = producer;
    }

    @Override
    public void run() {
        long start = System.currentTimeMillis();
        bubbleSort(numbers);
        saveFile();
        try {
            System.out.println("Thread no: " + id + ", time[ms]: " + (System.currentTimeMillis() - start));
            producer.setProgressBarID(progressBarID);
            queue.take();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    private void bubbleSort(int[] arr) {
        int n = arr.length;
        jProgressBars[progressBarID].setMaximum(n);
        int temp;
        for (int i = 0; i < n; i++) {
            for (int j = 1; j < (n - i); j++) {
                if (arr[j - 1] > arr[j]) {
                    //swap elements
                    temp = arr[j - 1];
                    arr[j - 1] = arr[j];
                    arr[j] = temp;
                }

            }
            jProgressBars[progressBarID].setValue(i + 1);
        }
    }

    private void saveFile() {
        try (PrintWriter printWriter = new PrintWriter(new File("/home/borowa/pliki/posortowane/plik" + id + ".txt"))) {
            for (int number : numbers) {
                printWriter.println(number);
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }
}
