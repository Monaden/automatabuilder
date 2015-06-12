/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package automatabuilder.parser;

/**
 *
 * @author Mikael
 */
public class AutomataParserException extends RuntimeException {
    
    public static final String PREFIX = "AutomataParserException: ";
    public static final String NO_OR_MORE_APLHABET = "No or more than one alphabet.";
    public static final String NO_SYMBOLS = "No symbols in alphabet.";
    public static final String NO_STATES = "No states.";
    public static final String NO_TRANSITIONS = "No transitions from state.";
    public static final String SHOULD_NOT_HAPPEN = "This should never happen!";
    public static final String TRANSITION_WITH_INVALID_SYMBOL = "Transition using symbol outside of alphabet.";
    public static final String MULTIPLE_TRANSITIONS_FORM_SYMBOL = "Multiple transitions from same symbol.";
    public static final String MISSING_TRANSITION = "Missing transitions.";
    public static final String NODE_LIST_IS_NULL = "NodeList is null";
    public static final String STATES_SAME_NAME = "Two or more states have the same name.";
            
    public final String exceptionMessage;
    
    AutomataParserException(String exceptionMessage){
        this.exceptionMessage = exceptionMessage;
    }
    
    @Override
    public String toString(){
        return PREFIX + exceptionMessage;
    }
}
