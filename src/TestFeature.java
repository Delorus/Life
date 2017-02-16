import go.Cell;

import java.awt.*;
import java.awt.geom.Point2D;
import java.util.ArrayList;

public final class TestFeature {

    public static void main(String[] args) {
        Cell cell = new Cell(-1, new Point2D.Float(1, 1), Color.white, Color.black);
        cell.addCountLivingCell(2);
        System.out.println(cell.getCountLivingCell());

        cell.addCountLivingCell(-1);
        System.out.println(cell.getCountLivingCell());

    }
}