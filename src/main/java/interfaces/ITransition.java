package interfaces;

import automatabuilder.Symbol;

public interface ITransition {

    public ISymbol getSymbol();

    public IState getTarget();

}