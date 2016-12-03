package com.example.breakout.client.lib;

public final class GameInput {
    private static Delegate delegate;

    private GameInput() {
    }

    public static void setDelegate(Delegate delegate) {
        GameInput.delegate = delegate;
    }

    public static int getX() {
        return delegate.getX();
    }

    public static boolean isSpacePressed() {
        return delegate.isSpacePressed();
    }

    public static void reset() {
        delegate.reset();
    }

    public static abstract class Delegate {
        protected static int x;
        protected static boolean spacePressed;

        final int getX() {
            return x;
        }

        final boolean isSpacePressed() {
            return spacePressed;
        }

        protected final void reset() {
            x = 0;
            spacePressed = false;
        }
    }
}
