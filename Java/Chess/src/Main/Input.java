package Main;

import java.io.*;
import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by Jacob Zamora on 2/12/2016.
 */

//[p|r|b|n|q|k|P|R|B|N|Q|K][l|L|d|D]
//[A-H|a-h][1-8][\*]?

public class Input {

    /**
     * Reads in a text file and generates a list of commands.
     * @param path
     * Path to the command list file.
     * @return
     * The parsed list of commands.
     */
    public static ArrayList<Command> readFile(String path)
    {
        ArrayList<Command> commandList = new ArrayList<>();
        try
        {
            FileReader fileReader = new FileReader(path);
            BufferedReader bufferReader = new BufferedReader((fileReader));
            String currentLine;
            while((currentLine = bufferReader.readLine()) != null)
            {
                Command command;
                currentLine = currentLine.replaceAll("\\s+", "");
                currentLine = currentLine.toUpperCase();
                command = generateCommandFromString("^([P|R|B|N|Q|K][L|D])([A-H][1-8])$", currentLine, CommandType.Place);
                if(command == null) command = generateCommandFromString("^([A-H][1-8])([A-H][1-8])$", currentLine, CommandType.Move);
                if(command == null) command = generateCommandFromString("^([A-H][1-8])([A-H][1-8][\\*])$", currentLine, CommandType.Capture);
                if(command == null) command = generateCommandFromString("^([A-H][1-8][A-H][1-8])([A-H][1-8][A-H][1-8])$", currentLine, CommandType.Castle);
                if(command != null)
                {
                    commandList.add(command);
                }
                else
                {
                    System.out.println("Invalid command: " + currentLine + "!");
                    System.out.println("Command has been thrown out!");
                }
            }
            bufferReader.close();
        }
        catch (FileNotFoundException e)
        {
            System.out.println("File not found!");
        }
        catch (IOException e) {
            System.out.println("IO exception found!");
        }
        return commandList;
    }
    private static Command generateCommandFromString(String regex, String clip, CommandType commandType)
    {
        Command command = null;
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(clip);
        if(matcher.find())
        {
            command = new Command(matcher.group(1), matcher.group(2), commandType);
        }
        return command;
    }
}
