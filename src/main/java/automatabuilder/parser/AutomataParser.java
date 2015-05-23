
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
    
    public static final String NUMBERS = "NUMBERS";
    public static final String LOWER_CASE_LETTERS = "LOWER_CASE_LETTERS";
    public static final String UPPER_CASE_LETTERS = "UPPER_CASE_LETTERS";
    public static final String ALL_LETTERS = "ALL_LETTERS";
    public static final String UNUSED = "UNUSED";
    
    public static final int UNICODE_ZERO_NR = 48;
    public static final int UNICODE_NINE_NR = 57;
    public static final int UNICODE_LOWER_CASE_A_NR = 97;
    public static final int UNICODE_LOWER_CASE_Z_NR = 122;
    public static final int UNICODE_UPPER_CASE_A_NR = 65;
    public static final int UNICODE_UPPER_CASE_Z_NR = 90;
    
    private static Map<String, IState> stateMap = new HashMap();
    private static Map<List<ITransition>, List<Element>> transitionMap = new HashMap();
    private static List<IState> automataStates = new ArrayList();
    private static IState startState = null;
    private static IAlphabet alphabet = null;
    private static Set<Symbol> symbolsInAlphabet = null;
    
    public static IAutomaton parseXmlFile(String filepath)
        throws AutomataParserException
    {   
        try{
            Document document = getDocument(filepath);
            Element docElem = document.getDocumentElement();
            parseAlphabet(docElem);
            parseStates(docElem);

            IAutomaton automata = new DFA(automataStates, alphabet, startState);
        
            return automata;
        }
        catch(AutomataParserException ex){
            throw ex;
        }
        catch(IllegalArgumentException ex){
            throw new AutomataParserException(ex.getMessage());
        }
        catch(ParserConfigurationException | SAXException | IOException ex){
            throw new AutomataParserException(ex.getMessage());
        }
        finally{
            resetStaticVariables();
        }
    }
    
    
    private static void parseAlphabet(Element docElem) 
    {
        Element alphabetElement = getAlphabetElement(docElem);
        List<Element> symbolElements = getElements(alphabetElement, SYMBOL_TAG);
        Set<Symbol> symbols = getSymbols(symbolElements);
        
        symbolsInAlphabet = symbols;
        alphabet = new Alphabet(symbols);
    }
    
    
    private static Element getAlphabetElement(Element parent)
    {
        return getElements(parent, ALPHABET_TAG).get(0);
    }
    
    
    private static List<Element> getElements(Element parent, String tagName)
    {
        List<Element> elements = getElementsByTag(parent, tagName);
        checkForMissingElements(elements, tagName);
        
        return elements;
    }
    
    
    private static Set<Symbol> getSymbols(List<Element> symbolElements)
    {
        Set<Symbol> symbols = new HashSet();
        
        symbolElements.forEach(e -> {
            String value = e.getAttribute(VALUE_TAG);
                
            if(isSpecialSymbol(value)){
                specialSymbolFunctionality(value, symbols);
            }
            else{
                symbols.add(new Symbol(value));
            }
        });
            
        return symbols;
    }
        
    
    private static void parseStates(Element docElem)
    {
        List<Element> stateElements = getElements(docElem, STATE_TAG);
        automataStates = getStates(stateElements);
        parseTransitions();
    }
    
    
    private static List<IState> getStates(List<Element> stateElements)
    {
        List<IState> states = new ArrayList();
        
        stateElements.forEach(e -> {
            String name = e.getAttribute(NAME_TAG);
            Boolean isFinal = Boolean.parseBoolean(e.getAttribute(FINAL_TAG));

            List<ITransition> transitions = new ArrayList();
            IState state = new State(transitions, isFinal, name);

            states.add(state);
            transitionMap.put(transitions, getElements(e, TRANSITION_TAG));
            stateMap.put(name, state);            
        });
            
        checkCorrectStates(states);
            
        startState = states.get(0);
        
        return states;
    }
    
    
    private static void parseTransitions(){
        transitionMap.forEach((transitions, transitionElements) -> {
            transitionElements.forEach(e ->{
                String value = e.getAttribute(SYMBOL_TAG);

                if(isSpecialSymbol(value)){
                    specialTransitionFunctionality(value, e, transitions);
                }
                else{
                    addTransition(value, transitions, e);
                }
            });

            checkCorrectTransitions(transitions);
        });
    }
    
    
    private static void addTransition(String value, List<ITransition> transitions, Element transitionElement)
    {
        Symbol symbol = new Symbol(value);
        IState target = stateMap.get(transitionElement.getAttribute(TARGET_TAG));
        
        checkSymbolInAlphabet(symbol);
        
        transitions.add(new Transition(target, symbol));
    }
    
    
    private static Document getDocument(String filepath)
        throws ParserConfigurationException, SAXException, IOException
    {
        Document document;
        DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
            
        DocumentBuilder db = dbf.newDocumentBuilder();
        document = db.parse(filepath);
            
        return document;
    }
    
    
    private static void resetStaticVariables(){
        stateMap = new HashMap();
        transitionMap = new HashMap();
        automataStates = new ArrayList();
        startState = null;
        alphabet = null;
        symbolsInAlphabet = null;
    }
    
    
    private static List<Element> getElementsByTag(Element parent, String tagName) 
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
    
    
    private static boolean isSpecialSymbol(String value){
        return value.equals(NUMBERS) || 
               value.equals(LOWER_CASE_LETTERS) || 
               value.equals(UPPER_CASE_LETTERS)|| 
               value.equals(ALL_LETTERS) ||
               value.equals(UNUSED);
    }
    
    
    private static void specialSymbolFunctionality(String value, Set<Symbol> symbols){
        switch(value){
            case NUMBERS:
                for(int i = UNICODE_ZERO_NR; i <= UNICODE_NINE_NR; i++){
                    symbols.add(new Symbol(String.valueOf((char) i)));
                }  
                break;
            case LOWER_CASE_LETTERS:
                for(int i = UNICODE_LOWER_CASE_A_NR; i <= UNICODE_LOWER_CASE_Z_NR; i++){
                    symbols.add(new Symbol(String.valueOf((char) i)));
                }
                break;
            case UPPER_CASE_LETTERS:
                for(int i = UNICODE_UPPER_CASE_A_NR; i <= UNICODE_UPPER_CASE_Z_NR; i++){
                    symbols.add(new Symbol(String.valueOf((char) i)));
                }
                break;
            case ALL_LETTERS:
                for(int i = UNICODE_LOWER_CASE_A_NR; i <= UNICODE_LOWER_CASE_Z_NR; i++){
                    symbols.add(new Symbol(String.valueOf((char) i)));
                }
                for(int i = UNICODE_UPPER_CASE_A_NR; i <= UNICODE_UPPER_CASE_Z_NR; i++){
                    symbols.add(new Symbol(String.valueOf((char) i)));
                }
                break;
        }
    }
    
    
    private static void specialTransitionFunctionality(String value, Element e, List<ITransition> transitions)
    {
        switch(value){
            case NUMBERS:
                for(int i = UNICODE_ZERO_NR; i <= UNICODE_NINE_NR; i++){
                    addTransition(String.valueOf((char) i), transitions, e);
                }
                break;
            case LOWER_CASE_LETTERS:
                for(int i = UNICODE_LOWER_CASE_A_NR; i <= UNICODE_LOWER_CASE_Z_NR; i++){
                    addTransition(String.valueOf((char) i), transitions, e);
                }
            case UPPER_CASE_LETTERS:
                for(int i = UNICODE_UPPER_CASE_A_NR; i <= UNICODE_UPPER_CASE_Z_NR; i++){
                    addTransition(String.valueOf((char) i), transitions, e);
                }
                break;
            case ALL_LETTERS:
                for(int i = UNICODE_LOWER_CASE_A_NR; i <= UNICODE_LOWER_CASE_Z_NR; i++){
                    addTransition(String.valueOf((char) i), transitions, e);
                }
                for(int i = UNICODE_UPPER_CASE_A_NR; i <= UNICODE_UPPER_CASE_Z_NR; i++){
                    addTransition(String.valueOf((char) i), transitions, e);
                }
                break;
            case UNUSED:
                Set<Symbol> remainingSymbols = new HashSet(symbolsInAlphabet);
                transitions.forEach(i -> {
                    remainingSymbols.remove(i.getSymbol());
                });
                remainingSymbols.forEach(i -> {
                    IState target = stateMap.get(e.getAttribute(TARGET_TAG));
                    transitions.add(new Transition(target, i));
                });
                break;
        }
    }
    
    
    private static void checkCorrectStates(List<IState> states)
    {
        Set<String> checkDuplicates = new HashSet();
        for(IState i : states){
            if(!checkDuplicates.add(i.getName())){
                throw new AutomataParserException(STATES_SAME_NAME);
            }
        }
    }
    
    
    private static void checkForMissingElements(List<Element> elements, String tagName)
    {
        if(tagName.equals(ALPHABET_TAG) ? elements.size() != 1 : elements.isEmpty()){
            String errorMessage;
            
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
    }
    
    
    private static void checkCorrectTransitions(List<ITransition> transitions)
    {
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
    
    
    private static void checkSymbolInAlphabet(Symbol symbol){
        if(!alphabet.contains(symbol)){
            throw new AutomataParserException(
                TRANSITION_WITH_INVALID_SYMBOL
            );
        }
    }
}
