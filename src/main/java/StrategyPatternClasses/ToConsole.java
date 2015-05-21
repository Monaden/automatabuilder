package StrategyPatternClasses;

import interfaces.IShowDFA;
import interfaces.IState;

import java.util.List;

/**
 * Created by oliv on 5/20/15.
 */
public class ToConsole implements IShowDFA {

    @Override
    public void showTable(List<IState> stateList){

    }

    @Override
    public void tableToFile(List<IState> stateList, String filename) {

    }

}
