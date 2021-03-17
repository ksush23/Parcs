import java.io.*;
import parcs.*;
import java.util.List;
import java.util.ArrayList;

public class Main implements AM {

    public static void main(String[] args) {
        task curtask = new task();
        curtask.addJarFile("Algo.jar");
        (new Main()).run(new AMInfo(curtask, (channel)null));
        curtask.end();
    }

    public void run(AMInfo info) {
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
            p.execute("Algo.java");
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
    }
}
