package Exp;

public class Symbol implements Word{

    private char s;

    public Symbol(char s) {
        this.s = s;
    }

    public char getS() {
        return s;
    }

    @Override
    public String toString () {
        return "symbol("+s+")";
    }
}
