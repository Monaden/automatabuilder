package interfaces;
import automatabuilder.Symbol;

public interface IWord {

    public Symbol head();

    public IWord tail();
}
