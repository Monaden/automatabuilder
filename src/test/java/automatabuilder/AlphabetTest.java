/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package automatabuilder;

import interfaces.IWord;
import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author Adam
 */
public class AlphabetTest {
    Set<Symbol> symbols = new LinkedHashSet();
    Alphabet lang = new Alphabet(symbols);
    public AlphabetTest() {
        symbols.add(new Symbol("a"));
        symbols.add(new Symbol("b"));
    }

    /**
     * Test of toString method, of class Alphabet.
     */
    @Test
    public void testToString() {
        System.out.println("toString");
        String expResult = "{a,b}";
        String result = lang.toString();
        assertEquals(expResult, result);
    }

    /**
     * Test of power method, of class Alphabet.
     */
    @Test
    public void testPower() {
        System.out.println("power");
        
        int n = 3;
        
        List<IWord> expResult = new ArrayList();
        expResult.add(new Word("aaa"));
        expResult.add(new Word("aab"));
        expResult.add(new Word("aba"));
        expResult.add(new Word("baa"));
        expResult.add(new Word("abb"));
        expResult.add(new Word("bab"));
        expResult.add(new Word("bba"));
        expResult.add(new Word("bbb"));
        List<IWord> result = lang.power(n);
        
        assertEquals(expResult.size(), result.size());
        assertEquals(result.get(0).toString(), "aaa");
    }

    /**
     * Test of isValid method, of class Alphabet.
     */
    @Test
    public void testIsValid() {
        System.out.println("isValid");
        IWord w1 = new Word("aaa");
        IWord w2 = new Word("abc");
        
        boolean r1 = lang.isValid(w1);
        boolean r2 = lang.isValid(w1);
        System.out.println(r1);
        System.out.println(r2);
        assertTrue(r1);
        assertFalse(r2);
    }

    
}
