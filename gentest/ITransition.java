package main.java.automatabuilder

public interface ITransition {

    public Symbol getSymbol();

    public IState getTarget();

}