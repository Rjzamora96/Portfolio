package Main;

import Pieces.*;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by Jacob Zamora on 2/12/2016.
 */
public class Board {
    private HashMap<String, Square> _boardMap;
    private ArrayList<String> _keyList;
    private String[] _columns = { "A", "B", "C", "D", "E", "F", "G", "H" };
    private String _error = "";
    private Piece _blackKing;
    private Piece _whiteKing;
    private ArrayList<Command> _commandHistory = new ArrayList<>();

    private boolean _isWhiteTurn = true;

    private boolean _isChecked = false;

    private boolean _whiteCheck = false;
    private boolean _blackCheck = false;

    private boolean _castling = false;

    private boolean _isTestBoard = false;

    public Board()
    {
        _keyList = new ArrayList<>();
        _boardMap = new HashMap<>();
        for(String column : _columns)
        {
            for (int i = 1; i <= 8; i++)
            {
                String squareID = column + i;
                Vector2D squareLocation = Vector2D.parseLocation(squareID);
                Square square = new Square(squareLocation);
                _boardMap.put(squareID, square);
                _keyList.add(squareID);
            }
        }
        for(String key : _keyList)
        {
            Square currentSquare = _boardMap.get(key);
            if(currentSquare.get_position().y < 7) currentSquare.set_up(getSquare(Vector2D.parseVector(Vector2D.sum(currentSquare.get_position(), new Vector2D(0, 1)))));
            if(currentSquare.get_position().y > 0) currentSquare.set_down(getSquare(Vector2D.parseVector(Vector2D.sum(currentSquare.get_position(), new Vector2D(0, -1)))));
            if(currentSquare.get_position().x > 0) currentSquare.set_left(getSquare(Vector2D.parseVector(Vector2D.sum(currentSquare.get_position(), new Vector2D(-1, 0)))));
            if(currentSquare.get_position().x < 7) currentSquare.set_right(getSquare(Vector2D.parseVector(Vector2D.sum(currentSquare.get_position(), new Vector2D(1, 0)))));
        }
    }

    public Board simulateCommand(Command command)
    {
        if(_isTestBoard) return null;
        Board testBoard = new Board();
        testBoard._isTestBoard = true;
        for(Command pastCommand : _commandHistory)
        {
            testBoard.executeCommand(pastCommand);
        }
        testBoard.executeCommand(command);
        return testBoard;
    }
    private void Check()
    {
        for(String key : _keyList)
        {
            if(_boardMap.get(key).get_occupant() != null)
            {
                _boardMap.get(key).get_occupant().generateMoves();
                _boardMap.get(key).get_occupant().generateCommandList();
            }
        }
        if(_whiteKing != null)
        {
            Square square = _whiteKing.get_position();
            if(square.isAttacked())
            {
                _whiteCheck = true;
            }
            else
            {
                _whiteCheck = false;
            }
        }
        if(_blackKing != null)
        {
            Square square = _blackKing.get_position();
            if(square.isAttacked())
            {
                _blackCheck = true;
            }
            else
            {
                _blackCheck = false;
            }
        }
        if(_blackCheck || _whiteCheck)
        {
            _isChecked = true;
        }
        else
        {
            _isChecked = false;
        }
    }

    /**
     * Generates a list of movable pieces...
     * @return The list of movable pieces...
     */
    public Piece[] generatePieceList()
    {
        ArrayList<Piece> pieces = new ArrayList<>();
        Check();
        if(_isChecked)
        {
            if(_isWhiteTurn && _whiteCheck) {
                for (Path path : _whiteKing.get_position().get_colliders()) {
                    if (path.get_owner().get_id().charAt(1) != 'L') {
                        for (Square square : path.get_movePath()) {
                            if (square.isDangerous(path.get_owner())) {
                                for (Piece piece : square.get_attackers()) {
                                    if (piece.get_id().charAt(1) == 'L' && !pieces.contains(piece)) {
                                        if(piece.generateCommandList().length != 0) pieces.add(piece);
                                    }
                                }
                            }
                        }
                    }
                    if (path.get_owner().get_position().isAttacked()) {
                        for (Piece attacker : path.get_owner().get_position().get_attackers()) {
                            if (attacker.get_id().charAt(1) == 'L' && !pieces.contains(attacker)) {
                                if(attacker.generateCommandList().length != 0) pieces.add(attacker);
                            }
                        }
                    }
                }
            }
            else if(!_isWhiteTurn && _blackCheck)
            {
                for (Path path : _blackKing.get_position().get_colliders()) {
                    if (path.get_owner().get_id().charAt(1) != 'D') {
                        for (Square square : path.get_movePath()) {
                            if (square.isDangerous(path.get_owner())) {
                                for (Piece piece : square.get_attackers()) {
                                    if (piece.get_id().charAt(1) == 'D' && !pieces.contains(piece)) {
                                        if(piece.generateCommandList().length != 0) pieces.add(piece);
                                    }
                                }
                            }
                        }
                    }
                    if (path.get_owner().get_position().isAttacked()) {
                        for (Piece attacker : path.get_owner().get_position().get_attackers()) {
                            if (attacker.get_id().charAt(1) == 'D' && !pieces.contains(attacker)) {
                                if(attacker.generateCommandList().length != 0) pieces.add(attacker);
                            }
                        }
                    }
                }
            }
        }
        else {
            for (String key : _keyList) {
                if (checkSquare(key)) {
                    Piece occupant = getSquare(key).get_occupant();
                    if (occupant.get_id().charAt(1) == 'L' && _isWhiteTurn) {
                        if(occupant.generateCommandList().length != 0) pieces.add(occupant);
                    } else if (occupant.get_id().charAt(1) == 'D' && !_isWhiteTurn) {
                        if(occupant.generateCommandList().length != 0) pieces.add(occupant);
                    }
                }
            }
        }
        return pieces.toArray(new Piece[pieces.size()]);
    }

    /**
     * Checks if a square is occupied...
     * @param square name of the square...
     * @return If the square is occupied...
     */
    public boolean checkSquare(String square)
    {
        return (_boardMap.containsKey(square) && _boardMap.get(square).get_occupant() != null);
    }

    public Square getSquare(String square)
    {
        return (_boardMap.containsKey(square)) ? _boardMap.get(square) : null;
    }

    /**
     * Executes a given command.
     * @param command
     * The command to be executed.
     * @return
     * Returns whether or not command was successful.
     */
    public boolean executeCommand(Command command)
    {
        _commandHistory.add(command);
        switch (command.get_commandType())
        {
            case Place:
                if(!checkSquare(command.get_action()))
                {
                    Piece piece;
                    if(command.get_target().charAt(0) == 'P')
                    {
                        piece = new Pawn(command.get_target(), _boardMap.get(command.get_action()));
                    }
                    else if(command.get_target().charAt(0) == 'B')
                    {
                        piece = new Bishop(command.get_target(), _boardMap.get(command.get_action()));
                    }
                    else if(command.get_target().charAt(0) == 'R')
                    {
                        piece = new Rook(command.get_target(), _boardMap.get(command.get_action()));
                    }
                    else if(command.get_target().charAt(0) == 'N')
                    {
                        piece = new Knight(command.get_target(), _boardMap.get(command.get_action()));
                    }
                    else if(command.get_target().charAt(0) == 'Q')
                    {
                        piece = new Queen(command.get_target(), _boardMap.get(command.get_action()));
                    }
                    else if(command.get_target().charAt(0) == 'K')
                    {
                        piece = new King(command.get_target(), _boardMap.get(command.get_action()));
                        if(piece.get_id().charAt(1) == 'D')
                        {
                            _blackKing = piece;
                        }
                        else
                        {
                            _whiteKing = piece;
                        }
                    }
                    else
                    {
                        piece = new Piece(command.get_target(), _boardMap.get(command.get_action()));
                    }
                    piece.set_owner(this);
                    _boardMap.get(command.get_action()).set_occupant(piece);
                    return true;
                }
                else
                {
                    _error = "Error: Invalid placement!";
                    return false;
                }
            case Move:
                if(!checkSquare(command.get_action()) && checkSquare(command.get_target()))
                {
                    Piece targetPiece = _boardMap.get(command.get_target()).get_occupant();
                    if((_isWhiteTurn && targetPiece.get_id().charAt(1) == 'D') || (!_isWhiteTurn && targetPiece.get_id().charAt(1) == 'L'))
                    {
                        _error = (_isWhiteTurn) ? "Error: It's white's turn!" : "Error: It's black's turn!";
                        return false;
                    }
                    targetPiece.generateMoves();
                    if(_keyList.contains(command.get_action()) && targetPiece.validateMove(command.get_action()))
                    {
                        _boardMap.get(command.get_target()).set_occupant(null);
                        _boardMap.get(command.get_action()).set_occupant(targetPiece);
                        _isWhiteTurn = !_isWhiteTurn;
                        Check();
                        return true;
                    }
                    else
                    {
                        _error = "Error: Invalid move!";
                        return false;
                    }
                }
                else
                {
                    _error = "Error: Target square is occupied or piece doesn't exist!";
                    return false;
                }
            case Capture:
                if(checkSquare(command.get_action().substring(0,2)) &&  checkSquare(command.get_target())) {
                    Piece targetPiece = _boardMap.get(command.get_target()).get_occupant();
                    if((_isWhiteTurn && targetPiece.get_id().charAt(1) == 'D') || (!_isWhiteTurn && targetPiece.get_id().charAt(1) == 'L'))
                    {
                        _error = (_isWhiteTurn) ? "Error: It's white's turn!" : "Error: It's black's turn!";
                        return false;
                    }
                    targetPiece.generateMoves();
                    if (_keyList.contains(command.get_action().substring(0, 2)) && targetPiece.validateMove(command.get_action().substring(0, 2)))
                    {
                        if(_boardMap.get(command.get_action().substring(0, 2)).get_occupant().get_id().charAt(1) != targetPiece.get_id().charAt(1)) {
                            targetPiece.takePiece(_boardMap.get(command.get_action().substring(0, 2)).get_occupant());
                            _boardMap.get(command.get_target()).set_occupant(null);
                            _boardMap.get(command.get_action().substring(0, 2)).set_occupant(targetPiece);
                            _isWhiteTurn = !_isWhiteTurn;
                            Check();
                            return true;
                        }
                        else
                        {
                            _error = "Error: You cannot capture your own piece!";
                            return false;
                        }
                    }
                    else
                    {
                        _error = "Error: Invalid capture!!";
                        return false;
                    }
                }
                else
                {
                    _error = "Error: Target square is unoccupied or piece doesn't exist!";
                    return false;
                }
            case Castle:
                _castling = true;
                if(!checkSquare(command.get_subTarget()) && !checkSquare(command.get_subAction()) && checkSquare(command.get_target()) && checkSquare(command.get_action()))
                {
                    Piece targetPiece = _boardMap.get(command.get_target()).get_occupant();
                    Piece actionPiece = _boardMap.get(command.get_action()).get_occupant();
                    if(((_isWhiteTurn && targetPiece.get_id().charAt(1) == 'D') || (!_isWhiteTurn && targetPiece.get_id().charAt(1) == 'L')) || (_isWhiteTurn && actionPiece.get_id().charAt(1) == 'D') || (!_isWhiteTurn && actionPiece.get_id().charAt(1) == 'L'))
                    {
                        _error = (_isWhiteTurn) ? "Error: It's white's turn!" : "Error: It's black's turn!";
                        return false;
                    }
                    targetPiece.generateMoves();
                    actionPiece.generateMoves();
                    if(_keyList.contains(command.get_subTarget()) && targetPiece.validateMove(command.get_subTarget()) && _keyList.contains(command.get_subAction()) && actionPiece.validateMove(command.get_subAction())) {
                        _boardMap.get(command.get_target()).set_occupant(null);
                        _boardMap.get(command.get_subTarget()).set_occupant(targetPiece);
                        _boardMap.get(command.get_action()).set_occupant(null);
                        _boardMap.get(command.get_subAction()).set_occupant(actionPiece);
                        _castling = false;
                        _isWhiteTurn = !_isWhiteTurn;
                        Check();
                        return true;
                    }
                    else
                        _error = "Error: Invalid castle!";
                        _castling = false;
                        return false;
                }
                else
                {
                    _error = "Error: Square is occupied or pieces don't exist!";
                    _castling = false;
                    return false;
                }
        }
        return true;
    }

    @Override
    public String toString()
    {
        String board = "";
        board += "   ------------------------------\n";
        for(int row = 8; row > 0; row--)
        {
            board += row + " ";
            for(String column : _columns)
            {
                String squareID = column + row;
                if(checkSquare(squareID))
                {
                    board += "|"+ _boardMap.get(squareID).get_occupant().get_id() + "|";
                }
                else
                {
                    board += "|  |";
                }
            }
            board += "\n";
        }
        board += "   ------------------------------\n";
        board += "   A   B   C   D   E   F   G   H";
        return board;
    }

    public boolean is_whiteCheck() {
        return _whiteCheck;
    }

    public boolean is_blackCheck() {
        return _blackCheck;
    }

    public boolean isTestBoard() {
        return _isTestBoard;
    }
    public String get_error() {
        return _error;
    }

    public boolean is_castling() {
        return _castling;
    }

    public boolean isWhiteTurn() {
        return _isWhiteTurn;
    }

    public boolean isChecked() {
        return _isChecked;
    }
}
