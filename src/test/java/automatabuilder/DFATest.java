/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package automatabuilder;

import exceptions.InvalidWordException;
import interfaces.IState;
import interfaces.ITransition;
import interfaces.IWord;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author adam
 */
public class DFATest {
    
    private final DFA instance;
    private final State q0;
    private final State q1;
    private final List<ITransition> transitions;
    private final Symbol a;
    
    public DFATest() {
        a = new Symbol("a");
        
        Set<Symbol> symbs = new TreeSet<>();
        symbs.add(a);
        
        Alphabet alph = new Alphabet(symbs);
        
        
        transitions = new ArrayList<>();
        q0 = new State(transitions,false,"q0");
        q1 = new State(transitions,true,"q1");
        List<IState> qs = new ArrayList<>();
        qs.add(q0);
        qs.add(q1);
        
        
        Transition t = new Transition(q1, a);
        transitions.add(t);
        
        instance = new DFA(qs, alph, q0);
        
    }
    
    @Before
    public void setUp() {
        
    }

    /**
     * Test of delta method, of class DFA.
     */
    @Test
    public void testDelta() {
        System.out.println("delta");
        
        IState state = instance.delta(q0, a);
        String result = state.getName();
        
        
        
        assertEquals("q1", result);
    }

    /**
     * Test of deltaHat method, of class DFA.
     */
    @Test
    public void testDeltaHat() {
        System.out.println("deltaHat");
        IWord ax = new Word("aaaaaa");
        
        IState result = instance.deltaHat(q0, ax);
        assertEquals("q1", result.getName());
    }

    /**
     * Test of test method, of class DFA.
     */
    @Test
    public void testTest() {
        System.out.println("test");
        String word = "aaaaa";
        boolean result = false;
        try {
            result = instance.test(word);
        } catch (InvalidWordException ex) {
            Logger.getLogger(DFATest.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        assertTrue(result);
    }
    /**
     * Test of invalid word.
     */
    @Test(expected=InvalidWordException.class)
    public void testInvalidWord() throws InvalidWordException {
        System.out.println("testInvalidWord");
        String word = "aabaa";
        boolean result = instance.test(word);
        fail("No exception thrown.");
    }
    
}
