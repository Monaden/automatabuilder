package main.java.automatabuilder

import java.util.Vector;

public interface IAlphabet {

    public IWord power(int n);

    public Boolean isValid(IWord);

}