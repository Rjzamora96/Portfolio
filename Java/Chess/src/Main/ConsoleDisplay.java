package Main;

import Pieces.Piece;

import java.util.Scanner;

/**
 * Created by Jacob Zamora on 2/17/2016.
 */
public class ConsoleDisplay implements IDisplayable {

    Scanner scanLee = new Scanner(System.in);

    public void displayBoard(Board board)
    {
        System.out.println(board);
    }

    public void displayCommand(Command command) {
        System.out.println(command);
    }

    @Override
    public void displayError(String error) {
        System.out.println(error);
    }

    @Override
    public void displayTurnswap(boolean isWhiteTurn) {
        if(isWhiteTurn)
        {
            System.out.println("It is now white's turn!");
        }
        else
        {
            System.out.println("It is now black's turn!");
        }
    }

    @Override
    public Piece getPieceSelection(Piece[] pieceList) {
        System.out.println("-Available Pieces-");
        System.out.println("1. Quit");
        for(int i = 0; i < pieceList.length; i++)
        {
            System.out.println(i + 2 + ". " + pieceList[i].get_id() + " on " + Vector2D.parseVector(pieceList[i].get_position().get_position()));
        }
        int response = getValidInt("Select a piece: ", "Error: Invalid input!", 1, pieceList.length + 1);
        return (response != 1) ? pieceList[response - 2] : null;
    }

    @Override
    public Command getCommandSelection(Command[] commandList) {
        System.out.println("-Available Commands-");
        System.out.println("1. Back");
        for(int i = 0; i < commandList.length; i++)
        {
            System.out.println(i + 2 + ". " + commandList[i]);
        }
        int response = getValidInt("Select a move: ", "Error: Invalid input!", 1, commandList.length + 1);
        return (response != 1) ? commandList[response - 2] : null;
    }

    public void displayStalemate()
    {
        System.out.println("There are no moves that can be made this turn, however no one is in check. Stalemate!");
    }

    public void displayCheck(boolean isWhiteTurn)
    {
        if(isWhiteTurn) {
            System.out.println("White's king is in check!");
        }
        else
        {
            System.out.println("Black's king is in check!");
        }
    }

    public void displayVictory(boolean isWhiteTurn)
    {
        if(isWhiteTurn)
        {
            System.out.println("Checkmate! Black wins!");
        }
        else
        {
            System.out.println("Checkmate! White wins!");
        }
    }

    private int getValidInt(String prompt, String error, int min, int max)
    {
        int parsedInt;
        while(true) {
            try {
                System.out.print(prompt);
                parsedInt = Integer.parseInt(scanLee.nextLine());
                if (parsedInt >= min && parsedInt <= max) {
                    return parsedInt;
                }
            } catch (NumberFormatException e) {
                continue;
            }
            System.out.println(error);
        }
    }
}
