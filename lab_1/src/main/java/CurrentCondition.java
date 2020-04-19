public class CurrentCondition {

    private int from;
    private String s;

    public CurrentCondition(int from, String s) {
        this.from = from;
        this.s = s;
    }

    public int getFrom() {
        return from;
    }

    public String getS() {
        return s;
    }

    @Override
    public boolean equals(Object obj){
        if (this == obj) {
            return true;
        }

        if (obj == null) {
            return false;
        }

        if (!(obj instanceof CurrentCondition)) {
            return false;
        }
        CurrentCondition other = (CurrentCondition) obj;

        return (other.getFrom() == this.getFrom())  && ( (other.getS()).equals(this.getS()));
    }

}
