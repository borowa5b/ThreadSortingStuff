package pl.borowa.file_sorting.view;

import pl.borowa.file_sorting.model.Producer;

import javax.swing.*;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.Random;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;

public class GUIView {
    private JPanel panel1;
    private JProgressBar progressBar1;
    private JProgressBar progressBar2;
    private JProgressBar progressBar3;
    private JProgressBar progressBar4;
    private JButton openFilesButton;

    private int threads = 4;

    private GUIView() {
        init();
    }

    public static void main(String[] args) {
        JFrame frame = new JFrame("GUI Sorter");
        frame.setContentPane(new GUIView().panel1);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        frame.setVisible(true);
    }

    private void init() {
        openFilesButton.addActionListener(actionEvent -> {
            JFileChooser chooser = new JFileChooser();
            chooser.setMultiSelectionEnabled(true);
            chooser.setCurrentDirectory(new File("/home/borowa/pliki/"));
            chooser.showOpenDialog(panel1);
            File[] filesInDirectory = chooser.getSelectedFiles();

            JProgressBar[] progressBars = {progressBar1, progressBar2, progressBar3, progressBar4};

            BlockingQueue<Object> queue = new ArrayBlockingQueue<>(4);
            Producer producer = new Producer(queue, filesInDirectory, progressBars);
            new Thread(producer).start();
        });
    }

    private void generujPliki() {
        for (int i = 0; i < 10; i++) {
            try (PrintWriter zapis = new PrintWriter("/home/borowa/pliki/do sortowania/plik" + i + ".txt")) {
                for (int j = 0; j < 25000; j++) {
                    int rand = new Random().nextInt(500) + 1;
                    zapis.println(rand);
                }
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
        }

        for (int i = 10; i < 20; i++) {
            try (PrintWriter zapis = new PrintWriter("/home/borowa/pliki/do sortowania/plik" + i + ".txt")) {
                for (int j = 0; j < 10000; j++) {
                    int rand = new Random().nextInt(500) + 1;
                    zapis.println(rand);
                }
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
        }

        for (int i = 20; i < 30; i++) {
            try (PrintWriter zapis = new PrintWriter("/home/borowa/pliki/do sortowania/plik" + i + ".txt")) {
                for (int j = 0; j < 40000; j++) {
                    int rand = new Random().nextInt(500) + 1;
                    zapis.println(rand);
                }
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
        }

        for (int i = 30; i < 40; i++) {
            try (PrintWriter zapis = new PrintWriter("/home/borowa/pliki/do sortowania/plik" + i + ".txt")) {
                for (int j = 0; j < 35000; j++) {
                    int rand = new Random().nextInt(500) + 1;
                    zapis.println(rand);
                }
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
        }

        for (int i = 40; i < 50; i++) {
            try (PrintWriter zapis = new PrintWriter("/home/borowa/pliki/do sortowania/plik" + i + ".txt")) {
                for (int j = 0; j < 20000; j++) {
                    int rand = new Random().nextInt(500) + 1;
                    zapis.println(rand);
                }
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
        }
    }
}
