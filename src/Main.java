package com.company;

import java.awt.image.BufferedImage;
import java.io.*;
import parcs.*;

import javax.imageio.ImageIO;
import java.util.List;
import java.util.ArrayList;

public class Main {


    public static void main(String[] args) throws IOException {
        task curtask = new task();
        curtask.addJarFile("Algo.jar");

        AMInfo info = new AMInfo(curtask, null);

        System.out.println("Start executing");
        long startTime = System.nanoTime();

        List<BufferedImage> photos = new ArrayList<>();
        int n = 20;
        for (int i = 0; i < n; i++){
            File file = new File("photo1.jpg");
            BufferedImage image = ImageIO.read(file);
            photos.add(image);
        }

        List<channel> channels = new ArrayList<>();
        for (BufferedImage photo : photos) {
            point p = info.createPoint();
            channel c = p.createChannel();
            p.execute("Algo");
            c.write((Serializable) photo);
            channels.add(c);
        }

        int i = 1;

        for (parcs.channel channel : channels) {
            System.out.println("\n\n\n\n Processing a photo number " + String.valueOf(i));
            i++;

            for (int j = 0; j < 6; j++) {
                List<Double> part = (List<Double>) channel.readObject();

                System.out.println("Red: " + part.get(0));
                System.out.println("Green: " + part.get(1));
                System.out.println("Blue: " + part.get(2));
                System.out.println("L: " + part.get(3));
                System.out.println("Channel a: " + part.get(4));
                System.out.println("Channel b: " + part.get(5));

                System.out.println("\n");
            }
        }

        double estimatedTime = (double) (System.nanoTime() - startTime) / 1000000000;
        System.out.println("Time total (excluding IO): " + estimatedTime);

        curtask.end();
    }
}