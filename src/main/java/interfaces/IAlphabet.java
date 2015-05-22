package interfaces;

import java.util.List;

public interface IAlphabet {
    public List<IWord> power(int n);
    public boolean isValid(IWord word);

}