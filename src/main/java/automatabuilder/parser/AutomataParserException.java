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
public class AutomataParserException extends Exception {
    
    public static final String PREFIX = "AutomataParserException: "; 
    public final String exceptionMessage;
    
    AutomataParserException(String exceptionMessage){
        this.exceptionMessage = exceptionMessage;
    }
    
    @Override
    public String toString(){
        return PREFIX + exceptionMessage;
    }
}
