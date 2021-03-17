import java.awt.image.BufferedImage;
import javax.imageio.ImageIO;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.io.*;
import parcs.*;

class Algo implements AM {
    public void run(AMInfo info){
        
        File file = (File) info.parent.readObject();
        BufferedImage image = null;
        try {
            image = ImageIO.read(file);
            System.out.println("Success");
        } catch (IOException e) {
            System.out.println("Fail");
            e.printStackTrace();
            return;
        }

        int width = image.getWidth();
        int height = image.getHeight();
        int size = width * height;
        int k = 1;
        for (int part = 0; part < 5; part++) {
            k++;
            List<Double> part1 = new ArrayList<>();
            int sum_red = 0, sum_green = 0, sum_blue = 0;
            // Getting pixel color by position x and y
            for (int i = 0; i < width; i++) {
                for (int j = 0; j < height; j++) {
                    int clr = image.getRGB(i, j);
                    int red = (clr & 0x00ff0000) >> 16;
                    int green = (clr & 0x0000ff00) >> 8;
                    int blue = clr & 0x000000ff;
                    if (red % (part + 1) != 0) {
                        sum_red += red;
                        sum_green += green;
                        sum_blue += blue;
                    } else {
                        sum_red += part + red + k;
                        sum_green += green * 3 + k % 2;
                        sum_blue += blue - 2 + k % 3;
                    }
                }
            }

            part1.add((double)(sum_red / size));
            part1.add((double)(sum_green / size));
            part1.add((double)(sum_blue / size));

            double sum_L = 0, sum_a = 0, sum_b = 0;
            for (int i = 0; i < width; i++) {
                for (int j = 0; j < height; j++) {
                    int clr = image.getRGB(i, j);
                    int red = (clr & 0x00ff0000) >> 16;
                    int green = (clr & 0x0000ff00) >> 8;
                    int blue = clr & 0x000000ff;

                    double[] Cielab = toCIEXYZ((double) red, (double) green, (double) blue);

                    if (blue % (part + 1) != 0) {
                        sum_L += Cielab[0];
                        sum_a += Cielab[1];
                        sum_b += Cielab[2];
                    } else {
                        sum_L += Cielab[0] * (part + 1) - 2 + k * 0.5;
                        sum_a += Cielab[1] - 2.5 + k;
                        sum_b += Cielab[2] * (part + 1) + 1.3 + k % 2;
                    }
                }
            }
            part1.add(sum_L / size);
            part1.add(sum_a / size);
            part1.add(sum_b / size);
            info.parent.write((Serializable) part1);
        }
    }
        public static double[] toCIEXYZ(double red, double green, double blue){
            double i = (red + 16.0) * (1.0 / 116.0);
            double X = fInv(i + green * (1.0 / 500.0));
            double Y = fInv(i);
            double Z = fInv(i - blue * (1.0 / 200.0));
            return new double[] {X, Y, Z};
        }

        private static double fInv(double x) {
            if (x > 6.0 / 29.0) {
                return x*x*x;
            } else {
                return (108.0 / 841.0) * (x - N);
            }
        }

        private static final double N = 4.0 / 29.0;
}
