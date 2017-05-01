package ru.sherb.go;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ru.sherb.Main;

import java.awt.geom.Point2D;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;


class UniverseTest {
    Universe universe;

    @BeforeEach
    void setUp() {
        universe = new Universe(Main.UNIVERSE_SIZE, Main.CELL_SIZE, Main.BACKGROUND_COLOR, Main.FOREGROUND_COLOR);
    }

    @Test
    void createAura() {

        final Cell testCell = universe.addCell(new Point2D.Float(2, 4), true);

        final List<Cell> aura = universe.createAura(testCell);
        // В окресности выбраной клетки не должно быть этой самой клетки
        assertFalse(aura.contains(testCell));

        final float x = testCell.getPosition().x;
        final float y = testCell.getPosition().y;
        final Point2D.Float[] testPosition = new Point2D.Float[]{
                new Point2D.Float(x - 1, y - 1),
                new Point2D.Float(x - 1, y),
                new Point2D.Float(x - 1, y + 1),
                new Point2D.Float(x, y - 1),
                new Point2D.Float(x, y + 1),
                new Point2D.Float(x + 1, y - 1),
                new Point2D.Float(x + 1, y),
                new Point2D.Float(x + 1, y + 1)
        };
        assertEquals(testPosition.length, aura.size());

        for (Point2D.Float pos : testPosition) {
            final Cell buf = new Cell(
                    pos,
                    new Point2D.Float(Main.CELL_SIZE, Main.CELL_SIZE),
                    Main.FOREGROUND_COLOR,
                    Main.BACKGROUND_COLOR);
            assertTrue(aura.contains(buf));
        }

        for (Cell cell : aura) {
            assertFalse(cell.isLive());
            assertEquals(1, cell.getCountLivingCell());
        }
    }

    @Test
    void insertAuraToVisualObjects() {
         /*
         В круглых скобках изображены тестовые клетки, числа обозначают число соседей рядом с клеткой
        _| 0 | 1 | 2 | 3 | 4 | 5 | 6 |y
        0|   |   |   |   |   |   |   |
        1|   |   |   | 1 | 1 | 1 |   |
        2|   |   |   | 1 |(1)| 2 | 1 |
        3|   |   |   | 1 | 2 |(1)| 1 |
        4|   |   |   |   | 1 | 1 | 1 |
        x
         */

        final Cell testCell = universe.addCell(new Point2D.Float(2, 4), true);

        universe.insertAuraToVisualObjects(universe.createAura(testCell));

        assertEquals(8 + 1, universe.getCellCount());

        final Cell testCell2 = universe.addCell(new Point2D.Float(3, 5), true);

        universe.insertAuraToVisualObjects(universe.createAura(testCell2));

        assertEquals(8 + 8 - 2, universe.getCellCount());

        assertTrue(testCell.isLive());
        assertTrue(testCell2.isLive());

        assertEquals(1, universe.getCell(1, 3).getCountLivingCell());
        assertEquals(1, universe.getCell(1, 4).getCountLivingCell());
        assertEquals(1, universe.getCell(1, 5).getCountLivingCell());

        assertEquals(1, universe.getCell(2, 3).getCountLivingCell());
        assertEquals(1, universe.getCell(2, 4).getCountLivingCell());
        assertEquals(2, universe.getCell(2, 5).getCountLivingCell());
        assertEquals(1, universe.getCell(2, 6).getCountLivingCell());

        assertEquals(1, universe.getCell(3, 3).getCountLivingCell());
        assertEquals(2, universe.getCell(3, 4).getCountLivingCell());
        assertEquals(1, universe.getCell(3, 5).getCountLivingCell());
        assertEquals(1, universe.getCell(3, 6).getCountLivingCell());

        assertEquals(1, universe.getCell(4, 4).getCountLivingCell());
        assertEquals(1, universe.getCell(4, 5).getCountLivingCell());
        assertEquals(1, universe.getCell(4, 6).getCountLivingCell());

    }

    @Test
    void removeAuraToVisualObject() {
        /*
         В круглых скобках изображены тестовые клетки, числа обозначают число соседей рядом с клеткой
        _| 0 | 1 | 2 | 3 | 4 | 5 | 6 |y
        0|   |   |   |   |   |   |   |
        1|   |   |   | 1 | 1 | 1 |   |
        2|   |   |   | 1 |(1)| 2 | 1 |
        3|   |   |   | 1 | 2 |(1)| 1 |
        4|   |   |   |   | 1 | 1 | 1 |
        x
        После удаления клетки (3, 5):
        _| 0 | 1 | 2 | 3 | 4 | 5 | 6 |y
        0|   |   |   |   |   |   |   |
        1|   |   |   | 1 | 1 | 1 |   |
        2|   |   |   | 1 |(0)| 1 | 0 |
        3|   |   |   | 1 | 1 | 1 | 0 |
        4|   |   |   |   | 0 | 0 | 0 |
        x
         */


        final Cell testCell = universe.addCell(new Point2D.Float(2, 4), true);
        universe.insertAuraToVisualObjects(universe.createAura(testCell));


        final Cell testCell2 = universe.addCell(new Point2D.Float(3, 5), true);
        universe.insertAuraToVisualObjects(universe.createAura(testCell2));

        universe.removeAuraToVisualObject(testCell2);

        assertEquals(1, universe.getCell(1, 3).getCountLivingCell());
        assertEquals(1, universe.getCell(1, 4).getCountLivingCell());
        assertEquals(1, universe.getCell(1, 5).getCountLivingCell());

        assertEquals(1, universe.getCell(2, 3).getCountLivingCell());
        assertEquals(0, universe.getCell(2, 4).getCountLivingCell());
        assertEquals(1, universe.getCell(2, 5).getCountLivingCell());
        assertEquals(0, universe.getCell(2, 6).getCountLivingCell());

        assertEquals(1, universe.getCell(3, 3).getCountLivingCell());
        assertEquals(1, universe.getCell(3, 4).getCountLivingCell());
        assertEquals(1, universe.getCell(3, 5).getCountLivingCell());
        assertEquals(0, universe.getCell(3, 6).getCountLivingCell());

        assertEquals(0, universe.getCell(4, 4).getCountLivingCell());
        assertEquals(0, universe.getCell(4, 5).getCountLivingCell());
        assertEquals(0, universe.getCell(4, 6).getCountLivingCell());
    }

    @Test
    void update() {
        /*
        Проверяется обновление вселенной из трех клеток:
        _| 0 | 1 | 2 | 3 | 4 | 5 | 6 | 7 |y
        0|   |   |   |   |   |   |   |   |
        1|   |   |   | 1 | 1 | 2 | 1 | 1 |
        2|   |   |   | 1 |(1)| 3 |(1)| 1 |
        3|   |   |   | 1 | 2 |(2)| 2 | 1 |
        4|   |   |   |   | 1 | 1 | 1 |   |
        x

        После обновления вселенная должна выглядить так:
        _| 0 | 1 | 2 | 3 | 4 | 5 | 6 | 7 |y
        0|   |   |   |   |   |   |   |   |
        1|   |   |   | 0 | 1 | 1 | 1 | 0 |
        2|   |   |   | 0 | 2 |(1)| 2 | 0 |
        3|   |   |   | 0 | 2 |(1)| 2 | 0 |
        4|   |   |   |   | 1 | 1 | 1 |   |
        x

        После второго обновления не должно остатья ни единой живой клетки
         */


        final Cell testCell = universe.addCell(new Point2D.Float(2, 4), true);

        final Cell testCell2 = universe.addCell(new Point2D.Float(3, 5), true);

        final Cell testCell3 = universe.addCell(new Point2D.Float(2, 6), true);

        universe.init();

        universe.update(0);

        assertFalse(testCell.isLive());
        assertTrue(testCell2.isLive());
        assertFalse(testCell3.isLive());
        assertTrue(universe.getCell(2, 5).isLive());

        assertEquals(0, universe.getCell(1, 3).getCountLivingCell());
        assertEquals(1, universe.getCell(1, 4).getCountLivingCell());
        assertEquals(1, universe.getCell(1, 5).getCountLivingCell());
        assertEquals(1, universe.getCell(1, 6).getCountLivingCell());
        assertEquals(0, universe.getCell(1, 7).getCountLivingCell());

        assertEquals(0, universe.getCell(2, 3).getCountLivingCell());
        assertEquals(2, universe.getCell(2, 4).getCountLivingCell());
        assertEquals(1, universe.getCell(2, 5).getCountLivingCell());
        assertEquals(2, universe.getCell(2, 6).getCountLivingCell());
        assertEquals(0, universe.getCell(2, 7).getCountLivingCell());

        assertEquals(0, universe.getCell(3, 3).getCountLivingCell());
        assertEquals(2, universe.getCell(3, 4).getCountLivingCell());
        assertEquals(1, universe.getCell(3, 5).getCountLivingCell());
        assertEquals(2, universe.getCell(3, 6).getCountLivingCell());
        assertEquals(0, universe.getCell(3, 7).getCountLivingCell());

        assertEquals(1, universe.getCell(4, 4).getCountLivingCell());
        assertEquals(1, universe.getCell(4, 5).getCountLivingCell());
        assertEquals(1, universe.getCell(4, 6).getCountLivingCell());

        universe.update(0);

        assertTrue(universe
                .getAllCell()
                .stream()
                .noneMatch(Cell::isLive)
        );


    }
}
