package interfaces;

import automatabuilder.Symbol;

import java.util.List;

public interface IAlphabet extends Iterable<Symbol>{
    public List<IWord> power(int n);
    public boolean isValid(IWord word);

}