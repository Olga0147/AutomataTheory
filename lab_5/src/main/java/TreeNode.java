import java.util.ArrayDeque;
import java.util.Deque;
import java.util.HashSet;
import java.util.Iterator;

public class TreeNode {

    private Deque<Character> stack;
    private Deque<Character> word;

    public TreeNode( String word,Character s) {
        this.stack = new ArrayDeque<>();
        this.stack.addLast(s);
        this.word = str2stack(word);
    }

    public TreeNode(Deque<Character> word,Deque<Character> stack) {
        this.stack = stack;
        this.word = word;
    }

    public Deque<Character> getStack() {
        return stack;
    }

    public void setStack(Deque<Character> stack) {
        this.stack = stack;
    }

    public Deque<Character> getWord() {
        return word;
    }

    public void setWord(Deque<Character> word) {
        this.word = word;
    }

    public void setWord(String word) {
        this.word = str2stack(word);
    }

    public Deque<Character> str2stack(String str){
        Deque<Character> newStack = new ArrayDeque<>();
        for (char c:str.toCharArray()) {
            newStack.addLast(c);
        }
        return newStack;
    }

    public String deq2str(Deque<Character> deque){
        Iterator i = deque.iterator();
        String str = new String();
        while (i.hasNext()){
            str+=i.next();
        }
        return str;
    }

    public boolean correct(HashSet<Character> N){
        int is = 0;
        int iw = word.size();

        for (Character character : stack) {
            if(!N.contains(character) && !word.contains(character)){
                return false;
            }
            if(!N.contains(character)){
                is++;
            }
        }

        return (is<=iw);
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        TreeNode treeNode = (TreeNode) o;
        return stack.equals(treeNode.stack) &&
                word.equals(treeNode.word);
    }

    @Override
    public String toString() {
        return "(" +
                "слово = " + deq2str(word) +
                ", стек = " + deq2str(stack) +
                ')';
    }


}
