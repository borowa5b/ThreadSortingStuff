package pl.borowa.file_sorting.model;

import javax.swing.*;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Scanner;
import java.util.concurrent.BlockingQueue;

public class Producer implements Runnable {
    private BlockingQueue<Object> queue;
    private File[] files;
    private JProgressBar[] progressBars;
    private ArrayList<Integer[]> arrayOfFiles;
    private int progressBarID;
    private boolean checked = false;

    public Producer(BlockingQueue<Object> queue, File[] files, JProgressBar[] progressBars) {
        this.queue = queue;
        this.files = files;
        this.progressBars = progressBars;
    }

    private void loadFilesToArray() {
        arrayOfFiles = new ArrayList<>();
        for (File file : files) {
            try (Scanner in = new Scanner(new FileReader(file))) {
                ArrayList<Integer> tmpList = new ArrayList<>();
                while (in.hasNextLine()) {
                    tmpList.add(Integer.parseInt(in.nextLine()));
                }
                int[] numbers = tmpList.stream().mapToInt(j -> j).toArray();
                Integer[] nums = Arrays.stream(numbers).boxed().toArray(Integer[]::new);
                arrayOfFiles.add(nums);
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
        }
    }

    synchronized void setProgressBarID(int progressBarID) {
        this.progressBarID = progressBarID;
    }

    @Override
    public void run() {
        Object dummyObject = new Object();
        loadFilesToArray();
        for (int i = 0; i < files.length; i++) {
            int[] numbers = Arrays.stream(arrayOfFiles.get(i)).mapToInt(Integer::intValue).toArray();
            try {
                queue.put(dummyObject);
                Runnable sorter = new ThreadSorter(numbers, i, progressBars, progressBarID, queue, this);
                Thread thread = new Thread(sorter);
                thread.start();
                System.out.println("Produced: " + i);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            if (!checked) {
                progressBarID++;
                if (progressBarID == 3) checked = true;
            }
        }
    }
}
