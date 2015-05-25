package automatabuilder;

import interfaces.IState;
import interfaces.ITransition;

import java.util.LinkedList;
import java.util.List;
import java.util.Objects;


public class State implements IState {

    private final List<ITransition> transitions;
    private final boolean isFinal;
    private final String name;

    public State (List<ITransition> transitions, boolean isFinal, String name) {
        if(transitions == null || name.isEmpty()){
           throw new IllegalArgumentException("State arguments invalid.");
        }
        this.transitions = transitions;
        this.isFinal     = isFinal;
        this.name        = name;
    }

    @Override
    public List<ITransition> getTransitions() {
        return new LinkedList<>(transitions);
    }

    @Override
    public String getName() {
        return name;
    }
    
    @Override
    public boolean isFinal() {
        return isFinal;
    }
    
    @Override
    public IState transition(Symbol a) {
        if (a.equals(Symbol.Epsilon)) {
            return this;
        }
        for (ITransition t : transitions) {
            if (t.getSymbol().equals(a)) {
                return t.getTarget();
            }
        }

        return DeadState.INSTANCE;
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
