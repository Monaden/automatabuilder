package interfaces;
import automatabuilder.Alphabet;

import java.util.List;

/**
 * Created by oliv on 5/20/15.
 */
public interface IShowDFA {
    public void showTable(List<IState> stateList, IAlphabet alphabet);
}
