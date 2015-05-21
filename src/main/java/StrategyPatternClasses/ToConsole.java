package StrategyPatternClasses;

import interfaces.IAlphabet;
import interfaces.IShowDFA;
import interfaces.IState;

import java.util.List;

/**
 * Created by oliv on 5/20/15.
 */
public class ToConsole implements IShowDFA {

    @Override
    public void showTable(List<IState> stateList, IAlphabet alphabet){
    }

}
