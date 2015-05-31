package automatabuilder;

import interfaces.*;

import java.util.List;


public class DFA implements IAutomaton {

    final protected List<IState> states;

    final protected IAlphabet alphabet;

    final protected IState startState;

    final protected IShowDFA outputter;

    public DFA(List<IState> states, IAlphabet alphabet, IState startState, IShowDFA outputter) {
        //TODO should maybe test for null arguments?
        this.states = states;
        this.alphabet = alphabet;
        this.startState = startState;
        this.outputter = outputter;
    }



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