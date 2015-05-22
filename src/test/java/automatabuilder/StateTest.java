/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package automatabuilder;

import interfaces.IState;
import interfaces.ITransition;
import java.util.Vector;
import org.junit.*;

import static org.junit.Assert.*;

/**
 *
 * @author Adam
 */
public class StateTest {

    private Vector<ITransition> transitions;

    private IState q0;
    private IState q1;
    private Symbol a;


    @Before
    public void setUp() throws Exception {
        transitions = new Vector<>();
    }

    private void buildStateWithOneTransitions(){
        a = new Symbol("a");

        q0 = new State(transitions, false, "q0");
        q1 = new State(new Vector(), true, "q1");

        Transition t1 = new Transition(q1, a);
        transitions.add(t1);

    }

    @Test
    public void testGetName() {
        State state = new State(transitions, true, "q0");
        assertEquals("q0", state.getName());
    }

    @Test
    public void testIsFinal() {
        State state = new State(transitions, true, "q0");
        assertEquals(true, state.isFinal());
    }

    @Test
    public void testTransition() {
        buildStateWithOneTransitions();
        IState result = q0.transition(a);
        assertEquals(q1, result);
    }

    @Test
    public void testToString() {
        buildStateWithOneTransitions();
        assertEquals("q0:(a->q1)", q0.toString());
        assertEquals("*q1:()",q1.toString());
    }

}
