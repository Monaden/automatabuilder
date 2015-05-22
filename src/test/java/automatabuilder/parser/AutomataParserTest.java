/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package automatabuilder.parser;

import interfaces.IAutomaton;
import static org.junit.Assert.assertTrue;
import org.junit.Test;

/**
 *
 * @author Mikael
 */
public class AutomataParserTest {
    
    public static final String TEST_FILES_DIRECTORY = "TestFilesAutomataParser/";
    
    @Test
    public void testOddNrPushAutomata(){  
        
        IAutomaton automata = null;
        String errorMessage = null;
        
        try {
            automata = AutomataParser.parseXmlFile(TEST_FILES_DIRECTORY + "odd_nr_push.xml");
        } catch (AutomataParserException ex) {
            errorMessage = ex.toString();
        }
        
        assertTrue(automata != null);
        assertTrue(errorMessage == null);
    }
    
    
    @Test
    public void testSubwordAbcAutomata(){
        
        IAutomaton automata = null;
        String errorMessage = null;
        
        try {
            automata = AutomataParser.parseXmlFile(TEST_FILES_DIRECTORY + "subword_abc.xml");
        } catch (AutomataParserException ex) {
            errorMessage = ex.toString();
        }
        
        assertTrue(automata != null);
        assertTrue(errorMessage == null);
    }
    
    
    @Test
    public void testInvalidState(){
            
        IAutomaton automata = null;
        String errorMessage = null;
        
        try {
            automata = AutomataParser.parseXmlFile(TEST_FILES_DIRECTORY + "invalid_state.xml");
        } catch (AutomataParserException ex) {
            errorMessage = ex.toString();
        }
        
        assertTrue(errorMessage.equals(AutomataParserException.PREFIX + "Invalid state."));
    }
    
    
    @Test
    public void testInvalidSymbol(){
            
        IAutomaton automata = null;
        String errorMessage = null;
        
        try {
            automata = AutomataParser.parseXmlFile(TEST_FILES_DIRECTORY + "invalid_symbol.xml");
        } catch (AutomataParserException ex) {
            errorMessage = ex.toString();
        }
        
        assertTrue(errorMessage.equals(AutomataParserException.PREFIX + "Invalid symbol."));
    }
    
    
    @Test
    public void testInvalidTransition(){
            
        IAutomaton automata = null;
        String errorMessage = null;
        
        try {
            automata = AutomataParser.parseXmlFile(TEST_FILES_DIRECTORY + "invalid_transition.xml");
        } catch (AutomataParserException ex) {
            errorMessage = ex.toString();
        }
        
        assertTrue(errorMessage.equals(AutomataParserException.PREFIX + "Invalid transition."));
    }
    
    
    @Test
    public void testMissingTransition(){
            
        IAutomaton automata = null;
        String errorMessage = null;
        
        try {
            automata = AutomataParser.parseXmlFile(TEST_FILES_DIRECTORY + "missing_transition.xml");
        } catch (AutomataParserException ex) {
            errorMessage = ex.toString();
        }
        
        assertTrue(errorMessage.equals(AutomataParserException.PREFIX + "Missing transitions."));
    }
    
    
    @Test
    public void testMultipleTransitionsFromOneSymbol(){
            
        IAutomaton automata = null;
        String errorMessage = null;
        
        try {
            automata = AutomataParser.parseXmlFile(TEST_FILES_DIRECTORY + "multiple_transitions_from_one_symbol.xml");
        } catch (AutomataParserException ex) {
            errorMessage = ex.toString();
        }
        
        assertTrue(errorMessage.equals(AutomataParserException.PREFIX + "Multiple transitions from same symbol."));
    }
    
    
    @Test
    public void testNoAlphabet(){
            
        IAutomaton automata = null;
        String errorMessage = null;
        
        try {
            automata = AutomataParser.parseXmlFile(TEST_FILES_DIRECTORY + "no_alphabet.xml");
        } catch (AutomataParserException ex) {
            errorMessage = ex.toString();
        }
        
        assertTrue(errorMessage.equals(AutomataParserException.PREFIX + "No or more than one alphabet."));
    }
    
    
    @Test
    public void testNoStates(){
            
        IAutomaton automata = null;
        String errorMessage = null;
        
        try {
            automata = AutomataParser.parseXmlFile(TEST_FILES_DIRECTORY + "no_states.xml");
        } catch (AutomataParserException ex) {
            errorMessage = ex.toString();
        }
        
        assertTrue(errorMessage.equals(AutomataParserException.PREFIX + "No states."));
    }
    
    
    @Test
    public void testNoSymbolInAlphabet(){
            
        IAutomaton automata = null;
        String errorMessage = null;
        
        try {
            automata = AutomataParser.parseXmlFile(TEST_FILES_DIRECTORY + "no_symbol_in_alphabet.xml");
        } catch (AutomataParserException ex) {
            errorMessage = ex.toString();
        }
        
        assertTrue(errorMessage.equals(AutomataParserException.PREFIX + "No symbols in alphabet."));
    }
    
    
    @Test
    public void testNoTransitionFromState(){
            
        IAutomaton automata = null;
        String errorMessage = null;
        
        try {
            automata = AutomataParser.parseXmlFile(TEST_FILES_DIRECTORY + "no_transition_from_state.xml");
        } catch (AutomataParserException ex) {
            errorMessage = ex.toString();
        }
        
        assertTrue(errorMessage.equals(AutomataParserException.PREFIX + "No transitions from state."));
    }
    
    
    @Test
    public void testStatesAboveAlphabet(){
            
        IAutomaton automata = null;
        String errorMessage = null;
        
        try {
            automata = AutomataParser.parseXmlFile(TEST_FILES_DIRECTORY + "states_above_alphabet.xml");
        } catch (AutomataParserException ex) {
            errorMessage = ex.toString();
        }
        
        assertTrue(automata != null);
        assertTrue(errorMessage == null);
    }
    
    
    @Test
    public void testTransitionUsingSymbolOutsideOfAlphabet(){
            
        IAutomaton automata = null;
        String errorMessage = null;
        
        try {
            automata = AutomataParser.parseXmlFile(TEST_FILES_DIRECTORY + "transition_using_symbol_outside_of_alphabet.xml");
        } catch (AutomataParserException ex) {
            errorMessage = ex.toString();
        }
        
        assertTrue(errorMessage.equals(AutomataParserException.PREFIX + "Transition using symbol outside of alphabet."));
    }
    
    
    @Test
    public void testTwoAlphabets(){
            
        IAutomaton automata = null;
        String errorMessage = null;
        
        try {
            automata = AutomataParser.parseXmlFile(TEST_FILES_DIRECTORY + "two_alphabets.xml");
        } catch (AutomataParserException ex) {
            errorMessage = ex.toString();
        }
        
        assertTrue(errorMessage.equals(AutomataParserException.PREFIX + "No or more than one alphabet."));
    }
    
    
    @Test
    public void testUnableToReadDocument(){
            
        IAutomaton automata = null;
        String errorMessage = null;
        
        try {
            automata = AutomataParser.parseXmlFile(TEST_FILES_DIRECTORY + "unable_to_read_document.xml");
        } catch (AutomataParserException ex) {
            System.out.println(ex.toString());
            errorMessage = ex.toString();
        }
        
        assertTrue(errorMessage.equals(AutomataParserException.PREFIX + "Unable to read document."));
    }
}
