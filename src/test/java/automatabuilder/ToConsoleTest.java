package automatabuilder;

import StrategyPatternClasses.ToConsole;
import interfaces.IAlphabet;
import interfaces.IShowDFA;
import interfaces.IState;
import interfaces.ITransition;

import java.util.*;

import org.junit.*;

import static org.junit.Assert.*;

/**
 * Created by oliv on 5/21/15.
 */
public class ToConsoleTest {
    Symbol a;
    Symbol b;
    Set<Symbol> symbols;
    List<ITransition> transList;
    IState q0;
    IAlphabet alphabet;

    @Before
    public void setup(){
        symbols = new HashSet<>();
        a = new Symbol("a");
        b = new Symbol("b");
        symbols.add(a);
        symbols.add(b);
        transList = new ArrayList<>();
        q0 = new State(transList, true, "q0");
        transList.add(new Transition(q0, a));
        transList.add(new Transition(q0, b));
        alphabet = new Alphabet(symbols);

    }

    @Test
    public void printTable(){
        IShowDFA outputter = new ToConsole();
        List<IState> stateList = new LinkedList<>();
        stateList.add(q0);
        outputter.showTable(stateList,alphabet);
    }
}
