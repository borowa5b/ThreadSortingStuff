package pl.borowa.threads_sorting;

import java.util.Arrays;

public class ThreadSorter implements Runnable {
    private int[] numbers;
    private int id, range1, range2;

    public ThreadSorter(int[] numbers, int range1, int range2, int id) {
//        this.numbers = Arrays.copyOfRange(numbers,range1,range2);
        this.numbers = numbers;
        this.range1 = range1;
        this.range2 = range2;
        this.id = id;
    }

    @Override
    public void run() {
        long start = System.currentTimeMillis();
//        Arrays.sort(numbers);
        Arrays.sort(numbers, range1, range2);
        System.out.println("Watek nr: " + id + ", czas[ms]: " + (System.currentTimeMillis() - start));
    }

    public int[] getNumbers() {
        return numbers;
    }
}
