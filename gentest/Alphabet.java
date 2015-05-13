package main.java.automatabuilder

import java.lang.StringBuilder;
import java.util.Set;

public class Alphabet implements IAlphabet {

    protected Set<Symbol> symbols;
    
    public Alphabet (Set<Symbol> symbols){
        this.symbols = symbols;
    }

    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append('{')
        for (Symbol s : symbols){
            sb.append(s);
            sb.append(',')
        }
        sb.setCharAt(sb.length()-1, '}');
        return sb.toString();
    }

}
