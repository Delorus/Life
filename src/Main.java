import core.Collection;
import core.Game;
import core.render.IRender;
import core.render.RenderCPU;
import go.Cell;
import go.Universe;

import java.awt.*;
import java.awt.geom.Point2D;
import java.util.ArrayList;

public final class Main {
    public static final int CELL_SIZE = 10;
    public static final int FIELD_SIZE = 10;
    public static final Color BACKGROUND_COLOR = Color.white;
    public static final Color FOREGROUND_COLOR = Color.black;

    public static void main(String[] args) {
        final Game game = new Game(400, 400, "Life", BACKGROUND_COLOR);

        final Collection universe = new Universe(FIELD_SIZE, CELL_SIZE, BACKGROUND_COLOR, FOREGROUND_COLOR);
        game.addCollection(universe);
//        final Random rnd = new Random();

//        final go.Cell[][] cells = new go.Cell[FIELD_SIZE][FIELD_SIZE];
//        for (int i = 0; i < FIELD_SIZE; i++) {
//            for (int j = 0; j < FIELD_SIZE; j++) {
//                // Уникальность id обеспечивается позицией ячейки в массиве,
//                // две ячейки не могут быть с одним и тем же индексом в массиве
//                cells[i][j] = new go.Cell(FIELD_SIZE * i + (i + j), new Point2D.Float(CELL_SIZE, CELL_SIZE));
//                cells[i][j].setPosition(new Point2D.Float(i, j));
//            }
//        }

        ArrayList<Cell> cells = new ArrayList<>();
        cells.add(new Cell(FIELD_SIZE * 2 + (2 + 4), new Point2D.Float(CELL_SIZE, CELL_SIZE), FOREGROUND_COLOR, BACKGROUND_COLOR));
        cells.get(0).setPosition(new Point2D.Float(2, 4));
        cells.get(0).setLive(true);
        cells.add(new Cell(1, new Point2D.Float(CELL_SIZE, CELL_SIZE), FOREGROUND_COLOR, BACKGROUND_COLOR));
        cells.add(new Cell(2, new Point2D.Float(CELL_SIZE, CELL_SIZE), FOREGROUND_COLOR, BACKGROUND_COLOR));
        cells.add(new Cell(3, new Point2D.Float(CELL_SIZE, CELL_SIZE), FOREGROUND_COLOR, BACKGROUND_COLOR));

        game.start();
    }
}
