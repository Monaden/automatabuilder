package automatabuilder;

import interfaces.IState;
import interfaces.ITransition;
import java.util.Objects;

import java.util.Vector;

public class State implements IState {

    protected java.util.Vector<ITransition> transitions;
    protected boolean isFinal;
    protected String name;

    public State (Vector<ITransition> transitions, boolean isFinal, String name) {
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
        
        StringBuffer sb = new StringBuffer("(");
        if (transitions.isEmpty()) {
            sb.append(")");
        } else {
            for (ITransition t : transitions) {
                sb.append(t.toString());
                sb.append(",");
            }
            sb.setCharAt(sb.length()-1, ')');
        }
        
        
        return String.format("%s%s:%s", prefix, name, sb.toString());
    }

    @Override
    public int hashCode() {
        int hash = 5;
        hash = 23 * hash + (this.isFinal ? 1 : 0);
        hash = 23 * hash + Objects.hashCode(this.name);
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final State other = (State) obj;
        if (this.isFinal != other.isFinal) {
            return false;
        }
        if (!Objects.equals(this.name, other.name)) {
            return false;
        }
        return true;
    }
    
    

}