import java.awt.image.BufferedImage;
import java.io.*;
import parcs.*;

import javax.imageio.ImageIO;
import java.util.List;
import java.util.ArrayList;

public class Main {


    public static void main(String[] args) throws IOException {
        task curtask = new task();
        curtask.addJarFile("Main.jar");

        AMInfo info = new AMInfo(curtask, null);

        System.out.println("Start executing");
        long startTime = System.nanoTime();

        List<File> photos = new ArrayList<>();
        int n = 20;
        for (int i = 0; i < n; i++){
            File file = new File("photo1.jpg");
            photos.add(file);
        }

        List<channel> channels = new ArrayList<>();
        for (File photo : photos) {
            point p = info.createPoint();
            channel c = p.createChannel();
            p.execute("Algo");
            c.write(photo);
            channels.add(c);
        }

        int i = 1;

        for (parcs.channel channel : channels) {
            System.out.println("\n\n\n\n Processing a photo number " + String.valueOf(i));
            i++;

            for (int j = 0; j < 6; j++) {
                String part = (String) channel.readObject();
                System.out.println(part + "\n");
            }
        }

        double estimatedTime = (double) (System.nanoTime() - startTime) / 1000000000;
        System.out.println("Time total (excluding IO): " + estimatedTime);

        curtask.end();
    }
}
