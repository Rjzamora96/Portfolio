package Pieces;

import Main.*;

import java.util.ArrayList;

/**
 * Created by Jacob Zamora on 2/22/2016.
 */
public class Pawn extends Piece {

    public Pawn() {super();}
    public Pawn(String id, Square position)
    {
        super(id, position);
    }

    public void generateMoves()
    {
        super.generateMoves();
        if(_id.equals("PL"))
        {
            Path path = new Path(this);
            if(!_owner.checkSquare(Vector2D.parseVector(_position.get_up().get_position()))) path.addSquare(_position.get_up());
            _moveList.add(path);
            if(!_hasMoved && !_owner.checkSquare(Vector2D.parseVector(_position.get_up().get_up().get_position())))
            {
                Path situationalPath = new Path(this);
                situationalPath.addSquare(_position.get_up());
                situationalPath.addSquare(_position.get_up().get_up());
                _moveList.add(situationalPath);
            }
            if(_position.get_up() != null)
            {
                if(_position.get_up().get_left() != null)
                {
                    Square targetSquare = _owner.getSquare(Vector2D.parseVector(_position.get_up().get_left().get_position()));
                    if(targetSquare != null && targetSquare.get_occupant() != null)
                    {
                        Path capturePath = new Path(this);
                        capturePath.addSquare(targetSquare);
                        _moveList.add(capturePath);
                    }
                }
                if(_position.get_up().get_right() != null)
                {
                    Square targetSquare = _owner.getSquare(Vector2D.parseVector(_position.get_up().get_right().get_position()));
                    if(targetSquare != null && targetSquare.get_occupant() != null)
                    {
                        Path capturePath = new Path(this);
                        capturePath.addSquare(targetSquare);
                        _moveList.add(capturePath);
                    }
                }
            }
        }
        else if(_id.equals("PD"))
        {
            Path path = new Path(this);
            if(!_owner.checkSquare(Vector2D.parseVector(_position.get_down().get_position())))path.addSquare(_position.get_down());
            _moveList.add(path);
            if(!_hasMoved && !_owner.checkSquare(Vector2D.parseVector(_position.get_down().get_down().get_position())))
            {
                Path situationalPath = new Path(this);
                situationalPath.addSquare(_position.get_down());
                situationalPath.addSquare(_position.get_down().get_down());
                _moveList.add(situationalPath);
            }
            if(_position.get_down() != null)
            {
                if(_position.get_down().get_left() != null)
                {
                    Square targetSquare = _owner.getSquare(Vector2D.parseVector(_position.get_down().get_left().get_position()));
                    if(targetSquare != null && targetSquare.get_occupant() != null)
                    {
                        Path capturePath = new Path(this);
                        capturePath.addSquare(targetSquare);
                        _moveList.add(capturePath);
                    }
                }
                if(_position.get_down().get_right() != null)
                {
                    Square targetSquare = _owner.getSquare(Vector2D.parseVector(_position.get_down().get_right().get_position()));
                    if(targetSquare != null && targetSquare.get_occupant() != null)
                    {
                        Path capturePath = new Path(this);
                        capturePath.addSquare(targetSquare);
                        _moveList.add(capturePath);
                    }
                }
            }
        }
    }
}
