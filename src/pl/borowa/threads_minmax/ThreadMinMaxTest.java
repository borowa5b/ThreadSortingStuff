package pl.borowa.threads_minmax;

public class ThreadMinMaxTest {
    public static void main(String[] args) {
        int threadNum = 5;
        int[] numbers = {7, 6, 4, 2, 1, 8, 3, 5, 11, 9};
        if (threadNum > numbers.length / 2) System.exit(1);

        ThreadMinMax[] threadMinMaxes = new ThreadMinMax[threadNum];
        Thread[] threads = new Thread[threadNum];
        for (int i = 0, j = 0; i < threadNum; i++, j += numbers.length / threadNum) {
            threadMinMaxes[i] = new ThreadMinMax(numbers, j, j + numbers.length / threadNum);
            threads[i] = new Thread(threadMinMaxes[i]);
            threads[i].start();
        }

        for (int i = 0; i < threadNum; i++) {
            try {
                threads[i].join();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        int min = numbers[0];
        int max = numbers[0];
        for (int i = 0; i < threadNum; i++) {
            System.out.println("Min: " + threadMinMaxes[i].getMin() + " | Max: " + threadMinMaxes[i].getMax());
            if (threadMinMaxes[i].getMin() < min) min = threadMinMaxes[i].getMin();
            else if (threadMinMaxes[i].getMax() > max) max = threadMinMaxes[i].getMax();
        }
        System.out.println("---------------");
        System.out.println("Min: " + min + " | Max: " + max);
    }
}
