package Pieces;

import Main.Path;
import Main.Square;
import Main.Vector2D;

/**
 * Created by Jacob Zamora on 2/22/2016.
 */
public class Knight extends Piece {

    private Vector2D[] _moveVectors = { new Vector2D(1, 2), new Vector2D(2, 1), new Vector2D(1, -2), new Vector2D(2, -1), new Vector2D(-1, 2), new Vector2D(-2, 1), new Vector2D(-1, -2), new Vector2D(-2, -1) };
    public Knight()
    {
        super();
    }
    public Knight(String id, Square position)
    {
        super(id, position);
    }

    public void generateMoves()
    {
        super.generateMoves();
        generatePathFromVectors(_moveVectors);
    }
}
