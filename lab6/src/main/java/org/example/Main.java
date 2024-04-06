package org.example;

import org.apache.commons.lang3.tuple.Pair;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ForkJoinPool;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class Main {
    public static void main(String[] args) {
        String currentDirectory = System.getProperty("user.dir");
        System.out.println("Current directory: " + currentDirectory);
//        String inputPath = "/Users/bitesaitzzz/Downloads/New Compressed (zipped) Folder (2)/Java/PT_lab6/src/main/resources/img/input";
//        String outputPath = "/Users/bitesaitzzz/Downloads/New Compressed (zipped) Folder (2)/Java/PT_lab6/src/main/resources/img/output/";
        String inputPath = "src/main/resources/img/input";
        String outputPath = "src/main/resources/img/output/";

        ForkJoinPool pool = new ForkJoinPool(5);
        List<Path> files;
        Path source = Path.of(inputPath);

        try (Stream<Path> stream = Files.list(source)){
            files = stream.collect(Collectors.toList());
            Collection<Path> collection = new ArrayList<Path>(files);
            Stream<Path>  pathStream = collection.stream();
            long time = System.currentTimeMillis();
            try {
                pool.submit(() -> {
                    Stream<Pair<String, BufferedImage>> outputPairStream = pathStream.map(val -> {
                        String name = String.valueOf(val.getFileName());
                        BufferedImage image = null;
                        try {
                            image = ImageIO.read(val.toFile());
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                        return Pair.of(name, image);
                    }).map(val -> {
                        if (val.getRight() == null) {
                            return null; // Пропустить изображение, если загрузка не удалась
                        }

                        BufferedImage image = new BufferedImage(val.getRight().getWidth(),
                                val.getRight().getHeight(),
                                val.getRight().getType());
                        for (int i = 0; i < val.getRight().getWidth(); i++) {
                            for (int j = 0; j < val.getRight().getHeight(); j++) {
                                int rgb = val.getRight().getRGB(i, j);
                                Color color = new Color(rgb);
                                int r = color.getRed();
                                int g = color.getGreen();
                                int b = color.getBlue();
                                Color outColor = new Color(r, b, r);
                                int outRgb = outColor.getRGB();
                                image.setRGB(i, j, outRgb);
                            }
                        }
                        return Pair.of(val.getLeft(), image);
                    }).parallel();
                    outputPairStream.forEach(val -> {
                        if (val != null) {
                            try {
                                ImageIO.write(val.getValue(), "jpg", new File(outputPath + val.getKey()));
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                        }
                    });
                }).get();
                pool.shutdown(); // Отключаем пул после завершения задач

                try {
                    pool.awaitTermination(Long.MAX_VALUE, java.util.concurrent.TimeUnit.NANOSECONDS); // Ждем завершения всех задач
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

                System.out.println(System.currentTimeMillis() - time);
            } catch (InterruptedException | ExecutionException e) {
                e.printStackTrace();
            }
        } catch (IOException e) {
            System.out.println("Unable to load images");
            e.printStackTrace();
        }
    }
}