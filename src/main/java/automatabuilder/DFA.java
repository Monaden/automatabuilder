package automatabuilder;

import interfaces.IAlphabet;
import interfaces.IAutomaton;
import interfaces.IState;
import interfaces.IWord;

import java.util.Vector;

public class DFA implements IAutomaton {

    protected java.util.Vector states;

    protected IAlphabet alphabet;

    protected IState startState;

    /**
   * @element-type IState
   */

    public Vector  myIState;
    public IAlphabet myIAlphabet;

    protected IState delta(IState q, Symbol s) {
        return null;
    }

    protected IState deltaHat(IState q, IWord ax) {
        return null;
    }

    @Override
    public Boolean test(String word) {
        return null;
    }
}