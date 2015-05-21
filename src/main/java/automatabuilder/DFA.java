package automatabuilder;

import interfaces.*;

import java.util.List;


public class DFA implements IAutomaton {

    protected List<IState> states;

    protected IAlphabet alphabet;

    protected IState startState;

    protected IShowDFA outputter;

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

}