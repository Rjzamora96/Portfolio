package Pieces;

import Main.*;

import java.util.ArrayList;

/**
 * Created by Jacob Zamora on 2/16/2016.
 */
public class Piece {

    protected String _id;
    protected Square _position;
    protected Board _owner;

    protected ArrayList<Path> _moveList = new ArrayList<>();

    protected boolean _hasMoved = false;

    public Piece() {}
    public Piece(String id, Square position)
    {
        _id = id;
        _position = position;
    }

    public Command[] generateCommandList()
    {
        ArrayList<Command> commands = new ArrayList<>();
        ArrayList<Path> tempList = new ArrayList<>();
        for(Path path : _moveList)
        {
            tempList.add(path);
            if(!path.checkPath())
            {
                if(path.get_head() != null && !_owner.checkSquare(Vector2D.parseVector(path.get_head().get_position())))
                {
                    if(!_owner.isTestBoard())
                    {
                        Command command = new Command(Vector2D.parseVector(_position.get_position()), Vector2D.parseVector(path.get_head().get_position()), CommandType.Move);
                        Board testBoard = _owner.simulateCommand(command);
                        if ((_id.charAt(1) == 'L' && !testBoard.is_whiteCheck()) || (_id.charAt(1) == 'D' && !testBoard.is_blackCheck())) commands.add(command);
                    }
                    else
                    {
                        Command command = new Command(Vector2D.parseVector(_position.get_position()), Vector2D.parseVector(path.get_head().get_position()), CommandType.Move);
                        commands.add(command);
                    }
                }
                else
                {
                    if(path.get_head() != null && path.get_head().get_occupant().get_id().charAt(1) != _id.charAt(1)) {
                        if(!_owner.isTestBoard())
                        {
                            Command command = new Command(Vector2D.parseVector(_position.get_position()), Vector2D.parseVector(path.get_head().get_position()) + "*", CommandType.Capture);
                            Board testBoard = _owner.simulateCommand(command);
                            if ((_id.charAt(1) == 'L' && !testBoard.is_whiteCheck()) || (_id.charAt(1) == 'D' && !testBoard.is_blackCheck())) commands.add(command);
                        }
                        else
                        {
                            Command command = new Command(Vector2D.parseVector(_position.get_position()), Vector2D.parseVector(path.get_head().get_position()) + "*", CommandType.Capture);
                            commands.add(command);
                        }
                    }
                }
            }
            else
            {
                tempList.remove(path);
                path.clearPath();
            }
        }
        _moveList = tempList;
        return commands.toArray(new Command[commands.size()]);
    }

    public void takePiece(Piece piece)
    {
        piece._moveList.forEach(Path::clearPath);
    }

    public void generateMoves()
    {
        _moveList.forEach(Path::clearPath);
        _moveList.clear();
    }



    protected Path generateFromPath(Square currentSquare, Path lastPath)
    {
        Path currentPath = new Path(this);
        if(lastPath != null)
        {
            lastPath.get_movePath().forEach(currentPath::addSquare);
        }
        currentPath.addSquare(currentSquare);
        _moveList.add(currentPath);
        return currentPath;
    }

    public boolean validateMove(String move)
    {
        Square targetSquare = _owner.getSquare(move);
        if(targetSquare != null)
        {
            for(Path path : _moveList)
            {
                if(path.compareHead(targetSquare) && !path.checkPath())
                {
                    return true;
                }
            }
        }
        return false;
    }

    protected void generateAdjacentPaths()
    {
        Square currentSquare = _position;
        Path lastPath = null;
        while((currentSquare = currentSquare.get_up()) != null) lastPath = generateFromPath(currentSquare, lastPath);
        currentSquare = _position;
        lastPath = null;
        while((currentSquare = currentSquare.get_right()) != null) lastPath = generateFromPath(currentSquare, lastPath);
        currentSquare = _position;
        lastPath = null;
        while((currentSquare = currentSquare.get_down()) != null) lastPath = generateFromPath(currentSquare, lastPath);
        currentSquare = _position;
        lastPath = null;
        while((currentSquare = currentSquare.get_left()) != null) lastPath = generateFromPath(currentSquare, lastPath);
    }

    protected void generateDiagonalPaths()
    {
        Square currentSquare = _position;
        Path lastPath = null;
        while(currentSquare.get_up() != null && (currentSquare = currentSquare.get_up().get_left()) != null) lastPath = generateFromPath(currentSquare, lastPath);
        currentSquare = _position;
        lastPath = null;
        while(currentSquare.get_up() != null && (currentSquare = currentSquare.get_up().get_right()) != null) lastPath = generateFromPath(currentSquare, lastPath);
        currentSquare = _position;
        lastPath = null;
        while(currentSquare.get_down() != null && (currentSquare = currentSquare.get_down().get_right()) != null) lastPath = generateFromPath(currentSquare, lastPath);
        currentSquare = _position;
        lastPath = null;
        while(currentSquare.get_down() != null && (currentSquare = currentSquare.get_down().get_left()) != null) lastPath = generateFromPath(currentSquare, lastPath);
    }

    protected void generatePathFromVectors(Vector2D[] _moveVectors)
    {
        for(Vector2D vector : _moveVectors)
        {
            Vector2D sum = Vector2D.sum(_position.get_position(), vector);
            if(sum.x >= 0 && sum.x <= 7 && sum.y >= 0 && sum.y <= 7)
            {
                Square currentSquare = _owner.getSquare(Vector2D.parseVector(Vector2D.sum(_position.get_position(), vector)));
                if(currentSquare != null)
                {
                    Path path = new Path(this);
                    path.addSquare(currentSquare);
                    _moveList.add(path);
                }
            }
        }
    }

    public String get_id() {
        return _id;
    }

    public Square get_position() {
        return _position;
    }

    public void set_position(Square _position) {
        this._position = _position;
    }

    public Board get_owner() {
        return _owner;
    }

    public void set_owner(Board _owner) {
        this._owner = _owner;
    }

    public boolean is_hasMoved() {
        return _hasMoved;
    }

    public void set_hasMoved(boolean _hasMoved) {
        this._hasMoved = _hasMoved;
    }

    public ArrayList<Path> get_moveList() {
        return _moveList;
    }
}
