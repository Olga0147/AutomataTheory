import Exp.Symbol;

import java.util.ArrayDeque;
import java.util.Deque;


public class Path {

    private final int from;
    private final int to;
    private final char w;

    public Path(int from,  char w, int to) {
        this.from = from;
        this.to = to;
        this.w = w;
    }

    public int getFrom() {
        return from;
    }

    public int getTo() {
        return to;
    }

    public char getW() {
        return w;
    }

    public static int findMax(Deque<Path> stack){
        int max = Integer.MIN_VALUE;
        for (Path p:stack ) {
            max = Math.max(p.to, max);
        }
        return max;
    }


    public static Deque<Path> shiftOrStates(Deque<Path> stack, int shift){

        Deque<Path> stack1 = new ArrayDeque<>();

        for (Path p:stack) {
            Path p1;
            if(p.from == 0){
                p1 = new Path(0,p.w,p.to+shift);
            }
            else{
                p1 = new Path(p.from+shift,p.w,p.to+shift);
            }
            stack1.add(p1);
        }
        return stack1;
    }

    public static Deque<Path> orOp(Deque<Path> stack1, Deque<Path> stack2){

        int max1 = findMax(stack1);
        int max2 = findMax(stack2);

        Deque<Path> stack = new ArrayDeque<>(stack1);
        stack.removeLast();
        Path p1 = stack1.getLast();
        Path p = new Path(p1.from,p1.w,max1+max2-1);
        stack.add(p);

        stack2 = shiftOrStates(stack2, max1-1);
        stack.addAll(stack2);

        return stack;
    }

    public static Deque<Path> kleene(Deque<Path> stack){
        Deque<Path> stack1 = new ArrayDeque<>();
        int max = findMax(stack);

        for (Path p:stack) {
            Path p1 = (p.to != max)?(new Path(p.from+1,p.w,p.to+1)):(new Path(p.from+1,p.w,1));
            stack1.add(p1);
        }

        Path p1 = new Path(0,'\0',1);
        Path p2 = new Path(1,'\0',max+1);
        stack1.addFirst(p1);
        stack1.addLast(p2);

        return stack1;
    }

    public static Deque<Path> concat(Deque<Path> stack1, Deque<Path> stack2){

        int max1 = findMax(stack1);

        Deque<Path> stack = new ArrayDeque<>(stack1);

        for (Path p:stack2) {
            Path p1 = new Path(p.from+max1,p.w,p.to + max1);
            stack.addLast(p1);
        }

        return stack;
    }

    public static Deque<Path> sym(Symbol s){

        Path p = new Path(0,s.getS(),1);
        Deque<Path> pathArrayDeque= new ArrayDeque<>();
        pathArrayDeque.addLast(p);

        return pathArrayDeque;
    }

    @Override
    public boolean equals(Object obj){
        if (this == obj) {
            return true;
        }

        if (obj == null) {
            return false;
        }

        if (!(obj instanceof Path)) {
            return false;
        }
        Path other = (Path) obj;

        return (other.getFrom() == this.getFrom()) && (other.getW() == this.getW()) && ( other.getTo() == this.getTo());
    }

}
