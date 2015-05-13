import java.util.Vector;

public interface IAlphabet {

      public Vector  myDFA;
    public DFA myDFA;

  public IWord power(Integer n);

  public Boolean isValid( IWord);

}