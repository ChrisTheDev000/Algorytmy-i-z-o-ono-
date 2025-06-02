import java.util.*;
import java.io.PrintWriter;

public class QuickSort {

    public static void quickSort(int[] array, int low, int high) {
        if (low < high) {
            int pivotIndex = partition(array, low, high);
            quickSort(array, low, pivotIndex - 1);
            quickSort(array, pivotIndex + 1, high);
        }
    }

    private static int partition(int[] array, int low, int high) {
        int pivot = array[high];
        int i = (low - 1);
        for (int j = low; j < high; j++) {
            if (array[j] <= pivot) {
                i++;
                int temp = array[i];
                array[i] = array[j];
                array[j] = temp;
            }
        }
        int temp = array[i + 1];
        array[i + 1] = array[high];
        array[high] = temp;
        return i + 1;
    }

    private static long getUsedMemory() {
        Runtime runtime = Runtime.getRuntime();
        runtime.gc();
        return runtime.totalMemory() - runtime.freeMemory();
    }

    private static int[] generateRandomArray(int size) {
        Random rand = new Random();
        int[] array = new int[size];
        for (int i = 0; i < size; i++) {
            array[i] = rand.nextInt(10_000);
        }
        return array;
    }

    public static void main(String[] args) {
        System.out.println("Copyright (c) 2025 Krystian Zatka, 124048. Wszelkie prawa zastrzeżone.");

        int[] nValues = {1000, 2000, 3000, 4000, 5000, 6000, 7000, 8000, 9000, 10000};

        try (PrintWriter writer = new PrintWriter("srednie_wyniki.csv")) {
            writer.println("n,avgTime(ms),avgMemory(KB)");

            for (int n : nValues) {
                System.out.println("\nWyniki dla n = " + n + ":");
                long totalDuration = 0;
                long totalMemory = 0;

                for (int i = 0; i < 10; i++) {
                    int[] array = generateRandomArray(n);

                    long startMemory = getUsedMemory();
                    long startTime = System.nanoTime();

                    quickSort(array, 0, array.length - 1);

                    long endTime = System.nanoTime();
                    long endMemory = getUsedMemory();

                    long duration = (endTime - startTime) / 1_000_000;  // ms
                    long memoryUsed = (endMemory - startMemory) / 1024;  // KB
                    duration = Math.max(duration, 1);
                    memoryUsed = Math.max(memoryUsed, 1);

                    totalDuration += duration;
                    totalMemory += memoryUsed;

                    System.out.println("Próba " + (i + 1) + ": czas = " + duration + " ms, pamięć = " + memoryUsed + " KB");
                }

                double avgDuration = (double) totalDuration / 10;
                double avgMemory = (double) totalMemory / 10;

                System.out.printf("Średni czas dla n = %d: %.2f ms\n", n, avgDuration);
                System.out.printf("Średnia pamięć dla n = %d: %.2f KB\n", n, avgMemory);

                writer.printf("%d,%.2f,%.2f%n", n, avgDuration, avgMemory);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
