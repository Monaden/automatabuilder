package automatabuilder;

import interfaces.*;

import java.io.*;
import java.util.List;


public class DFA implements IAutomaton {

    protected List<IState> states;

    protected IAlphabet alphabet;

    protected IState startState;

    protected IShowTransitionTable showTransitionTable;

    protected IState delta(IState q, Symbol s) {
        //TODO: Implement
        return null;
    }

    protected IState deltaHat(IState q, IWord ax) {
        //TODO: Implement
        return null;
    }

    @Override
    public Boolean test(String word) {
        //TODO: Implement
        return null;
    }

    //TODO add to interface since public
    public void show(){
        showTransitionTable.showTable(this);
    }

}