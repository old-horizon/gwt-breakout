package com.example.breakout.client.scenes;

import com.example.breakout.client.lib.GameColor;
import com.example.breakout.client.lib.GameInput;
import com.example.breakout.client.lib.GameLoop;
import com.example.breakout.client.lib.GameScene;
import com.example.breakout.client.lib.GameScreen;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class BreakOutScene implements GameScene {
    private static final int ROW = 10;
    private static final int COLUMN = 8;
    private final Ball ball = new Ball(315, 380);
    private final Paddle paddle = new Paddle(272, 400);
    private final List<Block> blocks = new ArrayList<>();

    public BreakOutScene() {
        for (int row = 0; row < ROW; row++) {
            for (int column = 0; column < COLUMN; column++) {
                blocks.add(new Block(Block.WIDTH * row, Block.HEIGHT * column));
            }
        }
    }

    @Override
    public void loop() {
        if (blocks.isEmpty()) {
            gameOver();
        }

        paddle.move();
        ball.move();
        if (paddle.hit(ball)) {
            ball.bounceY();
        }

        boolean isBounced = false;
        for (final Iterator<Block> i = blocks.iterator(); i.hasNext();) {
            final Block block = i.next();
            if (block.hit(ball)) {
                i.remove();
                if (!isBounced) {
                    ball.bounceY();
                    isBounced = true;
                }
            } else {
                block.draw();
            }
        }

        paddle.draw();
        ball.draw();
    }

    private static void gameOver() {
        GameLoop.setScene(new TitleScene());
    }

    private static class Block {
        private static final double WIDTH = 64;
        private static final double HEIGHT = 32;
        private static final double MARGIN = 1;
        private final double x1;
        private final double x2;
        private final double y1;
        private final double y2;

        Block(final double x1, final double y1) {
            this.x1 = x1 + MARGIN;
            this.x2 = x1 + WIDTH - MARGIN;
            this.y1 = y1 + MARGIN;
            this.y2 = y1 + HEIGHT - MARGIN;
        }

        boolean hit(final Ball ball) {
            return x1 <= ball.x2() && ball.x1() <= x2 && y1 <= ball.y2() && ball.y1() <= y2;
        }

        void draw() {
            GameScreen.drawRect(GameColor.RED, x1 , y1, x2 - x1, y2 - y1);
        }
    }

    private static class Ball {
        private static final double RADIUS = 10;
        private static final double DIAMETER = RADIUS * 2;
        private double x;
        private double y;
        private double vx = 1;
        private double vy = -3;

        Ball(final double x, final double y) {
            this.x = x;
            this.y = y;
        }

        double x1() {
            return x;
        }

        double x2() {
            return x + DIAMETER;
        }

        double y1() {
            return y;
        }

        double y2() {
            return y + DIAMETER;
        }

        void move() {
            if (x <= 0 || GameScreen.WIDTH <= x + DIAMETER) {
                bounceX();
            }
            if (y <= 0) {
                bounceY();
            }
            if (GameScreen.HEIGHT <= y) {
                BreakOutScene.gameOver();
            }
            x += vx;
            y += vy;
        }

        void bounceX() {
            vx *= -1;
        }

        void bounceY() {
            vy *= -1;
        }

        void setY(final double y) {
            this.y = y;
        }

        void draw() {
            GameScreen.drawCircle(GameColor.WHITE, x, y, RADIUS);
        }

    }

    private static class Paddle {
        private static final double WIDTH = 96;
        private static final double HEIGHT = 16;
        private static final int SPEED = 4;
        private double x1;
        private double x2;
        private double y1;
        private double y2;

        Paddle(double x1, double y1) {
            this.x1 = x1;
            this.y1 = y1;
            x2 = x1 + WIDTH;
            y2 = y1 + HEIGHT;
        }

        void move() {
            x1 += GameInput.getX() * SPEED;
            x2 = x1 + WIDTH;
            resetPos();
        }

        void draw() {
            GameScreen.drawRect(GameColor.RED, x1, y1, WIDTH, HEIGHT);
        }

        boolean hit(final Ball ball) {
            if (x1 <= ball.x2() && ball.x1() <= x2 && y1 <= ball.y2() && ball.y1() <= y2) {
                ball.setY(y1 - Ball.DIAMETER);
                return true;
            }
            return false;
        }

        private void resetPos() {
            if (x1 <= 0) {
                x1 = 0;
            }
            if (GameScreen.WIDTH <= x1 + WIDTH) {
                x1 = GameScreen.WIDTH - WIDTH;
            }
        }
    }
}
