package main.java.automatabuilder

public class Word implements IWord {

    public Symbol head;
    public IWord tail;

    public String toString() {
        return head + tail;
     }

}
