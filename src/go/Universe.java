package go;

import core.Collection;
import core.Game;
import core.GameObject;
import core.VisualObject;

import java.awt.*;
import java.awt.geom.Point2D;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class Universe extends Collection {
    private final int size;
    private final int cellSize;
    private final Color color;
    private final Color cellColor;

    public Universe(int universeSize, int cellSize, Color universeColor, Color cellColor) {
        this.size = universeSize;
        this.cellSize = cellSize;
        this.color = universeColor;
        this.cellColor = cellColor;
    }

    /**
     * Создает "мертвые" клетки в окресностях выбранной
     * @param cell клетка, возле которой нужно создать мертвые клетки
     * @return 8 объектов типа {@code Cell}
     */
    protected List<Cell> createAura(final Cell cell) {
        final ArrayList<Cell> aura = new ArrayList<>();

        final int x = (int) cell.getPosition().x;
        final int y = (int) cell.getPosition().y;

        for (int i = x - 1; i <= x + 1; i++) {
            for (int j = y - 1; j <= y + 1; j++) {
                if (i < 0 || i > size || j < 0 || j > size) continue;

                final Cell buf = new Cell(size * i + (i + j),
                        new Point2D.Float(cellSize, cellSize),
                        cellColor,
                        color);

                if (cell.equals(buf)) continue;

                buf.setPosition(new Point2D.Float(i, j));
                buf.setCountLivingCell(1);
                buf.setLive(false);
                aura.add(buf);
            }
        }

        return aura;
    }

    /**
     * Добавление ауры к визуальным объектам, при этом, если такой объект уже существует, то у него увеличивается счетчик живых клеток
     * @param aura коллекция "мертвых" клеток, окружающих одну живую
     */
    protected void insertAuraToVisualObjects(final List<Cell> aura) {

        for (Cell cell : aura) {
            final int i;
            if ((i = getVisualObjects().indexOf(cell)) != -1) {
                ((Cell) getVisualObjects().get(i)).addCountLivingCell(1);
            }
        }

        aura.removeIf(cell -> getVisualObjects().contains(cell));

        for (Cell cell : aura) {
            addVisualObject(cell);
        }
    }

    protected void removeAuraToVisualObject(final Cell removedCell) {
        final int x = (int) removedCell.getPosition().x;
        final int y = (int) removedCell.getPosition().y;

        for (int i = x - 1; i <= x + 1; i++) {
            for (int j = y - 1; j <= y + 1; j++) {
                if (i < 0 || i > size || j < 0 || j > size) continue;

                final Cell buf = new Cell(size * i + (i + j),
                        new Point2D.Float(cellSize, cellSize),
                        cellColor,
                        color);

                ((Cell) getVisualObjects().get(getVisualObjects().indexOf(buf))).addCountLivingCell(-1);
            }
        }
    }

    protected List<VisualObject> nextGeneration() {
        for (VisualObject visualObject : getVisualObjects()) {
            final Cell cell = (Cell) visualObject;

            if (cell.isLive()) {
                if (cell.getCountLivingCell() < 2 && cell.getCountLivingCell() > 3) {
                    cell.setLive(false);
                    removeAuraToVisualObject(cell);
                }
            } else {
                if (cell.getCountLivingCell() == 3) {
                    cell.setLive(true);
                    insertAuraToVisualObjects(createAura(cell));
                }
            }
        }

        return getVisualObjects();
    }

    @Override
    public boolean addVisualObject(VisualObject visualObject) {
        if (! (visualObject instanceof Cell)) return false;

        if (! super.addVisualObject(visualObject)) {
            ((Cell) getVisualObjects().get(getVisualObjects().indexOf(visualObject))).setLive(((Cell) visualObject).isLive());
        }

        return true;
    }

    @Override
    public void init() {
        super.init();

        if (getVisualObjects() != null) {
            final List<VisualObject> cells = new ArrayList<>(getVisualObjects());

            for (VisualObject vo : cells) {
                final Cell cell = (Cell) vo;

                if (cell.isLive()) {
                    insertAuraToVisualObjects(createAura(cell));
                }
            }
        }
    }

    @Override
    public void update(float dt) {
        super.update(dt);

        nextGeneration();
    }
}
