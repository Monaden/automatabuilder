package StrategyPatternClasses;

import automatabuilder.Symbol;
import interfaces.IAlphabet;
import interfaces.IState;
import interfaces.ITransition;

import java.io.PrintStream;
import java.util.List;

/**
 * Created by oliv on 5/23/15.
 */
public class TableGenerator {
    final StringBuilder sb            = new StringBuilder();
    final StringBuilder whitespace    = new StringBuilder();
    final String whitespaceString;
    final List<IState> stateList;
    final IAlphabet alphabet;
    PrintStream printStream;
    int maxNameLength;

    public TableGenerator (List<IState> stateList, IAlphabet alphabet, PrintStream printStream) {
        //TODO maybe add tests for nulls
        this.stateList = stateList;
        this.alphabet = alphabet;
        this.printStream = printStream;
        maxNameLength = getNameLength();

        for (int i = 0; i < maxNameLength/2; i++) {
            whitespace.append(' ');
        }
        for (IState state : stateList) {
            if (state.isFinal()) {
                maxNameLength++;
                break;
            }
        }
        whitespaceString = whitespace.toString();
    }
        
    public void generate() {
        printStream.print(getTable());
    }

    public void changePrintStream(PrintStream printStream) {
        //TODO maybe add tests for null
        this.printStream = printStream;
    }

    private String getTable(){
        firstRow();
        stateList.forEach(state -> addrow(state));
        return sb.toString();
    }

    private int getNameLength() {
        int cellLength = 0;
        for (Symbol symbol : alphabet) {
            int symbolLength = symbol.toString().length();
            if (symbolLength > cellLength) {
               cellLength = symbolLength;
           }
        }

        for (IState state : stateList) {
            int nameLength = state.getName().length();
            if(nameLength > cellLength) {
               cellLength = nameLength;
            }
        }
        return cellLength;
    }

    private void firstRow(){
        sb.append('[');
        sb.append(whitespaceString);
        sb.append(whitespaceString);
        for (int i = 0; i < maxNameLength; i++) {
            sb.append(" ");
        }
        for (Symbol symbol : alphabet) {
            addcell(symbol.toString());
        }
        sb.append("]\n");
    }

    /*
    adds a row the StringBuilder with a state to the far left and
    the states transitions between two whitespace.
     */
    private void addrow(IState state){
        List<ITransition> transitionList = state.getTransitions();
        sb.append('[');
        sb.append(whitespaceString);
        String finalMark = (state.isFinal()) ? "*" : "";
        sb.append(finalMark);
        sb.append(state.getName());
        sb.append(whitespaceString);
        for (ITransition transition : transitionList) {
            addcell(transition.getSymbol().toString());
        }
        sb.append("]\n");
    }
    /*
    adds a single cell to the StringBuilder with the cellChar between two whitespace
     */
    private void addcell(String cellChar) {
            sb.append('|');
            sb.append(whitespaceString);
            sb.append(cellChar);
            sb.append(whitespaceString);
    }
}
