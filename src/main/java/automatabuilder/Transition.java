package automatabuilder;

import interfaces.IState;
import interfaces.ITransition;

public class Transition implements ITransition {

    final protected IState target;

    final protected Symbol symbol;

    public Transition(IState target, Symbol symbol) {
        if (target == null || symbol == null) {
            throw new IllegalArgumentException("Incorrect transition");
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