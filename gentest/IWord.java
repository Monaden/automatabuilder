import java.util.Vector;

public interface IWord {

    private final IWord myIWord;
    public ISymbol myISymbol;
      public Vector  myIAutomaton;
    public Vector  myISymbol;
    public Symbol mySymbol;
  
  public Symbol head();

  public IWord tail();

}