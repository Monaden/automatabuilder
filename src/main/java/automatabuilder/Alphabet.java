package automatabuilder;

import interfaces.IAlphabet;
import interfaces.IWord;

import java.lang.StringBuilder;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

public class Alphabet implements IAlphabet {

    protected Set<Symbol> symbols;
    
    public Alphabet (Set<Symbol> symbols){
        if (symbols == null) {
            throw new IllegalArgumentException("Symbols cannot be null");
        }
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
    public List<IWord> power(int n) {
        List<IWord> words = new LinkedList();
        // Base case
        if (n == 1) {
            for (Symbol a : symbols) {
                words.add(new Word(a.toString()));
            }
            return words;
        }
        
        List<IWord> oneShort = power(n-1);
        for (Symbol a : symbols) {
            for (IWord word : oneShort) {
                words.add(new Word(a, word));
            }
        }
        return words;
    }

    @Override
    public boolean isValid(IWord word) {
        //TODO Implement with iterable
        IWord next = word;
        while (next != null && next != Word.Empty) {
            Symbol a = next.head();
            if (!symbols.contains(a) && a != Symbol.Epsilon) {
                return false;
            }
            next = next.tail();
        }
        return true;
    }

    @Override
    public Iterator<Symbol> iterator() {
        return symbols.iterator();
    }
}
