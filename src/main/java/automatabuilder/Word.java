package automatabuilder;

import interfaces.IWord;

public class Word implements IWord {

    private Symbol head;
    private IWord tail;

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
