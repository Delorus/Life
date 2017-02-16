package go;

import core.GameObject;
import core.VisualObject;

import java.awt.*;
import java.awt.geom.Point2D;
import java.util.ArrayList;
import java.util.List;

public class Cell extends VisualObject {
    private final Color liveColor;
    private final Color deadColor;
    private boolean live;
    private int countLivingCell;


    public Cell(int id, Point2D.Float size, Color liveColor, Color deadColor) {
        super(id, size);
        this.liveColor = liveColor;
        this.deadColor = deadColor;
    }

    public void setLive(boolean live) {
        this.live = live;
        setColor(live ? liveColor : deadColor);
        setShouldBeRender(true);
    }

    public void setCountLivingCell(int countLivingCell) {
        this.countLivingCell = countLivingCell;
    }

    public void addCountLivingCell(int countLivingCell) {
        this.countLivingCell += countLivingCell;
    }

    public int getCountLivingCell() {
        return countLivingCell;
    }

    public boolean isLive() {
        return live;
    }

    @Override
    public void init() {
//        live = true;
        setShouldBeRender(true);
    }

    @Override
    public void update(float dt) {

    }

    @Override
    public void end() {
//        live = false;
    }
}
