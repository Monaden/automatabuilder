/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package automatabuilder.parser;

import automatabuilder.*;
import interfaces.*;
import java.io.IOException;
import java.util.*;
import javax.xml.parsers.*;
import org.w3c.dom.*;
import org.xml.sax.SAXException;

import static automatabuilder.parser.AutomataParserException.*;

/**
 *
 * @author Mikael
 */
public class AutomataParser {
    
    public static final String ALPHABET_TAG = "alphabet";
    public static final String SYMBOL_TAG = "symbol";
    public static final String STATE_TAG = "state";
    public static final String TRANSITION_TAG = "transition";
    public static final String TARGET_TAG = "target";
    public static final String VALUE_TAG = "value";
    public static final String NAME_TAG = "name";
    public static final String FINAL_TAG = "final";
    
    private static Map<String, IState> stateMap = new HashMap();
    private static Map<String, List<ITransition>> transitionMap = new HashMap();
    private static List<IState> states = new ArrayList();
    private static IState startState = null;
    private static IAlphabet alphabet = null;
    
    
    public static IAutomaton parseXmlFile(String filepath) 
        throws AutomataParserException
    {   
        try{
            Document document = getDocument(filepath);
            Element docElem = document.getDocumentElement();
            parseAlphabet(docElem);
            parseStates(docElem);

            IAutomaton automata = new DFA(states, alphabet, startState);
        
            return automata;
        }
        catch(AutomataParserException ex){
            throw ex;
        }
        finally{
            resetStaticVariables();
        }
    }
    
    
    private static void parseAlphabet(Element docElem) 
        throws AutomataParserException
    {
        Element alphabetElement = getAlphabetElement(docElem);
        List<Element> symbolElements = getElements(alphabetElement, SYMBOL_TAG);
        Set<Symbol> symbols = getSymbols(symbolElements);
        
        alphabet = new Alphabet(symbols);
    }
    
    
    private static Element getAlphabetElement(Element parent)
        throws AutomataParserException
    {
        return getElements(parent, ALPHABET_TAG).get(0);
    }
    
    
    private static List<Element> getElements(Element parent, String tagName)
        throws AutomataParserException
    {
        List<Element> elements = getElementsByTag(parent, tagName);
        
        if(tagName.equals(ALPHABET_TAG) ? elements.size() != 1 : elements.isEmpty()){
            String errorMessage = "";
            
            switch(tagName){
                case ALPHABET_TAG:
                    errorMessage = NO_OR_MORE_APLHABET;
                    break;
                case SYMBOL_TAG:
                    errorMessage = NO_SYMBOLS;
                    break;
                case STATE_TAG:
                    errorMessage = NO_STATES;
                    break;
                case TRANSITION_TAG:
                    errorMessage = NO_TRANSITIONS;
                    break;
                default:
                    errorMessage = SHOULD_NOT_HAPPEN;
            }
            throw new AutomataParserException(errorMessage);
        }
        
        return elements;
    }
    
    
    private static Set<Symbol> getSymbols(List<Element> symbolElements)
        throws AutomataParserException
    {
        Set<Symbol> symbols = new HashSet();
        
        try{
            for(Element e : symbolElements){
                symbols.add(new Symbol(e.getAttribute(VALUE_TAG)));
            }
        }
        catch(IllegalArgumentException ex){
            throw new AutomataParserException(INVALID_SYMBOL);
        }
        
        return symbols;
    }
        
    
    private static void parseStates(Element docElem)
        throws AutomataParserException
    {
        List<Element> stateElements = getElements(docElem, STATE_TAG);
        states = getStates(stateElements);
        parseTransitions(stateElements);
    }
    
    
    private static List<IState> getStates(List<Element> stateElements)
        throws AutomataParserException
    {
        List<IState> states = new ArrayList();
        
        try{
            for(Element e : stateElements){
                String name = e.getAttribute(NAME_TAG);
                Boolean isFinal = Boolean.parseBoolean(e.getAttribute(FINAL_TAG));

                List<ITransition> transitions = new ArrayList();
                IState state = new State(transitions, isFinal, name);

                states.add(state);
                transitionMap.put(name, transitions);
                stateMap.put(name, state);            
            }
        }
        catch(IllegalArgumentException ex){
            throw new AutomataParserException(INVALID_STATE);
        }
        return states;
    }
    
    
    private static void parseTransitions(List<Element> stateElements)
        throws AutomataParserException
    {
        for(Element e : stateElements){
            String name = e.getAttribute(NAME_TAG);
            List<Element> transitionElements = getElements(e, TRANSITION_TAG);
            addTransitions(transitionMap.get(name), transitionElements);            
        }
    }
    
    
    private static void addTransitions(List<ITransition> transitions, List<Element> transitionElements) 
        throws AutomataParserException
    {
        try{
            for(Element e : transitionElements){
                    Symbol symbol = new Symbol(e.getAttribute(SYMBOL_TAG));
                    IState target = stateMap.get(e.getAttribute(TARGET_TAG));

                    if(!alphabet.contains(symbol)){
                        throw new AutomataParserException(
                            TRANSITION_WITH_INVALID_SYMBOL
                        );
                    }
                    transitions.add(new Transition(target, symbol));
            }
        }
        catch(IllegalArgumentException ex){
            throw new AutomataParserException(INVALID_TRANSITION);
        }
        
        Set<Symbol> checkDuplicates = new HashSet();
        for(ITransition i : transitions){
            if(!checkDuplicates.add(i.getSymbol())){
                throw new AutomataParserException(MULTIPLE_TRANSITIONS_FORM_SYMBOL);
            }
        }
        
        if(transitions.size() != alphabet.size()){
            throw new AutomataParserException(MISSING_TRANSITION);
        }
    }
       
    
    private static Document getDocument(String filepath) 
        throws AutomataParserException
    {
        Document document = null;
        DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
        
        try{
            DocumentBuilder db = dbf.newDocumentBuilder();
            document = db.parse(filepath);
        }
        catch(ParserConfigurationException | SAXException | IOException ex){
            throw new AutomataParserException(UNABLE_TO_READ);
        }
        
        return document;
    }
    
    
    private static void resetStaticVariables(){
        stateMap = new HashMap();
        transitionMap = new HashMap();
        states = new ArrayList();
        startState = null;
        alphabet = null;
    }
    
    
    private static List<Element> getElementsByTag(Element parent, String tagName) 
            throws AutomataParserException
    {
        List<Element> elements = new ArrayList();
        NodeList nodeList = parent.getElementsByTagName(tagName);
        
        if(nodeList == null){
            throw new AutomataParserException(NODE_LIST_IS_NULL);
        }
        
        for(int i = 0; i < nodeList.getLength(); i++){
            elements.add((Element) nodeList.item(i));
        }
        
        return elements;
    }
}
