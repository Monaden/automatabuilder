package main.java.automatabuilder;

import java.util.Iterator;
import java.util.Vector;

public class State implements IState {

    protected java.util.Vector<ITransition> transitions;
    protected Boolean isFinal;
    protected String name;

    public String getName() {
        return name;
    }
    
    public boolean isFinal() {
        return isFinal;
    }
    
    public IState transition(Symbol a) {
        for (ITransition t : transitions) {
            if (t.getSymbol() == a) {
                return t.getTarget();
            }
        }
    }
    
    @Override
    public String toString() {
        String prefix = isFinal ? "*" : "";
        
        boolean first = true;
        StringBuffer sb = new StringBuffer("(");
        for (ITransition t : transitions) {
            sb.append(t.toString());
            sb.append(", ");
        }
        sb.setCharAt(sb.length()-1, ')');
        
        
        return String.format("%s%s:%s", prefix, name, transitions);
    }
    
    

}