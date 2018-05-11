package pl.borowa.threads_minmax;

public class ThreadMinMax implements Runnable {
    private int max, min;
    private int range1, range2;
    private int[] numbers;

    ThreadMinMax(int[] numbers, int range1, int range2) {
        this.numbers = numbers;
        this.range1 = range1;
        this.range2 = range2;
    }

    @Override
    public void run() {
        min = numbers[range1];
        max = numbers[range1];
        for (int i = range1 + 1; i < range2; i++) {
            if(numbers[i] < min) min = numbers[i];
            else if(numbers[i] > max) max = numbers[i];
        }
    }

    public int getMax() {
        return max;
    }

    public int getMin() {
        return min;
    }
}
