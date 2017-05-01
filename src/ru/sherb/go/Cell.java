package ru.sherb.go;

import ru.sherb.core.VisualObject;

import java.awt.*;
import java.awt.geom.Point2D;

public class Cell extends VisualObject {
    private final Color liveColor;
    private final Color deadColor;
    private boolean live;
    private int countLivingCell;


    public Cell(Point2D.Float pos, Point2D.Float size, Color liveColor, Color deadColor) {
        super(size);
        this.liveColor = liveColor;
        this.deadColor = deadColor;
        setPosition(pos);
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
        //passed
        //TODO переместить сюда логику умирания и оживления клеток из метода Universe.nextGeneration
    }

    @Override
    public void end() {
//        live = false;
    }

    @Override
    public boolean equals(Object o) {
        if (super.equals(o)) return true;

        if (o == null || getClass() != o.getClass()) return false;

        final Cell that = (Cell) o;

        return that.getPosition().x == this.getPosition().x && that.getPosition().y == this.getPosition().y;
    }

    @Override
    public int hashCode() {
        int result = super.hashCode();
        result = 31 * result + (int) getPosition().x;
        result = 31 * result + (int) getPosition().y;
        return result;
    }

    @Override
    public String toString() {
        return "Cell{" +
                "live=" + live +
                ", countLivingCell=" + countLivingCell +
                ", position=" + getPosition() +
                '}';
    }
}
