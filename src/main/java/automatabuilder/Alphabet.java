package automatabuilder;

import interfaces.IAlphabet;
import interfaces.IWord;

import java.lang.StringBuilder;
import java.util.Set;

public class Alphabet implements IAlphabet {

    protected Set<Symbol> symbols;
    
    public Alphabet (Set<Symbol> symbols){
        this.symbols = symbols;
    }

    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append('{');
        for (Symbol s : symbols){
            sb.append(s);
            sb.append(',');
        }
        sb.setCharAt(sb.length()-1, '}');
        return sb.toString();
    }

    @Override
    public IWord power(int n) {
        //TODO Implement
        return null;
    }

    @Override
    public Boolean isValid(IWord word) {
        //TODO implement
        return null;
    }

    @Override
    public Boolean isValid() {
        //TODO Implpement
        return null;
    }
}
