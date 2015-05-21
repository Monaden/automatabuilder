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

/**
 *
 * @author Mikael
 */
public class AutomataParser {
    
    public static final int DEFAULT_MODE = 0;
    public static final int CONVENIENT_MODE = 1;
    
    private static Map<String, IState> stateMap = new HashMap();
    private static Map<String, List<ITransition>> transitionMap = new HashMap();
    private static List<IState> states = new ArrayList();
    private static IState startState = null;
    private static IAlphabet alphabet = null;
    private static int parseMode;
    
    public static IAutomaton parseXmlFile(String filepath)
        throws AutomataParserException
    {
        return parseXmlFile(filepath, DEFAULT_MODE);
    }
    
    
    public static IAutomaton parseXmlFile(String filepath, int modeFlag) 
        throws AutomataParserException
    {   
        parseMode = modeFlag;
        
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
        List<Element> symbolElements = getSymbolElements(alphabetElement);
        Set<Symbol> symbols = getSymbols(symbolElements);
        
        alphabet = new Alphabet(symbols);
    }
    
    
    private static Element getAlphabetElement(Element rootElement) 
        throws AutomataParserException
    {
        List<Element> elements = getElementsByTag(rootElement, "alphabet");
        
        if(elements.size() != 1){
            throw new AutomataParserException("No or more than one alphabet.");
        }
        return elements.get(0);
    }
    
    
    private static List<Element> getSymbolElements(Element alphabetElement)
        throws AutomataParserException
    {
        List<Element> symbolElements = getElementsByTag(
            alphabetElement, 
            "symbol"
        );
        
        if(symbolElements.isEmpty()){
            throw new AutomataParserException("No symbols in alphabet.");
        }
        
        return symbolElements;
    }
    
    
    private static Set<Symbol> getSymbols(List<Element> symbolElements)
        throws AutomataParserException
    {
        Set<Symbol> symbols = new HashSet();
        
        try{
            for(Element e : symbolElements){
                symbols.add(new Symbol(e.getAttribute("value")));
            }
        }
        catch(IllegalArgumentException ex){
            throw new AutomataParserException("Invalid symbol.");
        }
        
        return symbols;
    }
        
    
    private static void parseStates(Element docElem)
        throws AutomataParserException
    {
        List<Element> stateElements = getStateElements(docElem);
        states = getStates(stateElements);
        parseTransitions(stateElements);
    }
    
    
    private static List<Element> getStateElements(Element rootElement)
        throws AutomataParserException
    {
        List<Element> elements = getElementsByTag(rootElement, "state");
        
        if(elements.isEmpty()){
            throw new AutomataParserException("No states.");
        }
        return elements;
    }
    
    
    private static List<IState> getStates(List<Element> stateElements)
        throws AutomataParserException
    {
        List<IState> states = new ArrayList();
        
        try{
            for(Element e : stateElements){
                String name = e.getAttribute("name");
                Boolean isFinal = Boolean.parseBoolean(e.getAttribute("final"));

                List<ITransition> transitions = new ArrayList();
                IState state = new State(transitions, isFinal, name);

                states.add(state);
                transitionMap.put(name, transitions);
                stateMap.put(name, state);            
            }
        }
        catch(IllegalArgumentException ex){
            throw new AutomataParserException("Invalid state.");
        }
        return states;
    }
    
    
    private static void parseTransitions(List<Element> stateElements)
        throws AutomataParserException
    {
        for(Element e : stateElements){
            String name = e.getAttribute("name");
            List<Element> transitionElements = getTransitionElements(e);
            addTransitions(transitionMap.get(name), transitionElements);            
        }
    }
    
    
    private static List<Element> getTransitionElements(Element stateElement)
        throws AutomataParserException
    {
        List<Element> transitionElements = getElementsByTag(
            stateElement, 
            "transition"
        );
        
        if(transitionElements.isEmpty()){
            throw new AutomataParserException("No transitions from state.");
        }
        
        return transitionElements;
    }
    
    
    private static void addTransitions(List<ITransition> transitions, List<Element> transitionElements) 
        throws AutomataParserException
    {
        try{
            for(Element e : transitionElements){
                    Symbol symbol = new Symbol(e.getAttribute("symbol"));
                    IState target = stateMap.get(e.getAttribute("target"));

                    if(!alphabet.contains(symbol)){
                        throw new AutomataParserException(
                            "Transition using symbols outside of alphabet."
                        );
                    }
                    transitions.add(new Transition(target, symbol));
            }
        }
        catch(IllegalArgumentException ex){
            throw new AutomataParserException("Invalid transition.");
        }
        
        Set<Symbol> checkDuplicates = new HashSet();
        for(ITransition i : transitions){
            if(!checkDuplicates.add(i.getSymbol())){
                throw new AutomataParserException("Multiple transitions from same symbol.");
            }
        }
        
        if(parseMode == DEFAULT_MODE){
            if(transitions.size() != alphabet.size()){
                throw new AutomataParserException("Missing transitions.");
            }
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
            throw new AutomataParserException("Unable to read document.");
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
            throw new AutomataParserException("NodeList is null");
        }
        
        for(int i = 0; i < nodeList.getLength(); i++){
            elements.add((Element) nodeList.item(i));
        }
        
        return elements;
    }
}
