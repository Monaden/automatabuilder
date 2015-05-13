public interface ITransition {

    public State myState;
  
  public Symbol getSymbol();

  public IState getTarget();

}