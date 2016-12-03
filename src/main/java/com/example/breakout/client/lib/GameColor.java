package com.example.breakout.client.lib;

public class GameColor {
    public static final GameColor BLACK = new GameColor(0, 0, 0);
    public static final GameColor WHITE = new GameColor(255, 255, 255);
    public static final GameColor RED = new GameColor(255, 0, 0);
    private final int red;
    private final int green;
    private final int blue;

    public GameColor(final int red, final int green, final int blue) {
        this.red = red;
        this.green = green;
        this.blue = blue;
    }

    public int getRed() {
        return red;
    }

    public int getGreen() {
        return green;
    }

    public int getBlue() {
        return blue;
    }
}
