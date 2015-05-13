/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package automatabuilder;

import interfaces.IState;
import interfaces.ITransition;
import java.util.Vector;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author Adam
 */
public class StateTest {
    
    public StateTest() {
    }

    /**
     * Test of getName method, of class State.
     */
    @org.junit.Test
    public void testGetName() {
        System.out.println("getName");
        Vector<ITransition> ts = new Vector();
        
        State instance = new State(ts, true, "q0");
        String expResult = "q0";
        
        String result = instance.getName();
        assertEquals(expResult, result);
    }

    /**
     * Test of isFinal method, of class State.
     */
    @org.junit.Test
    public void testIsFinal() {
        
        System.out.println("isFinal");
        
        Vector<ITransition> ts = new Vector();
        
        State instance = new State(ts, true, "q0");
        boolean expResult = true;
        boolean result = instance.isFinal();
        assertEquals(expResult, result);
    }

    /**
     * Test of transition method, of class State.
     */
    @org.junit.Test
    public void testTransition() {
        System.out.println("transition");
        Symbol a = new Symbol("a");
        Vector<ITransition> ts = new Vector();
        
        State q0 = new State(ts, true, "q0");
        State q1 = new State(new Vector(), true, "q1");
        
        Transition t1 = new Transition(q1, a);
        ts.add(t1);
        
        IState result = q0.transition(a);
        assertEquals(q1, result);
    }

    /**
     * Test of toString method, of class State.
     */
    @org.junit.Test
    public void testToString() {
        System.out.println("toString");
        Symbol a = new Symbol("a");
        Vector<ITransition> ts = new Vector();
        
        State q0 = new State(ts, false, "q0");
        State q1 = new State(new Vector(), true, "q1");
        
        Transition t1 = new Transition(q1, a);
        ts.add(t1);
        
        String expResult1 = "q0:(a->q1)";
        String expResult2 = "*q1:()";
        String result1 = q0.toString();
        String result2 = q1.toString();
        System.out.println(result2);
        System.out.println(expResult2);
        assertEquals(expResult1, result1);
        assertEquals(expResult2, result2);
    }
    
}
