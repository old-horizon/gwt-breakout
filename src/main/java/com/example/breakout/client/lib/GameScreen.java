package com.example.breakout.client.lib;

public final class GameScreen {

    public static final int WIDTH = 640;
    public static final int HEIGHT = 480;
    public static final GameColor BACKGROUND_COLOR = GameColor.BLACK;

    private static Delegate delegate;

    private GameScreen() {
    }

    public static void setDelegate(final Delegate delegate) {
        GameScreen.delegate = delegate;
    }

    public static void clear() {
        delegate.drawRect(BACKGROUND_COLOR, 0, 0, WIDTH, HEIGHT);
    }

    public static void drawRect(
            final GameColor color, final double x, final double y, final double width, final double height) {
        delegate.drawRect(color, x, y, width, height);

    }

    public static void drawCircle(final GameColor color, final double x, final double y, final double radius) {
        delegate.drawCircle(color, x, y, radius);
    }

    public static void drawText(
            final GameColor color, final double x, final double y, final double size, final String text) {
        delegate.drawText(color, x, y, size, text);
    }

    public interface Delegate {
        void drawRect(GameColor color, double x, double y, double width, double height);
        void drawCircle(GameColor color, double x, double y, double radius);
        void drawText(GameColor color, double x, double y, double size, String text);
    }
}
