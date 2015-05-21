package StrategyPatternClasses;

import automatabuilder.Symbol;
import interfaces.IAlphabet;
import interfaces.IShowDFA;
import interfaces.IState;
import interfaces.ITransition;

import java.util.List;

/**
 * Created by oliv on 5/20/15.
 */
public class ToConsole implements IShowDFA {

    @Override
    public void showTable(List<IState> stateList, IAlphabet alphabet){
        StringBuilder sb = new StringBuilder();
        StringBuilder whitespace = new StringBuilder();
        int maxNameLength = getNameLength(alphabet, stateList);

        for (int i = 0; i < maxNameLength/2; i++) {
            whitespace.append(' ');
        }
        firstRow(alphabet,whitespace.toString(),maxNameLength,sb);
        for (IState state : stateList) {
            addrow(state, whitespace.toString(), sb);
        }
        System.out.println(sb);
    }

    private int getNameLength(IAlphabet alphabet, List<IState> stateList) {
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

    private void firstRow(IAlphabet alphabet,String whitespace,int extraWhitespace, StringBuilder stringBuilder){
        stringBuilder.append('[');
        stringBuilder.append(whitespace + whitespace);
        for (int i = 0; i < extraWhitespace; i++) {
            stringBuilder.append(" ");
        }
        for (Symbol symbol : alphabet) {
            addcell(whitespace, symbol.toString(), stringBuilder);
        }
        stringBuilder.append("]\n");
    }

    /*
    adds a row the StringBuilder with a state to the far left and
    the states transitions between two whitespace.
     */
    private void addrow( IState state, String whitespace, StringBuilder stringBuilder){
        List<ITransition> transitionList = state.getTransitions();
        stringBuilder.append('[');
        stringBuilder.append(whitespace);
        stringBuilder.append(state.getName());
        stringBuilder.append(whitespace);
        for (ITransition transition : transitionList) {
            addcell(whitespace, transition.getSymbol().toString(), stringBuilder);
        }
        stringBuilder.append("]\n");
    }
    /*
    adds a single cell to the StringBuilder with the cellChar between two whitespace
     */
    private void addcell(String whitespace, String cellChar, StringBuilder stringBuilder) {
            stringBuilder.append('|');
            stringBuilder.append(whitespace.toString());
            stringBuilder.append(cellChar);
            stringBuilder.append(whitespace.toString());
    }
}
