package StrategyPatternClasses;

import automatabuilder.Symbol;
import interfaces.IAlphabet;
import interfaces.IState;
import interfaces.ITransition;

import java.util.List;

/**
 * Created by oliv on 5/23/15.
 */
class TableGenerator {
    StringBuilder sb            = new StringBuilder();
    StringBuilder whitespace    = new StringBuilder();
    String whitespaceString;

    public String getTable(List<IState> stateList, IAlphabet alphabet){
        int maxNameLength = getNameLength(alphabet, stateList);
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
        firstRow(alphabet,maxNameLength);
        for (IState state : stateList) {
            addrow(state);
        }
        return sb.toString();
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

    private void firstRow(IAlphabet alphabet,int extraWhitespace){
        sb.append('[');
        sb.append(whitespaceString + whitespaceString);
        for (int i = 0; i < extraWhitespace; i++) {
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
