package Exp;

public class Or implements Word {

    private Word s1;
    private Word s2;

    public Or(Word s1, Word s2) {
        this.s1 = s1;
        this.s2 = s2;
    }

    public Word getS1() {
        return s1;
    }

    public Word getS2() {
        return s2;
    }

    @Override
    public String toString () {
        return "or("+s1.toString()+" , "+s2.toString()+")";
    }
}
