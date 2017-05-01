package ru.sherb.core.render;

import ru.sherb.core.VisualObject;

import java.awt.*;

public class RenderCPU implements IRender {
    /**
     * Графический контекст для рисования
     */
    private final Canvas canvas;
    private final Color backgroundColor;

    public RenderCPU(Canvas canvas, Color background) {
        this.canvas = canvas;
        this.backgroundColor = background;
    }

    @Override
    public void init() {
        //TODO заглушка
    }

    @Override
    public void paint(VisualObject... visualObjects) {
        //TODO заглушка
    }
}
