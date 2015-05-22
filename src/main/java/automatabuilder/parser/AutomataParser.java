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
        List<Element> symbolElements = getElements(alphabetElement, "symbol");
        Set<Symbol> symbols = getSymbols(symbolElements);
        
        alphabet = new Alphabet(symbols);
    }
    
    
    private static Element getAlphabetElement(Element parent)
        throws AutomataParserException
    {
        return getElements(parent, "alphabet").get(0);
    }
    
    
    private static List<Element> getElements(Element parent, String tagName)
        throws AutomataParserException
    {
        List<Element> elements = getElementsByTag(parent, tagName);
        
        if(tagName.equals("alphabet") ? elements.size() != 1 : elements.isEmpty()){
            String errorMessage = "";
            
            switch(tagName){
                case "alphabet":
                    errorMessage = "No or more than one alphabet.";
                    break;
                case "symbol":
                    errorMessage = "No symbols in alphabet.";
                    break;
                case "state":
                    errorMessage = "No states.";
                    break;
                case "transition":
                    errorMessage = "No transitions from state.";
                    break;
                default:
                    errorMessage = "This should never happen!";
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
        List<Element> stateElements = getElements(docElem, "state");
        states = getStates(stateElements);
        parseTransitions(stateElements);
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
            List<Element> transitionElements = getElements(e, "transition");
            addTransitions(transitionMap.get(name), transitionElements);            
        }
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
                            "Transition using symbol outside of alphabet."
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
        
        if(transitions.size() != alphabet.size()){
            throw new AutomataParserException("Missing transitions.");
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
