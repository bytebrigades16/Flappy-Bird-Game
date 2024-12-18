import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.Random;
import javax.imageio.ImageIO;

class Pipe {
    public boolean passed;
    int x, y;
    int pipeGap;
    int pipeWidth = 26;
    int pipeHeight = 160;
    BufferedImage completeImage;
    BufferedImage[] pipeImage = new BufferedImage[2];

    public Pipe(int x, int y) {
        this.x = x;
        this.y = y;
        Random random = new Random();
        pipeGap = random.nextInt(81) + 120;
        loadPipeImages();
    }

    private void loadPipeImages() {
        try {
            completeImage = ImageIO.read(Pipe.class.getResource("flappybirdss.png"));
            pipeImage[0] = completeImage.getSubimage(56, 323, pipeWidth, pipeHeight);
            pipeImage[1] = completeImage.getSubimage(84, 323, pipeWidth, pipeHeight);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void paintImage(Graphics pen) {
        pen.drawImage(pipeImage[0], x, y, pipeWidth + 45, pipeHeight + 150, null);
        pen.drawImage(pipeImage[1], x, y + pipeHeight + pipeGap + 150, pipeWidth + 45, pipeHeight + 400, null);
    }

    public void move(int speed) {
        x -= speed;
    }

    public boolean isOffScreen() {
        return x + pipeWidth < 0;
    }
}