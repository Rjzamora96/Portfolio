package Main;

import Pieces.Piece;

import java.util.ArrayList;

/**
 * Created by Jacob Zamora on 2/22/2016.
 */
public class Square {
    private Vector2D _position;
    private Piece _occupant;
    private Square _up;
    private Square _down;
    private Square _left;
    private Square _right;

    private boolean _attacked = false;

    private ArrayList<Piece> _attackers = new ArrayList<>();

    private ArrayList<Path> _colliders = new ArrayList<>();

    public void registerPath(Path path)
    {
        if(!_colliders.contains(path))
        {
            _colliders.add(path);
            _attacked = true;
            _attackers.add(path.get_owner());
        }
    }

    public boolean isAttacked()
    {
        for(Piece attacker : _attackers)
        {
            if(attacker.get_id().charAt(1) != _occupant.get_id().charAt(1))
            {
                return true;
            }
        }
        return false;
    }

    /**
     * Checks against a piece to see if it's a dangerous spot for that piece...
     * @param piece Piece to see if it would be attacked.
     * @return
     */
    public boolean isDangerous(Piece piece)
    {
        for(Piece attacker : _attackers)
        {
            if(attacker.get_id().charAt(1) != piece.get_id().charAt(1))
            {
                return true;
            }
        }
        return false;
    }

    public void unregisterPath(Path path)
    {
        if(_colliders.contains(path))
        {
            _colliders.remove(path);
            _attackers.remove(path.get_owner());
            if(_colliders.isEmpty())
            {
                _attacked = false;
            }
        }
    }

    public Square(Vector2D position)
    {
        _position = position;
    }

    public Piece get_occupant() {
        return _occupant;
    }

    public void set_occupant(Piece _occupant) {
        if(_occupant != null)
        {
            if(_occupant.get_position() != this && !_occupant.is_hasMoved())
            {
                _occupant.set_hasMoved(true);
            }
            _occupant.set_position(this);
        }
        this._occupant = _occupant;
    }

    public Vector2D get_position() {
        return _position;
    }

    public void set_position(Vector2D _position) {
        this._position = _position;
    }

    public Square get_up() {
        return _up;
    }

    public void set_up(Square _up) {
        this._up = _up;
    }

    public Square get_down() {
        return _down;
    }

    public void set_down(Square _down) {
        this._down = _down;
    }

    public Square get_left() {
        return _left;
    }

    public void set_left(Square _left) {
        this._left = _left;
    }

    public Square get_right() {
        return _right;
    }

    public void set_right(Square _right) {
        this._right = _right;
    }

    public boolean is_attacked() {
        return _attacked;
    }

    public void set_attacked(boolean _attacked) {
        this._attacked = _attacked;
    }

    public ArrayList<Path> get_colliders() {
        return _colliders;
    }
    public ArrayList<Piece> get_attackers() {
        return _attackers;
    }
}
