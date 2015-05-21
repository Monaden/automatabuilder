package interfaces;
import java.util.List;

/**
 * Created by oliv on 5/20/15.
 */
public interface IShowDFA {
    public void showTable(List<IState> stateList);
    public void tableToFile(List<IState> stateList, String filename);
}
