package interfaces;

import automatabuilder.Symbol;

public interface IAlphabet extends Iterable<Symbol>{
    public IWord power(int n);
    public Boolean isValid(IWord word);

    Boolean isValid();
}