/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ui;

import automatabuilder.DFA;
import automatabuilder.State;
import automatabuilder.Symbol;
import automatabuilder.Transition;
import interfaces.IAutomaton;
import automatabuilder.parser.*;
import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.Vector;

/**
 *
 * @author Adam
 */
public class CLI {
    private IAutomaton automata;
    private File loadedFile;
    
    
    public CLI(File f) {
        if (f != null) {
            loadFile(f);
        }
        this.loadedFile = f;
    }
    
    public void start() {
        while (true) {
            BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
            System.out.print("> ");
            try {
                String input = br.readLine();
                if (input.startsWith(":")) {
                    String[] parts = input.substring(1).split("\\s+");
                    run(parts);
                } else {
                    if (automata == null) {
                        System.err.println("No automata loaded.");
                    } else {
                        try {
                            if (automata.test(input)) {
                                System.out.printf(
                                    "'%s' was accepted by the automaton%n",
                                    input
                                );
                            } else {
                                System.out.printf(
                                    "'%s' was not accepted by the automaton%n",
                                        input
                                );
                            }
                            
                        } catch (Exception e) {
                            System.err.println("Error: "+e.getMessage());
                        }
                    }
                }
                
            } catch(IOException e){
                System.err.println("Error reading from command line.");
            }
        }
    }
    
    private void run(String... cmd) {
        cmd[0] = cmd[0].toLowerCase();
        switch (cmd[0]) {
            case "q":
            case "quit":
                System.out.println("Bye!");
                System.exit(0);
                break;
            case "h":
            case "help":
                printHelp();
                break;
            case "r":
            case "reload":
                loadFile(loadedFile);
                break;
            case "p":
            case "print":
                printAutomaton();
                break;
            case "l":
            case "load":
                if (cmd.length < 2) {
                    System.err.println("Not enough arguments for load.");
                    System.err.println("Use :load <file>");
                } else {
                    File f = new File(cmd[1]);
                    if (!f.exists()) {
                        System.err.printf("File %s not found.%n",f.getAbsolutePath());
                        return;
                    }

                    if (!f.getName().endsWith(".xml")) {
                        System.err.println("File is not an xml file.");
                        return;
                    }
                    loadFile(f);
                }
                break;
            default:
                System.err.println("Unknown command: "+cmd[0]);
                System.err.println("Use :help for help");
        }
        
    }

    private void printHelp() {
        String format = "%-10s %-8s %s%n";
        System.out.println("Help for Automata Builder");
        System.out.printf(format,"Command","Short","Description");
        System.out.println("--------------------------------");
        System.out.printf(format,":help",":h","This help.");
        System.out.println("");
        System.out.printf(format,":quit",":q","Quit the application.");
        System.out.println("");
        System.out.printf(format,":load",":l","Load an automaton from file.");
        System.out.printf(format,"","","Useage: :load <filename>");
        System.out.println("");
        System.out.printf(format,":reload",":r","Reload the currently loaded file.");
        System.out.println("");
    }

    private void loadFile(File f) {
        try {
            this.automata = AutomataParser.parseXmlFile(f.getAbsolutePath());
            System.out.printf("Loaded file %s %n",f.getAbsolutePath());
            loadedFile = f;
            
        } catch (AutomataParserException ape) {
            System.err.println("Error loading file: "+ape.getMessage());
        }
    }

    private void printAutomaton() {
        this.automata.printTo(System.out);
    }
    
    
    
}
