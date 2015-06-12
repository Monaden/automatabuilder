package automatabuilder;

import exceptions.InvalidWordException;
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


    
    public DFA(List<IState> states, IAlphabet alphabet, IState startState){
        this.states = states;
        this.alphabet = alphabet;
        this.startState = startState;
	this.outputter = null;
    }
    

    public DFA(List<IState> states, IAlphabet alphabet, IState startState, IShowDFA outputter) {
        this.states = states;
        this.alphabet = alphabet;
        this.startState = startState;
        this.outputter = outputter;
    }

    public DFA(List<IState> states, IAlphabet alphabet, IState startState) {
        this(states, alphabet, startState, null);
    }

    
    
    protected IState delta(IState q, Symbol s) {
        return q.transition(s);
    }

    protected IState deltaHat(IState q, IWord ax) {
        if (ax == null || ax.equals(Word.Empty)) {
            return q;
        }
        IState next = delta(q,ax.head());
        return deltaHat(next, ax.tail());
    }

    @Override
    public boolean test(String word) throws InvalidWordException {
        IWord w = new Word(word);
        
        if (!alphabet.isValid(w)) {
            throw new InvalidWordException(
                String.format("The word '%s' is invalid", word)
            );
        }
        
        IState end = deltaHat(startState, w);
        
        return end.isFinal();
    }

}
