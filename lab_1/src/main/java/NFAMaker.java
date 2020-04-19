import Exp.*;

import java.util.Deque;
import java.util.HashSet;

public class NFAMaker {

    public NFAMaker() {
        alphabet = new HashSet<>();
    }

    private Deque<Path> nfa;
    private final HashSet<Character> alphabet;

    public Deque<Path> getNfa() {
        return nfa;
    }

    public HashSet<Character> getAlphabet() {
        return alphabet;
    }


    public void makeNFA(Word w){
        Deque<Path> pathDeque = maker(w);
        System.out.println("\nТаблица переходов:\n"+"| Текущее состояние | "+"Символ | "+"Следующее состояние |");
        for (Path p:pathDeque) {
            char c = (p.getW() == '\0')?'ε':p.getW();
            System.out.println("| "+p.getFrom()+" | "+c+" | "+p.getTo()+" |");
        }

        this.nfa =  pathDeque;
    }

    public Deque<Path> maker(Word w){

        switch (w.getClass().getCanonicalName()){

            case "Exp.Symbol" : {
                alphabet.add(((Symbol) w).getS());
                return Path.sym((Symbol) w);
            }
            case "Exp.Concatination":{
                Word w1 = ((Concatination)w).getS1();
                Word w2 = ((Concatination)w).getS2();
                Deque<Path> pathDeque1 = maker(w1);
                Deque<Path> pathDeque2 = maker(w2);
                return Path.concat(pathDeque1,pathDeque2);
            }
            case "Exp.KleeneStar":{
                Word w1 = ((KleeneStar)w).getS();
                Deque<Path> pathDeque1 = maker(w1);
                return Path.kleene(pathDeque1);
            }
            case "Exp.Or" : {
                Word w1 = ((Or)w).getS1();
                Word w2 = ((Or)w).getS2();
                Deque<Path> pathDeque1 = maker(w1);
                Deque<Path> pathDeque2 = maker(w2);
                return Path.orOp(pathDeque1,pathDeque2);
            }
            default:
                System.out.println(w.getClass().getCanonicalName());
                return null;
        }

    }


}
