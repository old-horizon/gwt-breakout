package com.example.breakout.client.scenes;

import com.example.breakout.client.lib.GameColor;
import com.example.breakout.client.lib.GameInput;
import com.example.breakout.client.lib.GameLoop;
import com.example.breakout.client.lib.GameScene;
import com.example.breakout.client.lib.GameScreen;

public class TitleScene implements GameScene {
    @Override
    public void loop() {
        GameScreen.drawText(GameColor.WHITE, 128, 128, 48, "PRESS SPACE KEY");
        if (GameInput.isSpacePressed()) {
            GameLoop.setScene(new BreakOutScene());
        }
    }

}
