package interfaces;

public interface IAlphabet {
    public IWord power(int n);
    public Boolean isValid(IWord word);

    Boolean isValid();
}