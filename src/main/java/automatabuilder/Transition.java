package automatabuilder;

import interfaces.IState;
import interfaces.ITransition;

public class Transition implements ITransition {

    public static final String EXCEPTION_MESSAGE = "Transition arguments invalid.";
    
    protected IState target;

    protected Symbol symbol;

    public Transition(IState target, Symbol symbol) {
        if (target == null || symbol == null) {
            throw new IllegalArgumentException(EXCEPTION_MESSAGE);
        }
        this.target = target;
        this.symbol = symbol;
    }

    public IState getTarget() {
        return target;
    }
    public Symbol getSymbol() {
        return symbol;
    }
    public String toString() {
        return symbol+"->"+target.getName();
    }

}