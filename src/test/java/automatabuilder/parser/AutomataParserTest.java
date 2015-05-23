/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package automatabuilder.parser;

import automatabuilder.State;
import automatabuilder.Symbol;
import automatabuilder.Transition;
import interfaces.IAutomaton;
import static org.junit.Assert.assertTrue;
import org.junit.Test;

import static automatabuilder.parser.AutomataParserException.*;

/**
 *
 * @author Mikael
 */
public class AutomataParserTest {
    
    public static final String TEST_FILES_DIRECTORY = "TestFilesAutomataParser/";
    

    private boolean testParseAutomata(String filepath){
        IAutomaton automata = null;
        String errorMessage = null;
        
        try {
            automata = AutomataParser.parseXmlFile(TEST_FILES_DIRECTORY + filepath);
        } catch (AutomataParserException ex) {
            errorMessage = ex.toString();
            System.out.println(ex.toString());
        }
        
        return automata != null && errorMessage == null;
    }
    
    
    private boolean testException(String filepath, String exceptionMessage){
        IAutomaton automata = null;
        String errorMessage = null;
        
        try {
            automata = AutomataParser.parseXmlFile(TEST_FILES_DIRECTORY + filepath);
        } catch (AutomataParserException ex) {
            errorMessage = ex.toString();
        }
        
        return errorMessage.equals(AutomataParserException.PREFIX + exceptionMessage);
    }
    
    
    @Test
    public void testOddNrPushAutomata(){
        assertTrue(testParseAutomata("odd_nr_push.xml"));
    }
    
    
    @Test
    public void testSubwordAbcAutomata(){
        assertTrue(testParseAutomata("subword_abc.xml"));
    }
    
    
    @Test
    public void testInvalidState(){
        assertTrue(testException("invalid_state.xml", State.EXCEPTION_MESSAGE));
    }
    

    @Test
    public void testInvalidSymbol(){
        assertTrue(testException("invalid_symbol.xml", Symbol.EXCEPTION_MESSAGE));
    }
    
    
    @Test
    public void testInvalidTransition(){
        assertTrue(testException("invalid_transition.xml", Transition.EXCEPTION_MESSAGE));
    }
    
    
    @Test
    public void testMissingTransition(){
        assertTrue(testException("missing_transition.xml", MISSING_TRANSITION));
    }
    
    
    @Test
    public void testMultipleTransitionsFromOneSymbol(){
        assertTrue(testException("multiple_transitions_from_one_symbol.xml", MULTIPLE_TRANSITIONS_FORM_SYMBOL));
    }
    
    
    @Test
    public void testNoAlphabet(){
        assertTrue(testException("no_alphabet.xml", NO_OR_MORE_APLHABET));
    }
    
    
    @Test
    public void testNoStates(){
        assertTrue(testException("no_states.xml", NO_STATES));
    }
    
    
    @Test
    public void testNoSymbolInAlphabet(){
        assertTrue(testException("no_symbol_in_alphabet.xml", NO_SYMBOLS));
    }
    
    
    @Test
    public void testNoTransitionFromState(){
        assertTrue(testException("no_transition_from_state.xml", NO_TRANSITIONS));
    }
    
    
    @Test
    public void testStatesAboveAlphabet(){
        assertTrue(testParseAutomata("states_above_alphabet.xml"));
    }
    
    
    @Test
    public void testTransitionUsingSymbolOutsideOfAlphabet(){
        assertTrue(testException("transition_using_symbol_outside_of_alphabet.xml", TRANSITION_WITH_INVALID_SYMBOL));
    }
    
    
    @Test
    public void testTwoAlphabets(){
        assertTrue(testException("two_alphabets.xml", NO_OR_MORE_APLHABET));
    }
        
    
    @Test
    public void testStatesSameName(){
        assertTrue(testException("states_same_name.xml", STATES_SAME_NAME));
    }
    
    
    @Test
    public void testUNUSEDfunctionality(){
        assertTrue(testParseAutomata("UNUSED_functionality.xml"));
    }
    
    
    @Test
    public void testNUMBERSfunctionality(){
        assertTrue(testParseAutomata("NUMBERS_functionality.xml"));
    }
    
    
    @Test
    public void testALL_LETTERSfunctionality(){
        assertTrue(testParseAutomata("ALL_LETTERS_functionality.xml"));
    }
}
