package automatabuilder;

import interfaces.IWord;

public class Word implements IWord {
    
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
     * Copy constructor, with added symbol.
     * @param a First symbol
     * @param w Rest of the word
     */
    public Word(Symbol a, IWord w) {
        this(a + w.toString()); // Ensures deep copy
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
}
