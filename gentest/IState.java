package main.java.automatabuilder

public interface IState {

    public IState transition(Symbol a);

    public String getName();

}