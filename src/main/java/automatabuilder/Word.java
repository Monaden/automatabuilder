package automatabuilder;

import interfaces.IWord;
import java.lang.Iterable;
import java.util.Iterator;

public class Word implements IWord, Iterable<Symbol> {

    private Symbol head;
    private IWord tail;

    //TODO not sure how to implement
    public Word () {
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

    public String toString() {
        //TODO use string format/or stringbuilder
        return head.toString() + tail.toString();
     }

    @Override
    public Symbol head() {
        //TODO implements
        return null;
    }

    @Override
    public IWord tail() {
        //TODO implements
        return null;
    }
}
