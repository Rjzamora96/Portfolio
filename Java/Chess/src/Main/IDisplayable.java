package Main;

import Pieces.Piece;

/**
 * Created by Jacob Zamora on 2/17/2016.
 */
public interface IDisplayable {
    void displayBoard(Board board);
    void displayCommand(Command command);
    void displayError(String error);
    void displayTurnswap(boolean isWhiteTurn);
    void displayCheck(boolean isWhiteTurn);
    void displayVictory(boolean isWhiteTurn);
    void displayStalemate();
    Piece getPieceSelection(Piece[] pieceList);
    Command getCommandSelection(Command[] commandList);
}
