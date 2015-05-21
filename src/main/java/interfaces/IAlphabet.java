package interfaces;

import automatabuilder.Symbol;
import java.util.List;

public interface IAlphabet {
    public List<IWord> power(int n);
    public boolean isValid(IWord word);
    public boolean contains(Symbol symbol);
    public int size();
}