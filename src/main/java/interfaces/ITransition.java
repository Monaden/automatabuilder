package interfaces;

import automatabuilder.Symbol;

public interface ITransition {

    public Symbol getSymbol();

    public IState getTarget();

}