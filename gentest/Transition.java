package main.java.automatabuilder;

import java.util.Vector;

public class Transition implements ITransition {

    protected IState target;

    protected ISymbol symbol;

    public Transition(IState target, ISymbol symbol) {
        if (target == null || symbol == null) {
            throw new IllegalArgumentException("Incorrect transition");
        }
        this.target = target;
        this.symbol = symbol;
    }

    
    
    public IState getTarget() {
        return target;
    }

    public ISymbol getSymbol() {
        return symbol;
    }
    
    public String toString() {
        return symbol+"->"+target.getName();
    }

}