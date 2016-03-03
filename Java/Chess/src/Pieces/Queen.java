package Pieces;

import Main.Square;

/**
 * Created by Jacob Zamora on 2/23/2016.
 */
public class Queen extends Piece {
    public Queen()
    {
        super();
    }
    public Queen(String id, Square position)
    {
        super(id, position);
    }

    public void generateMoves()
    {
        super.generateMoves();
        generateDiagonalPaths();
        generateAdjacentPaths();
    }
}
