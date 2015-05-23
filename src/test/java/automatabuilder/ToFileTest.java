package automatabuilder;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Set;
import StrategyPatternClasses.ToConsole;
import StrategyPatternClasses.ToFile;
import interfaces.IAlphabet;
import interfaces.IShowDFA;
import interfaces.IState;
import interfaces.ITransition;
import java.io.IOException;
import java.util.*;
import org.junit.*;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 * Created by oliv on 5/23/15.
 */
public class ToFileTest {  Symbol a;
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
    public void printTable() throws IOException{
        List<IState> stateList = new LinkedList<>();
        stateList.add(q0);
        String expected = "[     | a | b ]\n[ *q0 | a | b ]\n";
        ToFile toFile = new ToFile("./transtest.txt");
        toFile.showTable(stateList,alphabet);
        File file = new File("./transtest.txt");
        Scanner scanner;
        try {
            scanner = new Scanner(file);
        } catch (FileNotFoundException e) {
            System.err.println("unable to open file transtext.txt");
            return;
        }
        StringBuilder stringBuilder = new StringBuilder();
        while (scanner.hasNext()) {
            stringBuilder.append(scanner.nextLine());
            stringBuilder.append("\n");
        }
        assertEquals(expected,(stringBuilder.toString()));
        file.delete();
    }
}
