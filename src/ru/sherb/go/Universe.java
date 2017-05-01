package ru.sherb.go;

import ru.sherb.core.Collection;

import java.awt.*;
import java.awt.geom.Point2D;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Universe extends Collection<Cell> {
    private final int size;
    private final int cellSize;
    private final Color color;
    private final Color cellColor;

    private Point2D.Float cellScale;

    public Universe(int universeSize, int cellSize, Color universeColor, Color cellColor) {
        this.size = universeSize;
        this.cellSize = cellSize;
        this.color = universeColor;
        this.cellColor = cellColor;
    }

    public void setCellScale(Point2D.Float scale) {
        this.cellScale = scale;

        getVisualObjects().forEach(cell -> cell.setScale(cellScale));
    }

    /**
     * Создает "мертвые" клетки в окресностях выбранной
     *
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

                final Cell buf = new Cell(
                        new Point2D.Float(i, j),
                        new Point2D.Float(cellSize, cellSize),
                        cellColor,
//                        Color.RED
                        color
                );
                if (cellScale != null) {
                    buf.setScale(cellScale);
                }

                if (cell.equals(buf)) continue;

                buf.setCountLivingCell(1);
                buf.setLive(false);
                aura.add(buf);
            }
        }

        return aura;
    }

    /**
     * Добавление ауры к визуальным объектам, при этом, если такой объект уже существует, то у него увеличивается счетчик живых клеток
     *
     * @param aura коллекция "мертвых" клеток, окружающих одну живую
     */
    protected void insertAuraToVisualObjects(final List<Cell> aura) { //TODO добавить вызов метода только из метода createAura и удалить из всех других

        for (Cell cell : aura) {
            final int i;
            if ((i = getVisualObjects().indexOf(cell)) != -1) {
                getVisualObjects().get(i).addCountLivingCell(1);
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

                final Cell buf = getCell(i, j);

                if (removedCell.equals(buf)) continue;

                getCell(i, j).addCountLivingCell(-1);
            }
        }
    }

    /**
     * Просчитывает позиции всех существующих клеток и изменяет их состояние по следующим правилам:
     * <ul>
     * <li>Если у живой клетки меньше двух соседей или больше трех, то клетка становится мертвой</li>
     * <li>Если у мертвой клетки ровно три соседа, то клетка становится живой</li>
     * </ul>
     *
     * @return список всех клеток из нового поколения
     */
    protected List<Cell> nextGeneration() {

        final ArrayList<Cell> removedCells = new ArrayList<>();
        final ArrayList<Cell> createdCells = new ArrayList<>();
        for (Cell cell : getVisualObjects()) {

            if (cell.isLive()) {
                if (cell.getCountLivingCell() < 2 || cell.getCountLivingCell() > 3) {
                    removedCells.add(cell);
                }
            } else {
                if (cell.getCountLivingCell() == 3) {
                    createdCells.add(cell);
                }
            }
        }

        removedCells.forEach(cell -> {
            cell.setLive(false);
            removeAuraToVisualObject(cell);
        });
        createdCells.forEach(cell -> {
            cell.setLive(true);
            insertAuraToVisualObjects(createAura(cell));
        });
        return getVisualObjects();
    }

    protected Cell getCell(int x, int y) {
        return getVisualObjects().get(getVisualObjects().indexOf(new Cell(
                new Point2D.Float(x, y),
                new Point2D.Float(cellSize, cellSize),
                cellColor,
                color
        )));
    }

    protected Cell getCell(Point2D.Float pos) {
        return getCell((int) pos.x, (int) pos.y);
    }

    protected List<Cell> getAllCell() {
        return getVisualObjects();
    }

    public Cell addCell(Point2D.Float pos, boolean live) {
        final Cell cell = new Cell(
                pos,
                new Point2D.Float(cellSize, cellSize),
                cellColor,
//                Color.RED
                color
        );
        cell.setLive(live);
        if (cellScale != null) {
            cell.setScale(cellScale);
        }

        if (getVisualObjects().contains(cell)) {
            getCell(pos).setLive(live);
            //TODO возможно добавить вставку или удаление ауры
            return getCell(pos);
        }

        return super.addVisualObject(cell) ? cell : null;
    }

    public int getCellCount() {
        return getVisualObjects().size();
    }

    @Override
    public void init() {
        super.init();

        if (getVisualObjects() != null) {
            final List<Cell> cells = new ArrayList<>(getVisualObjects());

            for (Cell vo : cells) {

                if (vo.isLive()) {
                    insertAuraToVisualObjects(createAura(vo));
                }
            }
        }
    }

    @Override
    public void update(float dt) {
//        getVisualObjects()
//                .stream()
//                .filter(Cell::isLive)
//                .forEach(System.out::println);
//
//        final char[] split = new char[100];
//        Arrays.fill(split,  '-');
//        System.out.println(new String(split));

        super.update(dt);

        nextGeneration();
    }
}
