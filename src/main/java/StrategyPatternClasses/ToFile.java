package StrategyPatternClasses;

import interfaces.IAlphabet;
import interfaces.IShowDFA;
import interfaces.IState;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.List;

/**
 * Created by oliv on 5/23/15.
 */
public class ToFile implements IShowDFA{

    private final String filepath;

    public ToFile(String filepath) {
       this.filepath = filepath;
    }

    @Override
    public void showTable(List<IState> stateList, IAlphabet alphabet) {
        File outputFile= new File(filepath);
        TableGenerator tableGenerator = new TableGenerator();
        PrintWriter printWriter;
        try {
            printWriter = new PrintWriter(outputFile);
        } catch (FileNotFoundException e) {
            System.err.println("Error trying to create file to write transition table to");
            System.err.println("filepath that caused error " + filepath);
            return;
        }
        printWriter.write(tableGenerator.getTable(stateList,alphabet));
        printWriter.close();
    }
}
