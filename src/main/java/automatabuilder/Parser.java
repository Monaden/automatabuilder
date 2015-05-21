/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package automatabuilder;

import interfaces.IAlphabet;
import interfaces.IAutomaton;
import interfaces.IState;
import interfaces.ITransition;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

/**
 *
 * @author Mikael
 */
public class Parser {
    private static Map<String, IState> stateMap = new HashMap();
    private static Map<String, List<ITransition>> transitionMap = new HashMap();
    private static List<IState> states = new ArrayList();
    private static IState startState = null;
    private static IAlphabet alphabet = null;
    
    public static IAutomaton parseXmlFile(String filepath){
        
        Document document = getDocument(filepath);
        Element docElem = document.getDocumentElement();
        parseAlphabet(docElem);
        parseStates(docElem);

        IAutomaton automata = new DFA(states, alphabet, startState);
        
        resetStaticVariables();
        
        return automata;
    }
    
    private static void parseAlphabet(Element docElem){
        Set<Symbol> symbols = new HashSet();
        
        NodeList nodeList = docElem.getElementsByTagName("alphabet");
        
        if(nodeList != null && nodeList.getLength() == 1){
            nodeList = ((Element) nodeList.item(0)).getElementsByTagName("symbol");
            if(nodeList != null && nodeList.getLength() > 0){
                for(int i = 0; i < nodeList.getLength(); i++){
                    Element elem = (Element) nodeList.item(i);
                    symbols.add(new Symbol(elem.getAttribute("value")));
                }
            }
            else{
                //TODO: no symbols: throw exception?
            }
        }
        else{
            //TODO: no or more than 1 alphabet: throw exception?
        }
        
        alphabet = new Alphabet(symbols);
    }
    
    private static void parseStates(Element docElem){
        NodeList nodeList = docElem.getElementsByTagName("state");
        
        if(nodeList != null && nodeList.getLength() > 0){
            for(int i = 0; i < nodeList.getLength(); i++){
                Element elem = (Element) nodeList.item(i);
                
                String name = elem.getAttribute("name");
                Boolean isFinal = Boolean.parseBoolean(elem.getAttribute("final"));
                
                List<ITransition> transitions = new ArrayList();
                IState state = new State(transitions, isFinal, name);
                
                states.add(state);
                if(i == 0){
                    startState = state;
                }
                
                transitionMap.put(name, transitions);
                stateMap.put(name, state);
            }
            
            for(int i = 0; i < nodeList.getLength(); i++){
                Element elem = (Element) nodeList.item(i);
                parseTransitions(elem);
            }
        }
        else{
            //TODO: no states: throw exception?
        }
    }
    
    private static void parseTransitions(Element stateElem){
        String name = stateElem.getAttribute("name");        
        NodeList nodeList = stateElem.getElementsByTagName("transition");
        
        if(nodeList != null && nodeList.getLength() > 0){
            List<ITransition> transitions = transitionMap.get(name);
            
            for(int i = 0; i < nodeList.getLength(); i++){
                Element elem = (Element) nodeList.item(i);
                Symbol symbol = new Symbol(elem.getAttribute(name));
                IState target = stateMap.get(elem.getAttribute("target"));
                
                if(alphabet.contains(symbol) && target != null){
                    transitions.add(new Transition(target, symbol));
                }
                else{
                    //TODO: invalid transition: throw exception?
                }
            }
        }
        else{
            //TODO: no transitions: throw exception?
        }
    }
    
    private static Document getDocument(String filepath){
        
        Document document = null;
        DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
        
        try{
            DocumentBuilder db = dbf.newDocumentBuilder();
            document = db.parse(filepath);
        }
        catch(ParserConfigurationException | SAXException | IOException ex){
            ex.printStackTrace();
        }
        
        //TODO: Consider throwing exception instead of null
        return document;
    }
    
    private static void resetStaticVariables(){
        stateMap = new HashMap();
        transitionMap = new HashMap();
        states = new ArrayList();
        startState = null;
        alphabet = null;
    }
}
