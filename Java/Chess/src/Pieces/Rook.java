package Pieces;

import Main.Path;
import Main.Square;
import Main.Vector2D;

/**
 * Created by Jacob Zamora on 2/22/2016.
 */
public class Rook extends Piece {
    public Rook()
    {
        super();
    }
    public Rook(String id, Square position)
    {
        super(id, position);
    }

    public void generateMoves()
    {
        super.generateMoves();
        generateAdjacentPaths();
        if(!_hasMoved && _owner.is_castling())
        {
            if(_id.charAt(1) == 'L')
            {
                if(!_owner.checkSquare("B1"))
                {
                    Path castlePath = new Path(this);
                    castlePath.addSquare(_owner.getSquare("D1"));
                    _moveList.add(castlePath);
                }
                Path castlePath = new Path(this);
                castlePath.addSquare(_owner.getSquare("F1"));
                _moveList.add(castlePath);
            }
            else
            {
                if(!_owner.checkSquare("B8"))
                {
                    Path castlePath = new Path(this);
                    castlePath.addSquare(_owner.getSquare("D8"));
                    _moveList.add(castlePath);
                }
                Path castlePath = new Path(this);
                castlePath.addSquare(_owner.getSquare("F8"));
                _moveList.add(castlePath);
            }
        }
    }
}
