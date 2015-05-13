import java.util.Vector;

public interface IState {

    public DFA myDFA;
      public Vector  myIAutomaton;
      public Transition myTransition;
    public Vector  myITransition;

  public IState transition();

  public String getName();

}