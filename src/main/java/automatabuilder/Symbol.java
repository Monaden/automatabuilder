package automatabuilder;

import java.util.Objects;

public class Symbol implements Comparable<Symbol> {

    public static final String EXCEPTION_MESSAGE = "Symbol arguments invalid.";
    
    protected String symbol;

    public static final Symbol Epsilon = new Symbol();
    
    public Symbol (String symbol) {
        if (symbol == null || "".equals(symbol)) {
            throw new IllegalArgumentException(EXCEPTION_MESSAGE);
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

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 83 * hash + Objects.hashCode(this.symbol);
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        
        final Symbol other = (Symbol) obj;
        
        if (!Objects.equals(this.symbol, other.symbol)) {
            return false;
        }
        return true;
    }

}
