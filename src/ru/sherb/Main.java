package ru.sherb;

import ru.sherb.core.Collection;
import ru.sherb.core.Game;
import ru.sherb.go.Cell;
import ru.sherb.go.Universe;

import java.awt.*;
import java.awt.geom.Point2D;
import java.util.ArrayList;
import java.util.Random;

public final class Main {
    public static final int CELL_SIZE = 10;
    public static final int UNIVERSE_SIZE = 40;
    public static final Color BACKGROUND_COLOR = Color.black;
    public static final Color FOREGROUND_COLOR = new Color(44, 209, 60);

    public static void main(String[] args) {
        final int width = 400;
        final int height = 400;
        final Game game = new Game(width, height, "Life", BACKGROUND_COLOR);

        final Universe universe = new Universe(UNIVERSE_SIZE, CELL_SIZE, BACKGROUND_COLOR, FOREGROUND_COLOR);
        game.addCollection(universe);
        universe.setCellScale(new Point2D.Float(width / UNIVERSE_SIZE / CELL_SIZE, height / UNIVERSE_SIZE / CELL_SIZE));

        final Random rnd = new Random();

//        final ru.sherb.go.Cell[][] cells = new ru.sherb.go.Cell[UNIVERSE_SIZE][UNIVERSE_SIZE];
        for (int i = 0; i < UNIVERSE_SIZE; i++) {
            for (int j = 0; j < UNIVERSE_SIZE; j++) {
                // Уникальность id обеспечивается позицией ячейки в массиве,
                // две ячейки не могут быть с одним и тем же индексом в массиве
                if (rnd.nextInt() > 0.7) {
                    universe.addCell(new Point2D.Float(i, j), true);
                }
//                cells[i][j] = new ru.sherb.go.Cell(UNIVERSE_SIZE * i + (i + j), new Point2D.Float(CELL_SIZE, CELL_SIZE));
//                cells[i][j].setPosition(new Point2D.Float(i, j));
            }
        }

        //create planer
//        universe.addCell(new Point2D.Float(2, 0), true);
//        universe.addCell(new Point2D.Float(2, 1), true);
//        universe.addCell(new Point2D.Float(2, 2), true);
//        universe.addCell(new Point2D.Float(1, 2), true);
//        universe.addCell(new Point2D.Float(0, 1), true);


        game.start();
    }
}
