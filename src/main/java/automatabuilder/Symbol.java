package automatabuilder;

public class Symbol {

    protected String symbol;

    public Symbol (String symbol) {
        if (symbol == null) {
            throw new IllegalArgumentException("Symbol cannot be null");
        }
        this.symbol = symbol;
    }

    public String toString() {
        return symbol;
    }

}
