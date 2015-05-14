package automatabuilder;

import java.util.Objects;

public class Symbol implements Comparable<Symbol> {

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

    @Override
    public int compareTo(Symbol o) {
        return Objects.compare(symbol, o.symbol, String.CASE_INSENSITIVE_ORDER);
    }

}
