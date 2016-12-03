package com.example.breakout.client.lib;

public final class GameLoop {
    private static GameScene scene;

    public static void setScene(final GameScene scene) {
        GameInput.reset();
        GameLoop.scene = scene;
    }

    public static void update() {
        GameScreen.clear();
        scene.loop();
    }
}
