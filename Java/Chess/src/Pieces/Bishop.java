package Pieces;

import Main.Path;
import Main.Square;

/**
 * Created by Jacob Zamora on 2/22/2016.
 */
public class Bishop extends Piece {
    public Bishop()
    {
        super();
    }
    public Bishop(String id, Square position)
    {
        super(id, position);
    }

    public void generateMoves()
    {
        super.generateMoves();
        generateDiagonalPaths();
    }
}
