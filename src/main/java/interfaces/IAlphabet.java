package interfaces;

import java.util.List;

public interface IAlphabet {
    public List<IWord> power(int n);
    public Boolean isValid(IWord word);

    Boolean isValid();
}