package Main;

import Pieces.Piece;

import java.util.ArrayList;

/**
 * Created by Jacob Zamora on 2/12/2016.
 */
public class GameManager {
    private final IDisplayable display = new ConsoleDisplay();
    public void run(String filePath)
    {
        ArrayList<Command> commands = Input.readFile(filePath);
        Board board = new Board();
        for(Command command : commands) {
            board.executeCommand(command);
        }
        while(true)
        {
            display.displayBoard(board);
            display.displayTurnswap(board.isWhiteTurn());
            if(board.isChecked())
            {
                display.displayCheck(board.isWhiteTurn());
            }
            Piece[] pieces = board.generatePieceList();
            if(pieces.length != 0) {
                Piece targetPiece = display.getPieceSelection(pieces);
                if (targetPiece == null) break;
                targetPiece.generateMoves();
                Command command = display.getCommandSelection(targetPiece.generateCommandList());
                if (command == null) continue;
                boolean successfulMove = board.executeCommand(command);
                if (!successfulMove) {
                    display.displayError(board.get_error());
                }
            }
            else
            {
                if((board.isWhiteTurn() && board.is_whiteCheck()) || (!board.isWhiteTurn() && board.is_blackCheck())) {
                    display.displayVictory(board.isWhiteTurn());
                }
                else
                {
                    display.displayStalemate();
                }
                break;
            }
        }
    }
}
