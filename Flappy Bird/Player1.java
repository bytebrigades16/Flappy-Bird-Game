import java.awt.*;
import java.awt.image.BufferedImage;
import java.awt.image.RasterFormatException;
import java.io.IOException;
import javax.imageio.ImageIO;

public class Player1 {
    BufferedImage[] birdImages = new BufferedImage[3];
    BufferedImage completeImage;
    BufferedImage backgroundImage;
    BufferedImage backgroundImage1;

    public Player1() {
        try {
            completeImage = ImageIO.read(getClass().getResource("bird.png"));
            backgroundImage1 = ImageIO.read(getClass().getResource("flappybirdss.png"));

            loadBirdImages();
        } catch (IOException e) {
            System.err.println("image not loaded");
            e.printStackTrace();
        }
    }

    private void loadBirdImages() {
        try {
            BufferedImage birdImage1 = completeImage.getSubimage(5, 5, 117, 90);
           // BufferedImage birdImage2 = completeImage.getSubimage(234, 0, 117, 90);
            BufferedImage birdImage2 = completeImage.getSubimage(117,90, 117, 90);
            BufferedImage birdImage3 = completeImage.getSubimage(352,90, 117, 90);
            backgroundImage = backgroundImage1.getSubimage(0, 0, 144, 255);

            birdImages[0] = birdImage1;
            birdImages[1] = birdImage2;
            birdImages[2] = birdImage3;
          //  birdImages[4] = birdImage4;
        } catch (RasterFormatException e) {
            System.err.println("image not loaded");
            e.printStackTrace();
        } catch (IllegalArgumentException e) {
            System.err.println("image not loaded");
            e.printStackTrace();
        }
    }

    public BufferedImage[] getBirdImage() {
        return birdImages;
    }

    public int getBirdHeight() {
        return birdImages[0].getHeight();
    }

    public int getBirdWidth() {
        return birdImages[0].getWidth();
    }

    public void paintImage(Graphics pen, BufferedImage birdImage, int x, int y) {
        pen.drawImage(backgroundImage, 0, 0, 600, 700, null);
        pen.drawImage(birdImage, x, y, 50, 50, null);
    }
}