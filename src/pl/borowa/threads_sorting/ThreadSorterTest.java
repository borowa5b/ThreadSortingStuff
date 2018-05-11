package pl.borowa.threads_sorting;

import java.util.Arrays;
import java.util.Random;

public class ThreadSorterTest {
    public static void main(String[] args) {
        int threadsNum = 4;
        final int RANGE = 10000000;
        int[] numbers = new int[RANGE];
        if (threadsNum > numbers.length / 2) System.exit(1);

        for (int i = 0; i < numbers.length; i++) {
            numbers[i] = new Random().nextInt(RANGE);
        }
//        System.out.println(Arrays.toString(numbers));
        long start = System.currentTimeMillis();

        ThreadSorter[] threadSorters = new ThreadSorter[threadsNum];
        Thread[] threads = new Thread[threadsNum];
        for (int i = 0, j = 0; i < threadsNum; i++, j += numbers.length / threadsNum) {
            threadSorters[i] = new ThreadSorter(numbers, j, j + numbers.length / threadsNum, i);
            threads[i] = new Thread(threadSorters[i]);
            threads[i].start();
        }

        for (int i = 0; i < threadsNum; i++) {
            try {
                threads[i].join();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        /*int[] sortedNumbers = new int[numbers.length];
        int i = 0, j = 0;
        while (i < threadsNum) {
            int[] tmp = threadSorters[i].getNumbers();
//            System.out.println(Arrays.toString(tmp));
            for (int k = 0; k < tmp.length; j++, k++) {
                sortedNumbers[j] = tmp[k];
            }
            i++;
        }*/
//        System.out.println("------------------------------------------------------------------------");
//        System.out.println(Arrays.toString(sortedNumbers));
//        System.out.println("------------------------------------------------------------------------");
//        Arrays.sort(sortedNumbers);
//        System.out.println(Arrays.toString(sortedNumbers));
        Arrays.sort(numbers);
        long stop = System.currentTimeMillis();
        System.out.println("Czas wykonania[ms]: " + (stop - start));
    }
}
