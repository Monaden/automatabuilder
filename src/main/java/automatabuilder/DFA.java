package automatabuilder;

import StrategyPatternClasses.TableGenerator;
import exceptions.InvalidWordException;
import interfaces.*;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintStream;

import java.util.List;
import java.util.Scanner;


public class DFA implements IAutomaton {

    final protected List<IState> states;

    final protected IAlphabet alphabet;

    final protected IState startState;

    final protected IShowDFA outputter;

    public DFA(List<IState> states, IAlphabet alphabet, IState startState, IShowDFA outputter) {
        //TODO should maybe test for null arguments?
        this.states = states;
        this.alphabet = alphabet;
        this.startState = startState;
        this.outputter = outputter;
    }

    public DFA(List<IState> states, IAlphabet alphabet, IState startState) {
        this(states, alphabet, startState, null);
    }

    protected IState delta(IState q, Symbol s) {
        return q.transition(s);
    }

    protected IState deltaHat(IState q, IWord ax) {
        if (ax == null || ax.equals(Word.Empty)) {
            return q;
        }
        IState next = delta(q,ax.head());
        return deltaHat(next, ax.tail());
    }

    @Override
    public boolean test(String word) throws InvalidWordException {
        IWord w = new Word(word);
        
        if (!alphabet.isValid(w)) {
            throw new InvalidWordException(
                String.format("The word '%s' is invalid", word)
            );
        }
        
        IState end = deltaHat(startState, w);
        
        return end.isFinal();
    }

    @Override
    public String testFile(File testFile) {
        int maxLength = 20;
        StringBuilder result = new StringBuilder();
        Scanner scanner = null;
        try {
            scanner = new Scanner(testFile);
        } catch (FileNotFoundException e) {
            System.err.println("Error trying to read test file");
            return "";
        }

        while (scanner.hasNext()) {
            String word = scanner.nextLine();
            StringBuilder line = new StringBuilder();
            String suffix;
            boolean accept;
            try {
                accept = test(word);
                suffix = accept ? "Accepted" : "Rejected";
            } catch (InvalidWordException e) {
                suffix = "Invalid word";
            }
            if (word.length() > maxLength) {
                for (int i = 0; i < maxLength-3; i++) {
                    line.append(word.charAt(i));
                }
                line.append(".. ");
            }
            else {
                line.append(word);
                for (int i = line.length(); i < maxLength; i++) {
                    line.append(" ");
                }
            }
            line.append(suffix);
            line.append("\n");
            result.append(line.toString());
            //set the stringbuilder to the empty string
            line.setLength(0);
        }
        return result.toString();
    }

    @Override
    public void printTo(PrintStream ps) {
        TableGenerator tg = new TableGenerator(this.states, this.alphabet, ps);
        tg.generate();
    }

}
