import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.Random;
import javax.swing.*;

public class AppPanel extends JPanel implements KeyListener {
    Timer gameTimer;
    Player1 player1 = new Player1();
    ArrayList<Pipe> pipes = new ArrayList<>();
    int birdCurrentIndex = 0;
    int pipeSpawnInterval = 200;
    int pipeSpeed = 5;
    BufferedImage[] birdImages = player1.getBirdImage();
    Random random = new Random();
    int birdY = 200;
    int birdVelocity = 0;
    final int gravity = 1;
    final int jumpStrength = -7;
    boolean gameOver = false;
    int score = 0;

    public AppPanel() {
        int panelWidth = Integer.parseInt(ResourceBundleData.getBundleData("PANEL_WIDTH"));
        int panelHeight = Integer.parseInt(ResourceBundleData.getBundleData("PANEL_HEIGHT"));
        setSize(panelWidth, panelHeight);
        initializePipes();
        startGameLoop();
        addKeyListener(this);
        setFocusable(true);
    }

    private void initializePipes() {
        for (int i = 0; i < 5; i++) {
            int x = 650 + i * pipeSpawnInterval;
            int y = random.nextInt(-150, -50);
            pipes.add(new Pipe(x, y));
        }
    }

    private void startGameLoop() {
        gameTimer = new Timer(75, e -> {
            if (!gameOver) {
                updateBird();
                movePipes();
                checkPipeSpawn();
                checkCollisions();
                checkScore();
                repaint();
            }
        });
        gameTimer.start();
    }

    private void updateBird() {
        birdVelocity += gravity;
        birdY += birdVelocity;

        if (birdY < 0) {
            birdY = 0;
            birdVelocity = 0;
        } else if (birdY > getHeight() - player1.getBirdHeight()) {
            birdY = getHeight() - player1.getBirdHeight();
            birdVelocity = 0;
        }

        birdCurrentIndex = (birdCurrentIndex + 1) % 3;
    }

    private void movePipes() {
        for (Pipe pipe : pipes) {
            pipe.move(pipeSpeed);
        }
    }

    private void checkPipeSpawn() {
        if (pipes.get(0).isOffScreen()) {
            pipes.remove(0);
            int x = pipes.get(pipes.size() - 1).x + pipeSpawnInterval;
            int y = random.nextInt(-150, -50);
            pipes.add(new Pipe(x, y));
        }
    }

    private void checkCollisions() {
        int birdWidth = 25;
        int birdHeight = 25;
        int birdX = 100;

        for (Pipe pipe : pipes) {
            if (birdX + birdWidth > pipe.x && birdX < pipe.x + pipe.pipeWidth + 45) {
                if (birdY < pipe.y + pipe.pipeHeight + 150) {
                    gameOver = true;
                    gameTimer.stop();
                    break;
                }

                if (birdY + birdHeight > pipe.y + 150 + pipe.pipeHeight + pipe.pipeGap) {
                    gameOver = true;
                    gameTimer.stop();
                    break;
                }
            }
        }
        if (birdY + birdHeight >= getHeight() || birdY <= 0) {
            gameOver = true;
            gameTimer.stop();
        }
    }


    private void checkScore() {
        int birdX = 100;
        int birdWidth = 25;

        for (Pipe pipe : pipes) {
            if (birdX > pipe.x + pipe.pipeWidth && !pipe.passed) {
                pipe.passed = true;
                score++;
            }
        }
    }

    @Override
    protected void paintComponent(Graphics pen) {
        super.paintComponent(pen);
        if (gameOver) {
            pen.setColor(Color.RED);
            pen.setFont(new Font("Arial", Font.BOLD, 50));
            pen.drawString("Game Over", getWidth() / 2 - 150, getHeight() / 2);
        } else {
            paintPlayer(pen);
            paintPipes(pen);
            paintScore(pen);
        }
    }

    private void paintPlayer(Graphics pen) {
        BufferedImage birdImage = birdImages[birdCurrentIndex];
        player1.paintImage(pen, birdImage, 100, birdY);
    }

    private void paintPipes(Graphics pen) {
        for (Pipe pipe : pipes) {
            pipe.paintImage(pen);
        }
    }

    private void paintScore(Graphics pen) {
        pen.setColor(Color.BLACK);
        pen.setFont(new Font("Arial", Font.BOLD, 30));
        pen.drawString("Score: " + score, 20, 40);
    }

    @Override
    public void keyPressed(KeyEvent e) {
        if (e.getKeyCode() == KeyEvent.VK_SPACE) {
            if (!gameOver) {
                birdVelocity = jumpStrength;
            } else {
                resetGame();
            }
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {}

    @Override
    public void keyTyped(KeyEvent e) {}

    private void resetGame() {
        birdY = 200;
        birdVelocity = 0;
        birdCurrentIndex = 0;
        score = 0;
        gameOver = false;
        pipes.clear();
        initializePipes();
        gameTimer.restart();
    }
}