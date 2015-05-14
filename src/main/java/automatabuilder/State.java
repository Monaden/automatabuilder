package automatabuilder;

import interfaces.IState;
import interfaces.ITransition;
import java.util.List;


public class State implements IState {

    protected List<ITransition> transitions;
    protected boolean isFinal;
    protected String name;

    public State (List<ITransition> transitions, boolean isFinal, String name) {
        if(transitions == null || name.equals("")){
           throw new IllegalArgumentException("State arguments invalid");
        }
        this.transitions = transitions;
        this.isFinal     = isFinal;
        this.name        = name;
    }

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

        //TODO fixa return null
        System.err.println("State missing transition something is wrong");
        return null;
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