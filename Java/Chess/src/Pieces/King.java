package Pieces;

import Main.*;

import java.util.ArrayList;
import java.util.Arrays;

/**
 * Created by Jacob Zamora on 2/23/2016.
 */
public class King extends Piece {

    private Vector2D[] _moveVectors =  { new Vector2D(1, 0), new Vector2D(-1, 0), new Vector2D(1, 1), new Vector2D(-1, -1), new Vector2D(0, 1), new Vector2D(0, -1), new Vector2D(-1, 1), new Vector2D(1, -1) };
    public King()
    {
        super();
    }
    public King(String id, Square position)
    {
        super(id, position);
    }

    public Command[] generateCommandList()
    {
        ArrayList<Command> commands = new ArrayList<>(Arrays.asList(super.generateCommandList()));
        if(!_hasMoved)
        {
            if(_id.charAt(1) == 'L')
            {
                if(!_owner.checkSquare("F1") && !_owner.checkSquare("G1"))
                {
                    Piece rook = _owner.getSquare("H1").get_occupant();
                    if(rook != null && rook.get_id().equals("RL") && !rook.is_hasMoved())
                    {
                        commands.add(new Command("E1G1", "H1F1", CommandType.Castle));
                    }
                }
                if(!_owner.checkSquare("D1") && !_owner.checkSquare("C1") && !_owner.checkSquare("B1"))
                {
                    Piece rook = _owner.getSquare("A1").get_occupant();
                    if(rook != null && rook.get_id().equals("RL") && !rook.is_hasMoved())
                    {
                        commands.add(new Command("E1C1", "A1D1", CommandType.Castle));
                    }
                }
            }
            else
            {
                if(!_owner.checkSquare("F8") && !_owner.checkSquare("G8"))
                {
                    Piece rook = _owner.getSquare("H8").get_occupant();
                    if(rook != null && rook.get_id().equals("RD") && !rook.is_hasMoved())
                    {
                        commands.add(new Command("E8G8", "H8F8", CommandType.Castle));
                    }
                }
                if(!_owner.checkSquare("D8") && !_owner.checkSquare("C8") && !_owner.checkSquare("B8"))
                {
                    Piece rook = _owner.getSquare("A8").get_occupant();
                    if(rook != null && rook.get_id().equals("RD") && !rook.is_hasMoved())
                    {
                        commands.add(new Command("E8C8", "A8D8", CommandType.Castle));
                    }
                }
            }
        }
        return commands.toArray(new Command[commands.size()]);
    }

    public void generateMoves()
    {
        super.generateMoves();
        generatePathFromVectors(_moveVectors);
        ArrayList<Path> tempList = new ArrayList<>();
        for(Path path : _moveList)
        {
            if(path.get_head() != null && !path.get_head().isDangerous(this))
            {
                tempList.add(path);
            }
        }
        _moveList = tempList;
        if(!_hasMoved && _owner.is_castling())
        {
            if(_id.charAt(1) == 'L')
            {
                if(!_owner.checkSquare("F1"))
                {
                    Path castlePath = new Path(this);
                    castlePath.addSquare(_owner.getSquare("G1"));
                    _moveList.add(castlePath);
                }
                if(!_owner.checkSquare("D1"))
                {
                    Path castlePath = new Path(this);
                    castlePath.addSquare(_owner.getSquare("C1"));
                    _moveList.add(castlePath);
                }
            }
            else
            {
                if(!_owner.checkSquare("F8"))
                {
                    Path castlePath = new Path(this);
                    castlePath.addSquare(_owner.getSquare("G8"));
                    _moveList.add(castlePath);
                }
                if(!_owner.checkSquare("D8"))
                {
                    Path castlePath = new Path(this);
                    castlePath.addSquare(_owner.getSquare("C8"));
                    _moveList.add(castlePath);
                }
            }
        }
    }
}
