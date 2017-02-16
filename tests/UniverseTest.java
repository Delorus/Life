import go.Cell;
import go.Universe;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.awt.*;
import java.awt.geom.Point2D;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static go.Universe.*;

/**
 * Created by sherb on 06.02.2017.
 */
class UniverseTest extends Universe {

    public UniverseTest() {
        super(Main.FIELD_SIZE, Main.CELL_SIZE, Main.BACKGROUND_COLOR, Main.FOREGROUND_COLOR);
    }

    @Test
    void testCreateAura() {
        final Cell testCell = new Cell(Main.FIELD_SIZE * 2 + (2 + 4),
                new Point2D.Float(Main.CELL_SIZE, Main.CELL_SIZE),
                Main.FOREGROUND_COLOR,
                Main.BACKGROUND_COLOR);
        testCell.setPosition(new Point2D.Float(2, 4));

        final List<Cell> aura = createAura(testCell);
        // В окресности выбраной клетки не должно быть этой самой клетки
        assertFalse(aura.contains(testCell));

        final int x = (int) testCell.getPosition().getX();
        final int y = (int) testCell.getPosition().getY();
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
            final Cell buf = new Cell((int) (Main.FIELD_SIZE * pos.x + (pos.x + pos.y)),
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
    void testInsertAuraToVisualObjects() {
        final Cell testCell = new Cell(Main.FIELD_SIZE * 2 + (2 + 4),
                new Point2D.Float(Main.CELL_SIZE, Main.CELL_SIZE),
                Main.FOREGROUND_COLOR,
                Main.BACKGROUND_COLOR);
        testCell.setPosition(new Point2D.Float(2, 4));
        testCell.setLive(true);
        addVisualObject(testCell);

        final List<Cell> aura = createAura(testCell);

        insertAuraToVisualObjects(aura);

        assertEquals(9, getVisualObjects().size());


        final Cell testCell2 = new Cell(Main.FIELD_SIZE * 3 + (3 + 5),
                new Point2D.Float(Main.CELL_SIZE, Main.CELL_SIZE),
                Main.FOREGROUND_COLOR,
                Main.BACKGROUND_COLOR);
        testCell2.setPosition(new Point2D.Float(3, 5));
        testCell2.setLive(true);
        addVisualObject(testCell2);

        insertAuraToVisualObjects(createAura(testCell2));

        assertTrue(testCell.isLive());
        assertTrue(testCell2.isLive());

        //TODO доделать проверку

    }

    @Test
    void testUpdate() {
        final Cell testCell = new Cell(Main.FIELD_SIZE * 2 + (2 + 4),
                new Point2D.Float(Main.CELL_SIZE, Main.CELL_SIZE),
                Main.FOREGROUND_COLOR,
                Main.BACKGROUND_COLOR);
        testCell.setPosition(new Point2D.Float(2, 4));
        testCell.setLive(true);
        addVisualObject(testCell);

        final Cell testCell2 = new Cell(Main.FIELD_SIZE * 3 + (3 + 5),
                new Point2D.Float(Main.CELL_SIZE, Main.CELL_SIZE),
                Main.FOREGROUND_COLOR,
                Main.BACKGROUND_COLOR);
        testCell2.setPosition(new Point2D.Float(3, 5));
        testCell2.setLive(true);
        addVisualObject(testCell2);

        final Cell testCell3 = new Cell(Main.FIELD_SIZE * 2 + (2 + 6),
                new Point2D.Float(Main.CELL_SIZE, Main.CELL_SIZE),
                Main.FOREGROUND_COLOR,
                Main.BACKGROUND_COLOR);
        testCell3.setPosition(new Point2D.Float(2, 6));
        testCell3.setLive(true);
        addVisualObject(testCell3);

        init();

        update(0);
    }


}
