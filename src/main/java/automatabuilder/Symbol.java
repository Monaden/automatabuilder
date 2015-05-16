package automatabuilder;

import java.util.Objects;

public class Symbol implements Comparable<Symbol> {

    protected String symbol;

    public static final Symbol Epsilon = new Symbol();
    
    public Symbol (String symbol) {
        if (symbol == null || "".equals(symbol)) {
            throw new IllegalArgumentException("Symbol cannot be null or empty");
        }
        this.symbol = symbol;
    }
    
    private Symbol() {
        this.symbol = "";
    }
    
    public String toString() {
        return symbol;
    }

    @Override
    public int compareTo(Symbol o) {
        return Objects.compare(symbol, o.symbol, String.CASE_INSENSITIVE_ORDER);
    }

}
