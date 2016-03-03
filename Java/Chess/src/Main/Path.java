package Main;

import Pieces.Piece;

import java.util.ArrayList;

/**
 * Created by Jacob Zamora on 2/22/2016.
 */
public class Path {

    private ArrayList<Square> _movePath = new ArrayList<>();

    private Square _head;

    private Piece _owner;

    public Path(Piece owner)
    {
        _owner = owner;
    }

    public void clearPath()
    {
        for(Square square : _movePath)
        {
            square.unregisterPath(this);
        }
    }
    public void addSquare(Square square)
    {
        _movePath.add(square);
        _head = square;
        _head.registerPath(this);
    }

    public boolean checkPath()
    {
        for(Square square : _movePath)
        {
            if(square.get_occupant() != null && square != _head) return true;
        }
        return false;
    }

    public boolean compareHead(Square square)
    {
        if(_head == square) return true;
        return false;
    }

    public ArrayList<Square> get_movePath() {
        return _movePath;
    }
    public Square get_head() {
        return _head;
    }
    public Piece get_owner() {
        return _owner;
    }
}
