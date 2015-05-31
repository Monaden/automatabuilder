package automatabuilder;
import StrategyPatternClasses.TableGenerator;
import interfaces.IAlphabet;
import interfaces.IShowDFA;
import interfaces.IState;
import interfaces.ITransition;

import java.io.*;
import java.util.*;
import org.junit.*;
import static org.junit.Assert.*;
/**
 * Created by oliv on 5/21/15.
 */
public class OutputTest {
    Symbol a;
    Symbol b;
    Set<Symbol> symbols;
    List<ITransition> transList;
    IState q0;
    IAlphabet alphabet;

    @Before
    public void setup() {
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
    public void printTable() {
        List<IState> stateList = new LinkedList<>();
        stateList.add(q0);
        TableGenerator tb = new TableGenerator(stateList,alphabet,System.out);
        String result = "[     | a | b ]\n[ *q0 | a | b ]\n";
        File file = new File("./testfile");
        try {
            tb.changePrintStream(new PrintStream(file));
        } catch (FileNotFoundException e) {
            System.err.println("test went wrong");
            return;
        }
        tb.generate();
        StringBuilder sb = new StringBuilder();
        BufferedReader br;
        try {
             br = new BufferedReader(new FileReader("testfile"));
        } catch (FileNotFoundException e ) {
            System.err.println("test went wrong");
            return;
        }
        Scanner sc = null;
        try {
            sc = new Scanner(file);
        } catch (FileNotFoundException e) {
            System.err.println("test went wrong");
            return;
        }
        while (sc.hasNext()) {
            sb.append(sc.nextLine());
            sb.append("\n");
        }
        System.out.print(sb.toString());
        assertTrue(sb.toString().equals(result));
    }
}
