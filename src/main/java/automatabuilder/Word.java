package automatabuilder;

import interfaces.IWord;
import java.lang.Iterable;
import java.util.Iterator;

public class Word implements IWord, Iterable<Symbol> {

    
    /**
     * The first symbol of the word.
     */
    private final Symbol head;
    
    /**
     * The rest of the word, represented by another IWord.
     */
    private final IWord tail;
    
    /**
     * The empty word
     */
    public static final Word Empty = new Word();
    
    /**
     * 
     * @param word 
     */
    public Word(String word) {
        
        // Remove all whitespace.
        
        if (word != null && word.length() > 0) {
            word = word.replaceAll("\\s+","");
            
            head = new Symbol(word.substring(0, 1));
            if (word.length() > 1) {
                tail = new Word(word.substring(1));
            } else {
                tail = Empty;
            }
        } else {
            head = Symbol.Epsilon;
            tail = null;
        }
    }
    
    /**
     * Copy constructor
     * @param w 
     */
    public Word(IWord w) {
        this(w.toString());
    }
    
    /**
     * Private empty constructor.
     */
    private Word() {
        head = Symbol.Epsilon;
        tail = null;
    }
    
    /**
     * String representation of a word.
     * @return 
     */
    @Override
    public String toString() {
        String headStr = head == null ? "" : head.toString();
        String tailStr = tail == null ? "" : tail.toString();
        
        return String.format("%s%s", headStr,tailStr);
    }
    @Override
    public Symbol head() {
        return head;
    }
    
    /**
     * Returns a copy of the tail of the word. 
     * @return IWord tail copy
     */
    @Override
    public IWord tail() {
        if (tail != null) {
            return new Word(tail);
        }
        return null;
    }
    
    
    @Override
    public Iterator<Symbol> iterator() {
        return new Iterator<Symbol>() {
            Symbol currentHead = head;
            IWord currentTail = tail;

            @Override
            public boolean hasNext() {
                return !head.equals("");
            }

            @Override
            public Symbol next() {
                Symbol next = currentHead;
                currentHead = currentTail.head();
                currentTail = currentTail.tail();
                return next;
            }

            @Override
            public void remove(){
                throw new UnsupportedOperationException("remove not supported for Word");
            }
        };
    }
}
