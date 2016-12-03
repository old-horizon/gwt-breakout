package com.example.breakout.client;

import com.example.breakout.client.lib.GameColor;
import com.example.breakout.client.lib.GameInput;
import com.example.breakout.client.lib.GameLoop;
import com.example.breakout.client.lib.GameScreen;
import com.example.breakout.client.scenes.TitleScene;
import com.google.gwt.animation.client.AnimationScheduler;
import com.google.gwt.canvas.client.Canvas;
import com.google.gwt.canvas.dom.client.Context2d;
import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.event.dom.client.KeyCodes;
import com.google.gwt.event.dom.client.KeyDownEvent;
import com.google.gwt.event.dom.client.KeyDownHandler;
import com.google.gwt.event.dom.client.KeyUpEvent;
import com.google.gwt.event.dom.client.KeyUpHandler;
import com.google.gwt.user.client.ui.RootPanel;

public class HtmlPlayer implements EntryPoint {
    private AnimationScheduler scheduler;

    public void onModuleLoad() {
        final RootPanel root = RootPanel.get();
        final HtmlScreen screen = new HtmlScreen();
        GameScreen.setDelegate(screen);
        GameInput.setDelegate(new HtmlInput(root));
        GameLoop.setScene(new TitleScene());

        root.add(screen.canvas);
        scheduler = AnimationScheduler.get();
        scheduler.requestAnimationFrame(new AnimationScheduler.AnimationCallback() {
            @Override
            public void execute(final double timestamp) {
                GameLoop.update();
                scheduler.requestAnimationFrame(this);
            }
        });
    }

    private static class HtmlInput extends GameInput.Delegate {
        HtmlInput(final RootPanel root) {
            final Handler handler = new Handler();
            root.addDomHandler(handler, KeyDownEvent.getType());
            root.addDomHandler(handler, KeyUpEvent.getType());
        }

        private class Handler implements KeyDownHandler, KeyUpHandler {
            @Override
            public void onKeyDown(final KeyDownEvent event) {
                final int keyCode = event.getNativeKeyCode();
                if (keyCode == KeyCodes.KEY_LEFT) {
                    x = -1;
                } else if (keyCode == KeyCodes.KEY_RIGHT) {
                    x = 1;
                } else if (keyCode == KeyCodes.KEY_SPACE) {
                    spacePressed = true;
                }
            }

            @Override
            public void onKeyUp(final KeyUpEvent event) {
                reset();
            }
        }
    }

    private static class HtmlScreen implements GameScreen.Delegate {
        private static final String PIXEL = "px";
        private static final String FONT_NAME = "sans-serif";
        private final Context2d context;
        private final Canvas canvas;

        HtmlScreen() {
            canvas = Canvas.createIfSupported();
            canvas.setWidth(GameScreen.WIDTH + PIXEL);
            canvas.setCoordinateSpaceWidth(GameScreen.WIDTH);
            canvas.setHeight(GameScreen.HEIGHT + PIXEL);
            canvas.setCoordinateSpaceHeight(GameScreen.HEIGHT);
            context = canvas.getContext2d();
            context.setTextAlign("left");
            context.setTextBaseline("top");
        }

        @Override
        public void drawRect(
                final GameColor color, final double x, final double y, final double width, final double height) {
            context.beginPath();
            context.setFillStyle(toRgb(color));
            context.fillRect(x, y, width, height);
            context.closePath();
        }

        @Override
        public void drawCircle(final GameColor color, final double x, final double y, final double radius) {
            context.beginPath();
            context.arc(x + radius, y + radius, radius, 0, Math.PI * 2, false);
            context.setFillStyle(toRgb(color));
            context.fill();
            context.closePath();
        }

        @Override
        public void drawText(
                final GameColor color, final double x, final double y, final double size, final String text) {
            context.setFillStyle(toRgb(color));
            context.setFont(size + PIXEL + " " + FONT_NAME);
            context.fillText(text, x, y);
        }

        private static String toRgb(final GameColor color) {
            return "rgb(" + color.getRed() + ", " + color.getGreen() + ", " + color.getBlue() + ")";
        }
    }
}
