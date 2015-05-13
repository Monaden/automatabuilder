import java.util.Vector;

public class DFA implements IAutomaton {

  protected java.util.Vector states;

  protected IAlphabet alphabet;

  protected IState startState;

    /**
   * 
   * @element-type IState
   */
  public Vector  myIState;
    public Vector  myIAlphabet;
      public IAlphabet myIAlphabet;

  protected IState delta(IState q, Symbol q) {
  return null;
  }

  protected IState deltaHat(IState q, IWord ax) {
  return null;
  }

}